package com.floriantoenjes.recipe;

import com.floriantoenjes.ingredient.IngredientService;
import com.floriantoenjes.item.ItemService;
import com.floriantoenjes.user.Role;
import com.floriantoenjes.user.User;
import com.floriantoenjes.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void listRecipes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/index"));
    }

}