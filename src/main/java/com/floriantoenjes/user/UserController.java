package com.floriantoenjes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/signup")
    public String signupForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());

        }
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("org.springframework.validation.BindingResult.user", user);
            redirectAttributes.addAttribute("user", user);
            return "redirect:/signup";
        }

        user.setRole(new Role("ROLE_USER"));
        userService.save(user);
        return "redirect:/index";
    }

    @RequestMapping("/profile")
    public String profile(Model model) {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("favorites", user.getFavorites());
        return "profile";
    }
}
