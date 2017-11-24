package com.floriantoenjes.user;

import com.floriantoenjes.core.BaseEntity;
import com.floriantoenjes.recipe.Recipe;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User extends BaseEntity implements UserDetails {

    @Size(min = 4, max = 20)
    private String username;

    @Size(min = 8, max = 250)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Recipe> recipes;

    @ManyToMany(mappedBy = "usersFavorited", cascade = CascadeType.ALL)
    private List<Recipe> favorites;

    public User(){}

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        role.setUser(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        role.setUser(this);
        this.role = role;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipe.setOwner(this);
        recipes.add(recipe);
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Recipe> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(Recipe recipe) {
        favorites.add(recipe);
    }

    public void removeFavorite(Recipe recipe) {
        favorites.remove(recipe);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username != null ? username.equals(user.username) : user.username == null;

    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
