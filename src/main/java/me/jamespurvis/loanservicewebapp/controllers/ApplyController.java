package me.jamespurvis.loanservicewebapp.controllers;


import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class ApplyController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/apply")
    public String showApplyPage() {
        return "apply";
    }

    @PostMapping("/apply")
    public String registerNewUser(@ModelAttribute Account account) throws Exception {
        Optional<Account> accountExists = accountService.findByEmail(account.getEmail());

        if (accountExists.isPresent()) {
            return "redirect:/apply?email_in_use";
        }

        accountService.save(account);

        return "redirect:/login?success";
    }
}
