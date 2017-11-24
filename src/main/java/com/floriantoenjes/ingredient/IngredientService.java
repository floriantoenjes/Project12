package com.floriantoenjes.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

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
