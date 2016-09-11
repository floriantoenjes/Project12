package com.floriantoenjes.ingredient;

import com.floriantoenjes.core.BaseEntity;
import com.floriantoenjes.item.Item;
import com.floriantoenjes.recipe.Recipe;

import javax.persistence.*;

@Entity
public class Ingredient extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    private Item item;
    private String condition;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Ingredient(){}

    public Ingredient(Item item, String condition, Integer quantity) {
        this.item = item;
        this.condition = condition;
        this.quantity = quantity;
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


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
