package com.floriantoenjes.recipe;

import com.floriantoenjes.step.Step;
import com.floriantoenjes.step.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

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
