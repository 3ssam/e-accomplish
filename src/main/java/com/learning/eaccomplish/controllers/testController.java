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
}
