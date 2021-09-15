CREATE DATABASE bank_account;

CREATE TABLE accounts (
  customer_id int NOT NULL,
  account_number int NOT NULL AUTO_INCREMENT,
  account_type varchar(255) NOT NULL,
  branch_address varchar(255) NOT NULL,
  create_date date DEFAULT NULL,
  PRIMARY KEY (account_number)
) ;

CREATE TABLE customer (
  customer_id int NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  mobile_number varchar(20) NOT NULL,
  create_date date DEFAULT NULL,
  address varchar(255) DEFAULT NULL,
  age int NOT NULL,
  salary double NOT NULL,
  PRIMARY KEY (customer_id)
) ;



