package com.floriantoenjes.user;

import com.floriantoenjes.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/signup")
    public String signupForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@RequestParam("passwordAgain") String passwordAgain,
                         @Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/signup";
        }

        if (!passwordAgain.equals(user.getPassword())) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Please enter the same password twice",
                    FlashMessage.Status.FAILED));
            return "redirect:/signup";
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("This username has already been taken",
                    FlashMessage.Status.FAILED));
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
