package com.floriantoenjes.ingredient;

import com.floriantoenjes.core.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Ingredient extends BaseEntity {
    private String item;
    private String condition;
    private Integer quantity;
}
