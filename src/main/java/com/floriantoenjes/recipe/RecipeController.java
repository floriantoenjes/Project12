package com.floriantoenjes.recipe;

import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.ingredient.IngredientService;
import com.floriantoenjes.item.ItemService;
import com.floriantoenjes.step.Step;
import com.floriantoenjes.user.User;
import com.floriantoenjes.user.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @RequestMapping("/index")
    public String listRecipes(Model model) {
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String addRecipe(Recipe recipe, BindingResult result) {
        System.out.println("BREAK");
        recipe.getIngredients().forEach( i -> i.setRecipe(recipe));
        recipe.getSteps().forEach( i -> i.setRecipe(recipe));
        recipeService.save(recipe);
        return "redirect:/index";
    }

    @RequestMapping("/add")
    public String recipeForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("categories", Category.values());
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
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Hibernate.initialize(user.getFavorites());
        user.addFavorite(recipe);
        recipe.addUserFavorited(user);
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
        return "edit";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.findById(id);
        recipeService.delete(recipe);
        return "redirect:/index";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }


}
