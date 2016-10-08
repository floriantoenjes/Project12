package com.floriantoenjes.recipe;

import com.floriantoenjes.core.BaseEntity;
import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.step.Step;
import com.floriantoenjes.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends BaseEntity {

    @Size(min = 3, max = 250)
    private String photo;
    @Size(min = 3, max = 40)
    private String name;
    @Size(min = 5, max = 250)
    private String description;
    @NotNull
    private Category category;
    @NotNull(message = "has to have a prep time")
    private Integer prepTime;

    private Integer cookTime;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
    @OneToMany(mappedBy = "recipe",  cascade = CascadeType.ALL)
    private List<Step> steps;
    // Todo: Should this be private?
    public boolean isFavorite;
    @ManyToOne
    @NotNull
    private User owner;
    @ManyToMany
    private List<User> usersFavorited;

    public Recipe() {
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public Recipe(String photo, String name, String description, Category category, Integer prepTime, Integer cookTime) {
        this();
        this.photo = photo;
        this.name = name;
        this.description = description;
        this.category = category;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void addStep(Step step) {
        step.setRecipe(this);
        steps.add(step);
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        for (Ingredient ingredient : ingredients) {
            ingredient.setRecipe(this);
        }
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
        for (Step step : steps) {
            step.setRecipe(this);
        }
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
//        owner.addRecipe(this);
        this.owner = owner;
    }

    public List<User> getUsersFavorited() {
        return usersFavorited;
    }

    public void setUsersFavorited(List<User> usersFavorited) {
        this.usersFavorited = usersFavorited;
    }

    public void addUserFavorited(User user) {
        usersFavorited.add(user);
    }

    public void removeUserFavorited(User user) {
        usersFavorited.remove(user);
    }
}

