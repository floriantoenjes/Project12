package com.floriantoenjes.ingredient;

import com.floriantoenjes.core.BaseEntity;
import com.floriantoenjes.recipe.Recipe;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Ingredient extends BaseEntity {
    private String name;
    private String condition;
    private Integer quantity;
    @ManyToOne
    private Recipe recipe;

    public Ingredient(){}

    public Ingredient(String name, String condition, Integer quantity) {
        this.name = name;
        this.condition = condition;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
