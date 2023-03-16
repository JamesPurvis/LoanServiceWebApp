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
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/manage/loan/{id}")
    public String showLoanPage(@PathVariable long id, Model model) {
        Optional<Loan> loan = loanService.findById(id);

        if (loan.isPresent()) {
            model.addAttribute("loanId", loan.get().getId());
            return "manage_loan";
        } else {
            return "404";
        }
    }
}
