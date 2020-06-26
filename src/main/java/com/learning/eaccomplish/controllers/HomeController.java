package com.learning.eaccomplish.controllers;

import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.models.Payment;
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
@SessionAttributes({"signupUser", "islogin"})
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
    @PostMapping("/payment")
    public String addPayment(@Valid Payment payment,  BindingResult result, Model model) {
        if (result.hasErrors())
            return "EditPayment";
        Parent parent = CurrentUser.getUser();
        paymentService.EditPayment(payment,parent);
        //Payment tempPayment = paymentService.getPaymentByParent(parent);
        return "HomePage";
    }

    @GetMapping("/payment")
    public String deactivatedPayment(@RequestParam(required = false) boolean active, Model model) {
        Parent parent = CurrentUser.getUser();
        Payment payment = CurrentUser.getUser().getPayment();
        paymentService.cancelPayment(payment,parent);
        return "HomePage";
    }


    @GetMapping()
    public String home(@RequestParam(required = false) String choose, Model model) {
        Parent parent = CurrentUser.getUser();
        if (choose == null)
            return "HomePage";
        if (choose.equalsIgnoreCase("Deactivated")) {
            parent.setActivated(false);
            parentService.EditParent(parent);
            model.addAttribute("isLocked", true);
            return "redirect:" + "/logout";
        } else if (choose.equalsIgnoreCase("Payment")) {
            Payment payment = parent.getPayment();
            if (payment == null)
                payment = paymentService.getPaymentByParent(parent);
            if (payment == null) {
                model.addAttribute("signupUser", parent);
                payment = new Payment();
                payment.setParent(parent);
                model.addAttribute("payment", payment);
                model.addAttribute("islogin", true);
                return "AddPayment";
            }
            model.addAttribute("payment", payment);
            return "EditPayment";
        }
//        else if (choose.equalsIgnoreCase("Children"));
//        else if (choose.equalsIgnoreCase("Quiz"));
        return "HomePage";
    }

    @RequestMapping("login")
    public String login(Model model) {
        Object o = model.getAttribute("isLocked");
        if (o != null)
            model.addAttribute("isLocked", true);
        return "Login";
    }

    @RequestMapping("logout")
    public String logout(Model model) {
        if (model.getAttribute("isLocked") != null)
            model.addAttribute("isLocked", true);
        return "Login";
    }

}
