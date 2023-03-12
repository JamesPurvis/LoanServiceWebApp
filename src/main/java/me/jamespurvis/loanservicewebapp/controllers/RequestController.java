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
        double debtToIncome = (Double.parseDouble(monthlyExpenses) + Double.parseDouble(rentPayment)) / monthlyIncome * 100;
        double maxPaymentAmount = monthlyIncome * 0.42;
        double monthlyInterestRate = 0.21 / 12;

        double maxLoanAmount = maxPaymentAmount / (1 - Math.pow(1 + monthlyInterestRate, -72));

        if (maxLoanAmount < 500 || debtToIncome > 43) {
            return "request_denied";
        } else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Account> optionalAccount = accountService.findByEmail(userDetails.getUsername());
            Account account = optionalAccount.get();
            account.setMaxApprovedAmount(maxLoanAmount);
            accountService.save(account);
            return "request_approved";
        }
    }
}
