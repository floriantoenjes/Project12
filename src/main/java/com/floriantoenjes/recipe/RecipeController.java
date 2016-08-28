package com.floriantoenjes.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @RequestMapping("/index")
    public String listRecipes(Model model) {
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String addRecipe() {

    }

    @RequestMapping("/recipe/{id}")
    public String add(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return "detail";
    }
    @RequestMapping("/add")
    public String recipeForm() {
        return null;
    }

}
