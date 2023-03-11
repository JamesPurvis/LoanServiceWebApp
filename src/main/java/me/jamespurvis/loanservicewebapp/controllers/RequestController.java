package me.jamespurvis.loanservicewebapp.controllers;


import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RequestController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/request")
    public String showRequestPage() {
        return "request";
    }

    @PostMapping("/request")

    public String processLoanRequest(@RequestParam("annualIncome") String annualIncome, @RequestParam("monthlyExpenses") String monthlyExpenses, @RequestParam("rentPayment") String rentPayment) throws Exception {
        double monthlyIncome = Double.parseDouble(annualIncome) / 12;
        double debtToIncome = Double.parseDouble(monthlyExpenses) + Double.parseDouble(rentPayment) / monthlyIncome;
        double maxPaymentAmount = monthlyIncome * debtToIncome;
        double maxLoanAmount =  maxPaymentAmount * ((1 - (1 / (1 + 24) ^ 64)) / 24);

        System.out.println(maxLoanAmount);

        if (maxLoanAmount < 500) {
            return "request_denied";
        }
        else {
            System.out.println(maxLoanAmount);
            UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            Optional<Account> accountOptional = accountService.findByEmail(details.getUsername());
            accountOptional.get().setMaxApprovedAmount(maxPaymentAmount);
            accountService.save(accountOptional.get());
            return "request_approved";
        }
    }
}
