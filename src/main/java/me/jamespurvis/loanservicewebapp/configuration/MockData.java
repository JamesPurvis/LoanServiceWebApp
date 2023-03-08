package me.jamespurvis.loanservicewebapp.configuration;


import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MockData implements CommandLineRunner {

    @Autowired
    private AccountService accountService;


    @Override
    public void run(String... args) throws Exception {
        Account account1 = new Account();

        account1.setEmail("admin@admin.com");
        account1.setPassword("lol123");
        account1.setFirstName("James");
        account1.setLastName("Purvis");
        account1.setDateOfBirth(new Date("01/01/1993"));

        accountService.save(account1);
    }
}
