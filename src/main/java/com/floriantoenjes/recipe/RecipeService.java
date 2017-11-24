package com.floriantoenjes.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void save(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Recipe findById(Long id) {
        return recipeRepository.findOne(id);
    }

    public List<Recipe> findAll() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }
}
