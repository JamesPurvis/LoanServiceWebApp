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

    @Autowired
    private IdentifierEncryptionService identifierEncryptionService;

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


    public Account save(Account entity) throws Exception {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setPersonalIdentifier(identifierEncryptionService.encrypt(entity.getPersonalIdentifier()));
        return accountRepository.save(entity);
    }


}
