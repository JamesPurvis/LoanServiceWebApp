package me.jamespurvis.loanservicewebapp.services;


import me.jamespurvis.loanservicewebapp.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component("UserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Account> optionalAccount = accountService.findByEmail(email);

        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("A user with the email address " + email + " " + "was not found!");
        }

        Account account = optionalAccount.get();

        return new User(account.getEmail(), account.getPassword(), Collections.emptyList());
    }
}
