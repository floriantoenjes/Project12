package com.floriantoenjes.core;

import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.item.Item;
import com.floriantoenjes.item.ItemRepository;
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
    ItemRepository items;

    @Autowired
    public DatabaseLoader(RecipeRepository recipes, UserRepository users, RoleRepository roles, ItemRepository items) {
        this.recipes = recipes;
        this.users = users;
        this.roles = roles;
        this.items = items;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Create some items
        items.save(new Item("Tomatoes"));
        items.save(new Item("Salad"));
        items.save(new Item("Potatoes"));
        items.save(new Item("Onions"));
        items.save(new Item("Strawberries"));


        // Create a recipe
        Ingredient egg = new Ingredient(new Item("Eggs"), "fresh", 2);
        Ingredient ham = new Ingredient(new Item("Ham"), "fresh", 4);
        Step step1 = new Step("1. Buy the groceries");
        Step step2 = new Step("2. Roast the ham");
        Recipe recipe = new Recipe("http://abc.com", "Ham and Eggs", "Tasty",
                Category.Breakfast, 20, 10);;
        recipe.addIngredient(egg);
        recipe.addIngredient(ham);
        recipe.addStep(step1);
        recipe.addStep(step2);
        recipes.save(recipe);

        User user = new User("user", "password", new Role("ROLE_USER"));
        users.save(user);
    }
}
