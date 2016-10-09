package com.floriantoenjes.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    // Prevent deletion of ingredients via REST
    @Override
    @RestResource(exported = false)
    void delete(Ingredient entity);
}
