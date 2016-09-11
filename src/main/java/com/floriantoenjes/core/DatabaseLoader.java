package com.floriantoenjes.core;

import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.item.Item;
import com.floriantoenjes.recipe.Category;
import com.floriantoenjes.recipe.Recipe;
import com.floriantoenjes.recipe.RecipeRepository;
import com.floriantoenjes.step.Step;
import com.floriantoenjes.user.Role;
import com.floriantoenjes.user.RoleRepository;
import com.floriantoenjes.user.User;
import com.floriantoenjes.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseLoader implements ApplicationRunner {
    RecipeRepository recipes;
    UserRepository users;
    RoleRepository roles;

    @Autowired
    public DatabaseLoader(RecipeRepository recipes, UserRepository users, RoleRepository roles) {
        this.recipes = recipes;
        this.users = users;
        this.roles = roles;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Ingredient egg = new Ingredient(new Item("Egg"), "superb", 6);
        Step step = new Step("Cook and prepare a tasty egg");
        Recipe recipe = new Recipe("http://abc.com", "Ham and Eggs", "Tasty",
                Category.Breakfast, 20, 10);;
        recipe.addIngredient(egg);
        recipe.addStep(step);
        recipes.save(recipe);

        User user = new User("fto", "password", new Role("ROLE_USER"));
        users.save(user);
    }
}
