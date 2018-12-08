package com.floriantoenjes.recipe;

import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.item.Item;
import com.floriantoenjes.item.ItemService;
import com.floriantoenjes.step.Step;
import com.floriantoenjes.user.User;
import com.floriantoenjes.user.UserService;
import com.floriantoenjes.web.FlashMessage;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@Transactional
public class RecipeController {

    private RecipeService recipeService;

    private ItemService itemService;

    private UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, ItemService itemService,
                     UserService userService) {
        this.recipeService = recipeService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @Resource(name = "localValidatorFactoryBean")
    private Validator validator;

    @RequestMapping("/index")
    public String listRecipes(@RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "q", required = false) String query, Model model,
                              RedirectAttributes redirectAttributes) {

        Map<Recipe, Boolean> recipeMap = new TreeMap<>();
        List<Recipe> recipes = recipeService.findAll();

        if (isPresent(query)) {
            Optional<List<Recipe>> searchResults = searchForRecipes(recipes, query);

            if (!searchResults.isPresent()) {
                redirectAttributes.addFlashAttribute("flash",
                        new FlashMessage("No recipes found", FlashMessage.Status.FAILED));
                return "redirect:/index";
            }

            recipes = searchResults.get();

        } else if (isPresent(category)) {
            model.addAttribute(category, true);
            recipes = filterRecipesByCategory(recipes, category);
        }

        assignFavoritesToRecipes(recipeMap, recipes);
        model.addAttribute("recipeMap", recipeMap);

        return "index";
    }

    private boolean isPresent(String str) {
        return str != null && !str.isEmpty();
    }

    private Optional<List<Recipe>> searchForRecipes(List<Recipe> recipes, String query) {
            List<Recipe> results = filterRecipesByQuery(recipes, query);

            if (results.size() > 0) {
                return Optional.of(results);
            } else {
                return Optional.empty();
            }
    }

    private List<Recipe> filterRecipesByQuery(List<Recipe> recipes, String query) {
        return recipes.stream().filter( recipe -> recipe.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Recipe> filterRecipesByCategory(List<Recipe> recipes, String category) {
        return recipes.stream()
                .filter(recipe -> recipe.getCategory().name().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    private void assignFavoritesToRecipes(Map<Recipe, Boolean> recipeMap, List<Recipe> recipes) {
        User user = getCurrentUser();

        recipes.forEach(recipe -> {
            Hibernate.initialize(recipe.getUsersFavorited());
            if (recipe.getUsersFavorited().contains(user)) {
                recipeMap.put(recipe, true);
            } else {
                recipeMap.put(recipe, false);
            }
        });
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String addRecipe(Recipe recipe, BindingResult result, RedirectAttributes redirectAttributes) {
        createRecipeAssociations(recipe);

        User user = getCurrentUser();
        recipe.setOwner(user);

        validator.validate(recipe, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipe", result);
            redirectAttributes.addFlashAttribute("recipe", recipe);
            return "redirect:/add";
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Recipe created",
                FlashMessage.Status.SUCCESS));

        recipeService.save(recipe);

        return "redirect:/index";
    }

    @RequestMapping("/add")
    public String recipeForm(Model model) {
        if (!model.containsAttribute("recipe")) {
            Recipe recipe = new Recipe();
            recipe.addIngredient(new Ingredient(new Item(""), "", 0));
            recipe.addStep(new Step(""));
            model.addAttribute("recipe", recipe);
        }
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("categories", Category.values());
        model.addAttribute("action", "/index");

        return "edit";
    }

    @RequestMapping("/recipe/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        Hibernate.initialize(recipe.getIngredients());
        Hibernate.initialize(recipe.getSteps());
        Hibernate.initialize(recipe.getUsersFavorited());
        User user = getCurrentUser();

        if (recipe.getUsersFavorited().contains(user)) {
            model.addAttribute("favorite", true);
        }

        model.addAttribute("recipe", recipe);

        return "detail";
    }


    @RequestMapping("/recipe/{id}/favorite")
    public String setFavorite(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        User user = getCurrentUser();
        Hibernate.initialize(user.getFavorites());
        List<Recipe> favorites = user.getFavorites();

        if (favorites.contains(recipe)) {
            favorites.remove(recipe);
            recipe.removeUserFavorited(user);
        } else {
            user.addFavorite(recipe);
            recipe.addUserFavorited(user);
        }

        userService.save(user);
        recipeService.save(recipe);

        return String.format("redirect:/recipe/%s", id);
    }

    @RequestMapping("/recipe/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Recipe recipe = recipeService.findById(id);

        User user = getCurrentUser();
        if (!user.equals(recipe.getOwner())) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("You are not allowed to edit this recipe",
                    FlashMessage.Status.FAILED));
            return "redirect:/index";
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", Category.values());
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("action", String.format("/recipe/%s/edit", id));

        return "edit";
    }

    @RequestMapping(value = "/recipe/{id}/edit", method = RequestMethod.POST)
    public String editRecipe(@PathVariable Long id, Recipe recipe, BindingResult result,
                             RedirectAttributes redirectAttributes) {
        createRecipeAssociations(recipe);

        User user = getCurrentUser();
        recipe.setOwner(user);

        validator.validate(recipe, result);

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipe", result);
            redirectAttributes.addFlashAttribute("recipe", recipe);
            return String.format("redirect:/recipe/%s/edit", id);
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Recipe updated",
                FlashMessage.Status.SUCCESS));

        recipeService.save(recipe);

        return "redirect:/index";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Recipe recipe = recipeService.findById(id);
        User user = getCurrentUser();

        if (!user.equals(recipe.getOwner())) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("You are not allowed to delete this recipe",
                    FlashMessage.Status.FAILED));
        } else {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Recipe has been deleted",
                    FlashMessage.Status.SUCCESS));
            recipeService.delete(recipe);
        }

        return "redirect:/index";
    }

    private void createRecipeAssociations(Recipe recipe) {
        recipe.getIngredients().forEach( ingredient -> ingredient.setRecipe(recipe));
        recipe.getSteps().forEach( step -> step.setRecipe(recipe));
    }

    private User getCurrentUser() {
        return userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
