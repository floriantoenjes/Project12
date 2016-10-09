package com.floriantoenjes.recipe;

import com.floriantoenjes.user.User;
import com.floriantoenjes.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Recipe.class)
public class RecipeEventHandler {
    private UserRepository users;

    @Autowired
    public RecipeEventHandler(UserRepository users) {
        this.users = users;
    }

    @HandleBeforeCreate
    public void addOwnerBasedOnLoggedInUser(Recipe recipe) {
        User user = getUser();
        recipe.setOwner(user);
    }

    private User getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return users.findByUsername(username);
    }

    @HandleBeforeSave
    public void addLastModifiedAsLoggedInUser(Recipe recipe) {
        User user = getUser();
        recipe.setOwner(user);
    }


}

