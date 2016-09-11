package com.floriantoenjes.item;

import com.floriantoenjes.core.BaseEntity;
import com.floriantoenjes.ingredient.Ingredient;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Item extends BaseEntity{
    private String name;
    @OneToOne
    private Ingredient ingredient;


    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
