package com.learning.eaccomplish.controllers;

import com.learning.eaccomplish.models.*;
import com.learning.eaccomplish.repositories.ChildRepository;
import com.learning.eaccomplish.security.CurrentUser;
import com.learning.eaccomplish.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"signupUser", "islogin", "currentChild", "currentQuestion", "currentQuiz", "currentReport"})
public class HomeController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ChildService childService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ResultService resultService;


    @PostMapping("/child")
    public String addParent(@Valid Child child, BindingResult result, Model model) {
        if (result.hasErrors())
            return "AddChild";
        Parent parent = CurrentUser.getUser();
        childService.createChild(child, parent);
        return "HomePage";
    }

    @PostMapping("login/child")
    public String childLogin(@Valid Child child, BindingResult result, Model model) {
        if (result.hasErrors())
            return "OpenQuiz";
        Parent parent = parentService.getParent(CurrentUser.getId());
        Child existChild = childService.getChild(child.getName(), child.getPinCode(), parent);
        if (existChild == null) {
            model.addAttribute("LoginError", true);
            return "OpenQuiz";
        }
        model.addAttribute("currentChild", existChild);
        return "StartQuiz";
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

    @RequestMapping("quiz")
    public String startQuiz(Model model) {
        Child child = (Child) model.getAttribute("currentChild");
        Question question = child.getLastQuestion();
        Quiz quiz = child.getLastQuiz();
        model.addAttribute("currentQuestion", question);
        model.addAttribute("currentQuiz", quiz);
        String fullQuestion = question.getFullQuestion() + " = ";
        model.addAttribute("fullQuestion", fullQuestion);
        model.addAttribute("next", false);
        Report report = reportService.getReport(child, quiz);
        List<Report> existReport = reportService.getReport(child, LocalDate.now().toString());
        if (report == null)
            report = reportService.createReport(child, quiz);
        if (CheckTest(report, existReport)) {
            model.addAttribute("finishQuiz", true);
            return "StartQuiz";
        }
        Result result = new Result();
        result.setQuestion(question);
        result.setReport(report);
        model.addAttribute("currentReport", report);
        model.addAttribute("result", result);
        return "Quiz";
    }


    @PostMapping("answer")
    public String answer(@Valid Result result, BindingResult bindingResult, Model model) {
        Child child = (Child) model.getAttribute("currentChild");
        Question question = (Question) model.getAttribute("currentQuestion");
        Quiz quiz = (Quiz) model.getAttribute("currentQuiz");
        Report report = (Report) model.getAttribute("currentReport");
        if (bindingResult.hasErrors()) {
            model.addAttribute("next", false);
            model.addAttribute("fullQuestion", question.getFullQuestion() + " = ");
            return "Quiz";
        }

        long answer = Long.parseLong(result.getState());
        if (answer == question.getActualResult()) {
            result = resultService.createResult(question, report, "Right");
            model.addAttribute("answer", true);
            model.addAttribute("next", true);
            report.setNumOfRight(report.getNumOfRight() + 1);
            report.setTotalNumber(report.getTotalNumber() + 1);
        } else {
            result = resultService.createResult(question, report, "Wrong");
            model.addAttribute("answer", false);
            model.addAttribute("next", true);
            report.setNumOfWrong(report.getNumOfWrong() + 1);
            report.setTotalNumber(report.getTotalNumber() + 1);
        }
        reportService.editReport(report);
        question = resultService.getNextQuestion(question);
        child.setLastQuestion(question);
        if (report.getTotalNumber() == 30) {
            quiz = resultService.getNextQuiz(quiz);
            child.setLastQuiz(quiz);
            childService.EditChild(child);
            if (report.getNumOfRight() == 30)
                report.setState("Excellent");
            else if (report.getNumOfRight() >= 15)
                report.setState("Good");
            else
                report.setState("Poor");
            reportService.editReport(report);
            return "EndQuiz";
        }
        model.addAttribute("currentReport", report);
        model.addAttribute("currentQuestion", question);
        model.addAttribute("currentQuiz", quiz);
        childService.EditChild(child);
        model.addAttribute("currentChild", child);
        return "Quiz";
//        Parent parent = parentService.getParent(CurrentUser.getId());
//        Child existChild = childService.getChild(child.getName(), child.getPinCode(), parent);
//        if (existChild == null) {
//            model.addAttribute("LoginError", true);
//            return "OpenQuiz";
//        }
//        model.addAttribute("currentChild", existChild);
        //return "redirect:" + "/quiz";
    }

    private boolean CheckTest(Report report, List<Report> reports) {
        if (reports == null)
            return false;
        if (reports.size() == 1 && reports.get(0).getQuiz().getId() == report.getQuiz().getId())
            return false;
        if (reports.size() == 1 && reports.get(0).getQuiz().getId() != report.getQuiz().getId() && reports.get(0).getState().equalsIgnoreCase("Excellent"))
            return false;
        if (reports.size() == 2) {
            for (int i = 0; i < reports.size(); i++)
                if (reports.get(i).getQuiz().getId() == report.getQuiz().getId())
                    return false;
        }
        return true;
    }

}
