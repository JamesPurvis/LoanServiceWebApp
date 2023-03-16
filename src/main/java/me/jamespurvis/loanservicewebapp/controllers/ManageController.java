package me.jamespurvis.loanservicewebapp.controllers;


import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.models.Loan;
import me.jamespurvis.loanservicewebapp.services.AccountService;
import me.jamespurvis.loanservicewebapp.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ManageController {


    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/manage")
    public String showManagePage(Model model) {
        UserDetails currentSession = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account current = accountService.findByEmail(currentSession.getUsername()).get();
        List<Loan> loans = loanService.findAllByAccount(current);
        model.addAttribute("loans", loans);
        return "manage";
    }
}
