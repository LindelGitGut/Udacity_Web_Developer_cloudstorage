package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String getSignupPage(Model model, @ModelAttribute("user") UserModel user) {
        return "signup";
    }

    @PostMapping
    String registerUser(Model model, @ModelAttribute("user") UserModel user) {



        //TODO abfrage implementieren + Wenn verfügbar user erstellen
        if (userService.isUserAvailable(user.getUsername()) != false) {
            if (userService.createUser(user) != null) {
                model.addAttribute("success", true);
                model.addAttribute("errormsg", null);
            } else {
                model.addAttribute("errormsg", "Could not create User, please try again!");
                model.addAttribute("success", false);
            }
        } else {
            model.addAttribute("errormsg", "Username already exists!");
            model.addAttribute("success", false);
        }
        return "signup";
    }

}
