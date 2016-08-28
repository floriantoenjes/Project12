package com.floriantoenjes.ingredient;

import com.floriantoenjes.recipe.Recipe;
import com.floriantoenjes.recipe.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    public void save(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public Ingredient findById(Long id) {
        return ingredientRepository.findOne(id);
    }

    public List<Ingredient> findAll() {
        return (List<Ingredient>) ingredientRepository.findAll();
    }
}
