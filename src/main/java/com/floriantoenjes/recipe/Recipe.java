package com.floriantoenjes.recipe;

import com.floriantoenjes.core.BaseEntity;
import com.floriantoenjes.ingredient.Ingredient;

import javax.persistence.Entity;
import java.awt.Image;
import java.util.List;

@Entity
public class Recipe extends BaseEntity {
    private Image photo;
    private String name;
    private String Description;
    private Category category;
    private Integer prepTime;
    private Integer cookTime;
    private List<Ingredient> ingredients;
    private List<String> steps;

    public Recipe(String name, String description, Category category, Integer prepTime, Integer cookTime,
                  List<Ingredient> ingredients, List<String> steps) {
        this.name = name;
        Description = description;
        this.category = category;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}

enum Category {
    Breakfast, Lunch, Dinner, Dessert
}