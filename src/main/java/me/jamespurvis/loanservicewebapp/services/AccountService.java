package me.jamespurvis.loanservicewebapp.services;


import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


    public Account save(Account entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return accountRepository.save(entity);
    }


}
