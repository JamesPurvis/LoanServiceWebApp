package me.jamespurvis.loanservicewebapp.repositories;

import me.jamespurvis.loanservicewebapp.models.Account;
import me.jamespurvis.loanservicewebapp.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByAccount(Account account);
}
