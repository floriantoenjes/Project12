package com.floriantoenjes.step;

import com.floriantoenjes.core.BaseEntity;
import com.floriantoenjes.recipe.Recipe;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Step extends BaseEntity {
    private String description;

    @ManyToOne
    private Recipe recipe;

    public Step() {
    }

    public Step(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
