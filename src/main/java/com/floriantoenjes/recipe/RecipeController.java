package com.floriantoenjes.recipe;

import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.ingredient.IngredientService;
import com.floriantoenjes.step.Step;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/index")
    public String listRecipes(Model model) {
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String addRecipe(@Valid Recipe recipe, @RequestParam(name = "item") String item,
                            @RequestParam(name = "condition") String condition,
                            @RequestParam(name="quantity") String quantity
            ,BindingResult result) {

        String[] items = item.split(",");
        String[] conditions = condition.split(",");
        String[] quantities = quantity.split(",");

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            ingredients.add(
                    new Ingredient(
                            items[i],
                            conditions[i],
                            Integer.parseInt(quantities[i]))
            );
        }
        recipe.setIngredients(ingredients);

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
        model.addAttribute("recipe", recipe);
        return "detail";
    }

    @RequestMapping("/recipe/{id}/favorite")
    public String setFavorite(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        recipe.setFavorite(!recipe.isFavorite());
        return String.format("redirect:/recipe/%s", id);
    }





}
