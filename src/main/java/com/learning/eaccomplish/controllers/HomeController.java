package com.learning.eaccomplish.controllers;

import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.models.Payment;
import com.learning.eaccomplish.models.User;
import com.learning.eaccomplish.security.CurrentUser;
import com.learning.eaccomplish.services.ParentService;
import com.learning.eaccomplish.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
//@SessionAttributes("currentUser")
public class HomeController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private PaymentService paymentService;


//    @PostMapping("/parent")
//    public String addParent(@Valid Parent parent, @RequestParam(required = false) Boolean active, BindingResult result, Model model) {
//        if (result.hasErrors())
//            return "AddParent";
//        Parent as = (Parent) model.getAttribute("currentUser");
//
//        return "AddPayment";
//    }
//
//    @PostMapping("/payment")
//    public String addPayment(@Valid Payment payment,  BindingResult result, Model model) {
//        if (result.hasErrors())
//            return "EditPayment";
//        Parent parent = (Parent) model.getAttribute("currentUser");
//        Payment tempPayment = paymentService.getPaymentByParent(parent);
//        return "HomePage";
//    }
//
//    @GetMapping("/payment")
//    public String deactivatedPayment(@RequestParam(required = false) boolean active, Model model) {
//        Parent parent = (Parent) model.getAttribute("currentUser");
//        Payment payment = paymentService.getPaymentByParent(parent);
//        payment.setActive(active);
//        paymentService.EditPayment(payment,parent);
//        return "HomePage";
//    }



    @GetMapping()
    public String home(@RequestParam(required = false) String choose,Model model) {
        User user = CurrentUser.getUser();
        Parent parent = parentService.getParent(CurrentUser.getId());
        if (choose == null)
            return "HomePage";
//        if (choose.equalsIgnoreCase("Deactivated")){
//            parent.setActivated(false);
//            parentService.EditParent(parent);
//            return "logout";
//        }
//        else if (choose.equalsIgnoreCase("Payment")){
//            Payment payment = paymentService.getPaymentByParent(parent);
//            model.addAttribute("payment",payment);
//            return "EditPayment";
//        }
//        else if (choose.equalsIgnoreCase("Children"));
//        else if (choose.equalsIgnoreCase("Quiz"));
        return "HomePage";
    }

    @RequestMapping("login")
    public String login() {
        return "Login";
    }

    @RequestMapping("logout")
    public String logout() {
        return "Login";
    }

}
