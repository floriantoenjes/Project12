package com.floriantoenjes.core;

import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.recipe.Category;
import com.floriantoenjes.recipe.Recipe;
import com.floriantoenjes.recipe.RecipeRepository;
import com.floriantoenjes.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseLoader implements ApplicationRunner {
    RecipeRepository recipes;

    @Autowired
    public DatabaseLoader(RecipeRepository recipes) {
        this.recipes = recipes;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Recipe recipe = new Recipe(
                "http://abc.com",
                "Ham and Eggs",
                "Tasty",
                Category.Breakfast, 20, 10
                );
        recipe.addIngredient(new Ingredient("Egg", "good", 10));
        recipe.addStep(new Step("Cook and prepare a tasty egg"));

        Recipe recipe2 = new Recipe(
                "url",
                "Tofu flesh",
                "It is tasty",
                Category.Dinner,
                2,
                5
        );
        recipe2.addIngredient(new Ingredient("Tofu", "packaged", 2));
        recipe.addStep(new Step("Eat it"));

        recipes.save(recipe);
        recipes.save(recipe2);
    }
}
