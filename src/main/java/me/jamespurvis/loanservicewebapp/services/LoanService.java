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

    public double totalAmountOwed(Account account) {
        List<Loan> loans = loanRepository.findAllByAccount(account);
        double totalAmount = 0;

        for(Loan loan : loans) {
            totalAmount+=loan.getLoanAmount();
        }

        return totalAmount;
    }

    public int amountofLoans(Account account) {
        return loanRepository.findAllByAccount(account).size();
    }

    public Loan save(Loan entity) {
        return loanRepository.save(entity);
    }
}
