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

@Component
public class DatabaseLoader implements ApplicationRunner {
    private RecipeRepository recipes;
    private UserRepository users;
    private ItemRepository items;

    @Autowired
    public DatabaseLoader(RecipeRepository recipes, UserRepository users, RoleRepository roles, ItemRepository items) {
        this.recipes = recipes;
        this.users = users;
        this.items = items;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Create a mock user
        // Username: user
        // Password: password
        User user = new User("user",
                "$2a$10$AGOk3D.U1cR719pUI4W98eUkEejDWH3/EpCa4vBzp8DE5OaYI9GfO",
                new Role("ROLE_USER"));
        users.save(user);

        // Create some items
        items.save(new Item("Tomatoes"));
        items.save(new Item("Salad"));
        items.save(new Item("Potatoes"));
        items.save(new Item("Onions"));
        items.save(new Item("Strawberries"));


        // Mock recipe 1
        Ingredient egg = new Ingredient(new Item("Eggs"), "fresh", 2);
        Ingredient ham = new Ingredient(new Item("Ham"), "fresh", 4);
        Step step11 = new Step("1. Buy the groceries");
        Step step12 = new Step("2. Roast the ham");
        Recipe recipe = new Recipe("http://abc.com", "Ham and Eggs", "A tasty breakfast meal",
                Category.Breakfast, 20, 10);;
        recipe.addIngredient(egg);
        recipe.addIngredient(ham);
        recipe.addStep(step11);
        recipe.addStep(step12);
        recipe.setOwner(user);
        recipes.save(recipe);


        // Mock recipe 2
        Ingredient chocolate = new Ingredient(new Item("Chocolate"), "bar", 2);
        Step step21 = new Step("Mix the chocolate");
        Recipe recipe2 = new Recipe("http://example.com", "Chocolate Smoothie", "A very high sugar drink",
                Category.Dessert, 5, 0);
        recipe2.addIngredient(chocolate);
        recipe2.addStep(step21);
        recipe2.setOwner(user);
        recipes.save(recipe2);

    }
}
