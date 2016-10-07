package com.floriantoenjes.recipe;

import com.floriantoenjes.Application;
import com.floriantoenjes.user.Role;
import com.floriantoenjes.user.User;
import com.floriantoenjes.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class RecipeControllerTest {
    private MockMvc mockMvc;

    @Autowired
    RecipeController recipeController;

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");


        User user = new User("user", "password",
                new Role("ROLE_USER"));
        user.setId(2L);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

        mockMvc = MockMvcBuilders
                .standaloneSetup(recipeController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void index_ShouldReturnIndexTemplate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/index"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void index_ShouldReturnTwoRecipes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/index"))
        .andExpect(MockMvcResultMatchers.view().name("index"))
        .andExpect(MockMvcResultMatchers.model().attribute("recipeMap", org.hamcrest.collection.IsMapWithSize
                .aMapWithSize(2)));
    }

    @Test
    public void index_ShouldReturnRecipeWithCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/index")
        .param("category", "breakfast"))
        .andExpect(MockMvcResultMatchers.view().name("index"))
        .andExpect(MockMvcResultMatchers.model().attribute("recipeMap", org.hamcrest.collection.IsMapWithSize
                .aMapWithSize(1)));
    }

    @Test
    public void index_ShouldReturnRecipeWithSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/index")
        .param("q", "smoothie"))
        .andExpect(MockMvcResultMatchers.view().name("index"))
        .andExpect(MockMvcResultMatchers.model().attribute("recipeMap", org.hamcrest.collection.IsMapWithSize
                .aMapWithSize(1)));
    }

    @Test
    public void index_post_ShouldAddNewRecipe() throws Exception {
        User user = (User) userService.loadUserByUsername("user");

        mockMvc.perform(MockMvcRequestBuilders.post("/index")
                .with(SecurityMockMvcRequestPostProcessors.user(user))
        .param("id", "")
        .param("version", "")
        .param("photo", "www.example.com")
        .param("name", "Recipe")
        .param("description", "Description of a recipe")
        .param("category", "Breakfast")
        .param("prepTime", "15")
        .param("cookTime", "5")
        .param("ingredients[0].id", "")
        .param("ingredients[0].version", "")
        .param("ingredients[0].item", "1")
        .param("ingredients[0].condition", "fresh")
        .param("ingredients[0].quantity", "3")
        .param("steps[0].id", "")
        .param("steps[0].version", "")
        .param("steps[0].description", "New step")
        ).andExpect(MockMvcResultMatchers.redirectedUrl("/index"));

        mockMvc.perform(MockMvcRequestBuilders.get("/index"))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attribute("recipeMap", org.hamcrest.collection.IsMapWithSize
                            .aMapWithSize(3)));

    }

    @Test
    public void index_post_ShouldRedirectOnValidationErrors() throws Exception {
        User user = (User) userService.loadUserByUsername("user");

        mockMvc.perform(MockMvcRequestBuilders.post("/index")
                .with(SecurityMockMvcRequestPostProcessors.user(user))
        .param("id", "")
        .param("version", "")
        // Empty String here which does not validate
        .param("photo", "")
        .param("name", "Recipe")
        .param("description", "Description of a recipe")
        .param("category", "Breakfast")
        .param("prepTime", "15")
        .param("cookTime", "5")
        .param("ingredients[0].id", "")
        .param("ingredients[0].version", "")
        .param("ingredients[0].item", "1")
        .param("ingredients[0].condition", "fresh")
        .param("ingredients[0].quantity", "3")
        .param("steps[0].id", "")
        .param("steps[0].version", "")
        .param("steps[0].description", "New step")
        ).andExpect(MockMvcResultMatchers.redirectedUrl("/add"));

    }

    @Test
    public void index_post_ShouldRedirectOnErrors() throws Exception {
        User user = (User) userService.loadUserByUsername("user");

        mockMvc.perform(MockMvcRequestBuilders.post("/index")
                .with(SecurityMockMvcRequestPostProcessors.user(user))
        // Missing parameters here
        .param("id", "")
        .param("version", "")
        ).andExpect(MockMvcResultMatchers.redirectedUrl("/add"));

    }

    @Test
    public void add_ShouldReturnNewRecipeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add"))
                .andExpect(MockMvcResultMatchers.view().name("edit"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "/index"));
    }

    @Test
    public void recipe_id_ShouldReturnRecipeDetailPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1"))
                .andExpect(MockMvcResultMatchers.view().name("detail"))
                .andExpect(MockMvcResultMatchers.model().attribute("recipe", org.hamcrest.Matchers.any(Recipe.class)));
    }

    @Test
    public void recipe_id_favorite_ShouldRedirectToDetailPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/favorite"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/recipe/1"));
    }

    @Test
    public void recipe_id_edit_ShouldReturnEditForm() throws Exception {
        User user = (User) userService.loadUserByUsername("user");

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/edit")
                .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(MockMvcResultMatchers.view().name("edit"))
                .andExpect(MockMvcResultMatchers.model().attribute("action", "/recipe/1/edit"))
                .andExpect(MockMvcResultMatchers.model().attribute("recipe", org.hamcrest.Matchers.any(Recipe.class)));
    }

    @Test
    public void recipe_id_edit_post_ShouldUpdateRecipe() throws Exception {
        Recipe recipe = recipeService.findById(1L);

        User user = (User) userService.loadUserByUsername("user");

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/1/edit")
                .with(SecurityMockMvcRequestPostProcessors.user(user))
                .param("id", "1")
                .param("version", "0")
                .param("photo", "http://abc.com")
                .param("name", "Updated Recipe")
                .param("description", "A tasty breakfast meal")
                .param("category", "Breakfast")
                .param("prepTime", "20")
                .param("cookTime", "10")
                .param("ingredients[0].id", "1")
                .param("ingredients[0].version", "0")
                .param("ingredients[0].item", "6")
                .param("ingredients[0].condition", "fresh")
                .param("ingredients[0].quantity", "2")
                .param("steps[0].id", "1")
                .param("steps[0].version", "0")
                .param("steps[0].description", "1. Buy the groceries")
                .param("steps[1].id", "2")
                .param("steps[1].version", "0")
                .param("steps[1].description", "2. Roast the ham")
        ).andExpect(MockMvcResultMatchers.redirectedUrl("/index"));

        Recipe updatedRecipe = recipeService.findById(1L);
        org.hamcrest.MatcherAssert.assertThat(updatedRecipe.getName(), org.hamcrest.Matchers.not(recipe.getName()));
    }

}