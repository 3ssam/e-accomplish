package com.learning.eaccomplish.controllers;

import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.models.Payment;
import com.learning.eaccomplish.services.ParentService;
import com.learning.eaccomplish.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
@SessionAttributes("signupUser")
public class SignUpController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private PaymentService paymentService;


    @PostMapping("/parent")
    public String addParent(@Valid Parent parent, BindingResult result, Model model) {
        if (result.hasErrors())
            return "AddParent";
        if (parentService.mailIsExist(parent)) {
            model.addAttribute("isExist",true);
            return "AddParent";
        }
        parentService.createParent(parent);
        model.addAttribute("signupUser",parent);
        Payment payment = new Payment();
        payment.setParent(parent);
        model.addAttribute("payment",payment);
        return "AddPayment";
    }

    @PostMapping("/payment")
    public String addPayment(@Valid Payment payment,  BindingResult result, Model model) {
        if (result.hasErrors())
            return "AddPayment";
        Parent parent = (Parent) model.getAttribute("signupUser");
        payment.setParent(parent);
        paymentService.createPayment(payment,parent);
        return "redirect:" + "/login";
    }


    @GetMapping()
    public String showSignUpForm(Parent parent) {
        return "AddParent";
    }


}
