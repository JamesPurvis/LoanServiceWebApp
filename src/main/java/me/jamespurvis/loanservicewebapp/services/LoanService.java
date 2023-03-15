package me.jamespurvis.loanservicewebapp.services;


import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.models.Loan;
import me.jamespurvis.loanservicewebapp.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {


    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> findAllByAccount(Account account) {
        return loanRepository.findAllByAccount(account);
    }

    public Loan save(Loan entity) {
        return loanRepository.save(entity);
    }
}
