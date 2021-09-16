package com.udemy.loan.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the loans database table.
 * 
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "loan_id")
	private int loanId;

	@Column(name = "amount_paid")
	private int amountPaid;

	@Column(name = "create_date")
	private LocalDate createDate;

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "loan_type")
	private String loanType;

	@Column(name = "outstanding_amount")
	private int outstandingAmount;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "total_loan")
	private int totalLoan;

}