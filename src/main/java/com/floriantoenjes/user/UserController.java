package com.floriantoenjes.user;

import com.floriantoenjes.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/signup")
    public String signupForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());

        }
        return "signup";
    }

    // ToDo: Check if username is already taken
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/signup";
        }

        user.setRole(new Role("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Account %s has been created",
                user.getUsername()), FlashMessage.Status.SUCCESS));
        return "redirect:/login";
    }

    @RequestMapping("/profile")
    public String profile(Model model) {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("favorites", user.getFavorites());
        return "profile";
    }
}
