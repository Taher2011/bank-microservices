package com.udemy.loan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.loan.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

	List<Loan> findByCustomerIdOrderByStartDate(int customerId);

	Optional<Loan> findByCustomerIdAndLoanId(int customerId, int loanNumber);

}
