CREATE DATABASE bank_card;

CREATE TABLE cards (
  card_id int NOT NULL AUTO_INCREMENT,
  card_number varchar(255) NOT NULL,
  customer_id int NOT NULL,
  card_type varchar(255) NOT NULL,
  total_limit int NOT NULL,
  amount_used int NOT NULL,
  avilable_amount int NOT NULL,
  create_date date DEFAULT NULL,
  PRIMARY KEY (card_id)
) ;




