package me.jamespurvis.loanservicewebapp.controllers;

import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.services.AccountService;
import me.jamespurvis.loanservicewebapp.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
public class DashboardController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;

    @PreAuthorize("isAuthenticated()")
    
    @RequestMapping("/dashboard")
    public String showDashboard(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> optionalAccount = accountService.findByEmail(userDetails.getUsername());

        model.addAttribute("lastPaymentDate", optionalAccount.get().getLastPaymentDate());
        model.addAttribute("amountOfLoans", loanService.amountofLoans(optionalAccount.get()));
        model.addAttribute("totalBalance", loanService.totalAmountOwed(optionalAccount.get()));
        model.addAttribute("paymentDueDate", optionalAccount.get().getPaymentDueDate());
        return "dashboard";
    }
}
