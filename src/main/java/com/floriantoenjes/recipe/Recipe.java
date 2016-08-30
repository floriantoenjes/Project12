package com.floriantoenjes.recipe;

import com.floriantoenjes.core.BaseEntity;
import com.floriantoenjes.ingredient.Ingredient;
import com.floriantoenjes.step.Step;
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.constraints.Range;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends BaseEntity {
    @Size(min = 3, max = 250)
    private String photo;
    @Size(min = 3, max = 250)
    private String name;
    @Size(min = 5, max = 250)
    private String description;
//    @NotNull
    private Category category;
    @Range(min = 1, max = 2000)
    private Integer prepTime;
    @Range(min = 1, max = 2000)
    private Integer cookTime;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ingredient> ingredients;
    @OneToMany(mappedBy = "recipe",  cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Step> steps;
    private boolean isFavorite;

    public Recipe() {
    }

    public Recipe(String photo, String name, String description, Category category, Integer prepTime, Integer cookTime) {
        this.photo = photo;
        this.name = name;
        this.description = description;
        this.category = category;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
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
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}

