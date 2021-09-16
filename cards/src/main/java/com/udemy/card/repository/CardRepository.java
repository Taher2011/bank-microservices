package com.udemy.card.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.card.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

	List<Card> findByCustomerId(int customerId);

}
