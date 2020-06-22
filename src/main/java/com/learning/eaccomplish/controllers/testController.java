package com.learning.eaccomplish.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/",""})
public class testController {


    @GetMapping
    public String test(Model model){
        model.addAttribute("name","Mohamed Essam");
        return "test";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model){
        model.addAttribute("name","Islam");
        return "adminPage";
    }

    @GetMapping("/user")
    public String getUser(Model model){
        model.addAttribute("name","Ahmed");
        return "UserPage";
    }

}
