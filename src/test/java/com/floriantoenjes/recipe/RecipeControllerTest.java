package com.floriantoenjes.recipe;

import com.floriantoenjes.Application;
import com.floriantoenjes.user.Role;
import com.floriantoenjes.user.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class RecipeControllerTest {
    private MockMvc mockMvc;

    @Autowired
    RecipeController recipeController;

    @Before
    public void setUp() throws Exception {
        User user = new User("user", "$2a$10$AGOk3D.U1cR719pUI4W98eUkEejDWH3/EpCa4vBzp8DE5OaYI9GfO",
                new Role("ROLE_USER"));
        user.setId(1L);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,null));
        mockMvc = MockMvcBuilders
                .standaloneSetup(recipeController)
                .build();
    }

    @Test
    public void listRecipes() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/index"));
    }

}