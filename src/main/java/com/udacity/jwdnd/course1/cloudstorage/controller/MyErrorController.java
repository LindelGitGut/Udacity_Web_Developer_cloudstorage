package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    UserService userService;

    public MyErrorController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/error")
    public String handleError(Model model) {

        try {
            userService.getCurrentUser();
            model.addAttribute("homeredirect", true);
            return "error";
        } catch (Exception e) {
            model.addAttribute("loginredirect", true);
            //do something like logging
            return "error";
        }


    }
}