package com.learning.eaccomplish.controllers;

import com.learning.eaccomplish.models.Child;
import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.models.Payment;
import com.learning.eaccomplish.repositories.ChildRepository;
import com.learning.eaccomplish.security.CurrentUser;
import com.learning.eaccomplish.services.ChildService;
import com.learning.eaccomplish.services.ParentService;
import com.learning.eaccomplish.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"signupUser", "islogin","currentChild"})
public class HomeController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ChildService childService;


    @PostMapping("/child")
    public String addParent(@Valid Child child, BindingResult result, Model model) {
        if (result.hasErrors())
            return "AddChild";
        Parent parent = CurrentUser.getUser();
        childService.createChild(child, parent);
        return "HomePage";
    }

    @PostMapping("login/child")
    public String childLogin(@Valid Child child, Model model) {
        Parent parent = parentService.getParent(CurrentUser.getId());
        Child existChild = childService.getChild(child.getName(), child.getPinCode(), parent);
        if (existChild == null) {
            model.addAttribute("LoginError", true);
            return "OpenQuiz";
        }
        model.addAttribute("currentChild", existChild);
        return "HomePage";
    }


    @PostMapping("/payment")
    public String addPayment(@Valid Payment payment, BindingResult result, Model model) {
        if (result.hasErrors())
            return "EditPayment";
        Parent parent = CurrentUser.getUser();
        paymentService.EditPayment(payment, parent);
        //Payment tempPayment = paymentService.getPaymentByParent(parent);
        return "HomePage";
    }

    @GetMapping("/payment")
    public String deactivatedPayment(@RequestParam(required = false) boolean active, Model model) {
        Parent parent = CurrentUser.getUser();
        Payment payment = CurrentUser.getUser().getPayment();
        paymentService.cancelPayment(payment, parent);
        return "HomePage";
    }

    @GetMapping()
    public String home(@RequestParam(required = false) String choose, Model model) {
        Parent parent = parentService.getParent(CurrentUser.getId());
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
        } else if (choose.equalsIgnoreCase("Children")) {
            List<Child> childrens = parent.getChildren();
            model.addAttribute("childrens", childrens);
            model.addAttribute("isFull", false);

            return "ShowChildern";
        } else if (choose.equalsIgnoreCase("addChild")) {
            if (parent.getChildren().size() == 2) {
                List<Child> childrens = parent.getChildren();
                model.addAttribute("childrens", childrens);
                model.addAttribute("isFull", true);
                return "ShowChildern";
            }
            Child child = new Child();
            model.addAttribute("child", child);
            return "AddChild";
        } else if (choose.equalsIgnoreCase("Quiz")) {
            Child child = new Child();
            model.addAttribute("child", child);
            model.addAttribute("LoginError", false);
            return "OpenQuiz";
        }
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

    @RequestMapping("child/delete/{id}")
    public String deleteChild(@PathVariable("id") long id, Model model) {
        childService.deleteChild(id);
//        Parent parent = parentService.getParent(CurrentUser.getId());
//        List<Child> childrens = parent.getChildren();
//        model.addAttribute("childrens", childrens);
//        model.addAttribute("isFull", false);
        return "HomePage";
    }


}
