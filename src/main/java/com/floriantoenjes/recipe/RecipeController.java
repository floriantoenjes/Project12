package com.floriantoenjes.recipe;

import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.ingredient.IngredientService;
import com.floriantoenjes.item.Item;
import com.floriantoenjes.item.ItemService;
import com.floriantoenjes.step.Step;
import com.floriantoenjes.user.User;
import com.floriantoenjes.user.UserService;
import com.floriantoenjes.web.FlashMessage;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    Validator validator;

    @RequestMapping("/index")
    public String listRecipes(@RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "q", required = false) String q, Model model,
                              RedirectAttributes redirectAttributes) {
        List<Recipe> recipes = recipeService.findAll();

        // If there is a query then search the recipes
        if (q != null && !q.isEmpty()) {
            recipes = recipes.stream().filter( recipe -> recipe.getName().toLowerCase().contains(q.toLowerCase()))
                    .collect(Collectors.toList());
            if (recipes.size() == 0) {
                redirectAttributes.addFlashAttribute("flash", new FlashMessage("No recipes found",
                        FlashMessage.Status.FAILED));
                return "redirect:/index";
            }
        }

        // Else if there is a category given then  filter for category
        else if (category != null && !category.isEmpty()) {
            recipes = recipes.stream().filter(recipe -> {
                return recipe.getCategory().name().equalsIgnoreCase(category);
            }).collect(Collectors.toList());
            model.addAttribute(category, true);
        }

        // Else just show all recipes

        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Map<Recipe, Boolean> recipeMap = new HashMap<>();

        recipes.forEach(recipe -> {
            Hibernate.initialize(recipe.getUsersFavorited());
            if (recipe.getUsersFavorited().contains(user)) {
                recipeMap.put(recipe, true);
            } else {
                recipeMap.put(recipe, false);
            }
        });

        model.addAttribute("recipeMap", recipeMap);
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String addRecipe(Recipe recipe, BindingResult result, RedirectAttributes redirectAttributes) {
        recipe.getIngredients().forEach( i -> i.setRecipe(recipe));
        recipe.getSteps().forEach( i -> i.setRecipe(recipe));

        // Validate the recipe
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

        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (recipe.getUsersFavorited().contains(user)) {
            model.addAttribute("favorite", true);
        }

        model.addAttribute("recipe", recipe);
        return "detail";
    }

    @RequestMapping("/recipe/{id}/favorite")
    public String setFavorite(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
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
    public String editForm(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", Category.values());
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("action", String.format("/recipe/%s/edit", id));
        return "edit";
    }

    @RequestMapping(value = "/recipe/{id}/edit", method = RequestMethod.POST)
    public String editRecipe(@PathVariable Long id, Recipe recipe, BindingResult result, RedirectAttributes redirectAttributes) {
        recipe.getIngredients().forEach( i -> i.setRecipe(recipe));
        recipe.getSteps().forEach( i -> i.setRecipe(recipe));

        // Validate the recipe
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
    public String deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.findById(id);
        recipeService.delete(recipe);
        return "redirect:/index";
    }

}
