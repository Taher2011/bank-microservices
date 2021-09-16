CREATE DATABASE bank_loan;

CREATE TABLE loans (
  loan_id int NOT NULL AUTO_INCREMENT,
  customer_id int NOT NULL,
  start_date date NOT NULL,
  loan_type varchar(255) NOT NULL,
  total_loan int NOT NULL,
  amount_paid int NOT NULL,
  outstanding_amount int NOT NULL,
  create_date date DEFAULT NULL,
  PRIMARY KEY (loan_id)
) ;





