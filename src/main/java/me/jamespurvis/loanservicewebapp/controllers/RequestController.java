package me.jamespurvis.loanservicewebapp.controllers;


import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.models.Loan;
import me.jamespurvis.loanservicewebapp.services.AccountService;
import me.jamespurvis.loanservicewebapp.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
public class RequestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;


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

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> optionalAccount = accountService.findByEmail(userDetails.getUsername());
        Account account = optionalAccount.get();
        double amountOwed = loanService.totalAmountOwed(account);

        if (maxLoanAmount < 250 || debtToIncome > 43 || amountOwed + 250 > maxLoanAmount ) {
            return "request_denied";
        } else {
            account.setMaxApprovedAmount(maxLoanAmount);
            accountService.save(account);

            return "redirect:/request/approved";
        }
    }

    @GetMapping("/request/approved")

    public String showApprovedPage(Model model) {
        List<Double> loanAmounts = new ArrayList<>();

        loanAmounts.add(250.00);
        loanAmounts.add(500.00);
        loanAmounts.add(750.00);
        loanAmounts.add(1000.00);
        loanAmounts.add(1250.00);
        loanAmounts.add(1500.00);
        loanAmounts.add(2000.00);

        UserDetails currentSession = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> optionalAccount = accountService.findByEmail(currentSession.getUsername());
        Account account = optionalAccount.get();
        double maxLoanAmount = account.getMaxApprovedAmount();
        double totalOwed = loanService.totalAmountOwed(account);

        model.addAttribute("loanAmounts", loanAmounts);
        model.addAttribute("maxLoanAmount", maxLoanAmount);
        model.addAttribute("totalOwed", totalOwed);
        return "request_approved";
    }

    @PostMapping("/request/approved")
    public String confirmSelection(@ModelAttribute("loan") Loan loan) {
        UserDetails currentSession = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> optionalAccount = accountService.findByEmail(currentSession.getUsername());
        loan.setAccount(optionalAccount.get());
        loan.setLoanStatus("CREATED");
        loan.setPaymentMethod("CREDIT/DEBIT CARD");
        optionalAccount.get().setPaymentDueDate(LocalDateTime.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")).toString());
        loanService.save(loan);

        return "redirect:/dashboard?loan_success";
    }
}
