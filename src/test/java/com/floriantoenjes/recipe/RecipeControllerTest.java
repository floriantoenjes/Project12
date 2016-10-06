package com.floriantoenjes.recipe;

import com.floriantoenjes.Application;
import com.floriantoenjes.user.Role;
import com.floriantoenjes.user.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
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

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");


        User user = new User("user2", "password2",
                new Role("ROLE_USER"));
        user.setId(2L);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,null));

        mockMvc = MockMvcBuilders
                .standaloneSetup(recipeController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void listRecipes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/index"))
        .andExpect(MockMvcResultMatchers.view().name("index"))
        .andExpect(MockMvcResultMatchers.model().attribute("recipeMap", ));
    }

}