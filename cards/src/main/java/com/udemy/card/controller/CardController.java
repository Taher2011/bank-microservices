package com.udemy.card.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.udemy.card.exception.CardServiceException;
import com.udemy.card.model.CardDTO;
import com.udemy.card.service.CardService;

@RestController
@RequestMapping("/v1/cards")
public class CardController {

	private final CardService cardService;

	public CardController(CardService cardService) {
		super();
		this.cardService = cardService;
	}

	@GetMapping("/customers")
	public ResponseEntity<List<CardDTO>> getCards() {
		return new ResponseEntity<>(cardService.getCards(), HttpStatus.OK);
	}

	@GetMapping("/customers/{customer-id}")
	public ResponseEntity<List<CardDTO>> getCardsForCustomer(
			@RequestHeader(required = false, name = "trace-id") String traceId,
			@PathVariable(name = "customer-id") int customerId) throws CardServiceException {
		return new ResponseEntity<>(cardService.getCardsForCustomer(traceId, customerId), HttpStatus.OK);
	}

	@PostMapping("/customers/{customer-id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createCard(@PathVariable(name = "customer-id") int customerId, @RequestBody CardDTO card) {
		cardService.createCard(customerId, card);
	}

	@DeleteMapping("/customers/{customer-id}/card/{card-number}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAccount(@PathVariable(name = "customer-id") int customerId,
			@PathVariable(name = "card-number") String cardNumber) throws CardServiceException {
		cardService.deleteCard(customerId, cardNumber);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public void createAccounts(@RequestBody List<CardDTO> cards) {
		cardService.createCards(cards);
	}

	@GetMapping("/properties")
	public ResponseEntity<String> getCardsProperties() throws JsonProcessingException {
		return new ResponseEntity<>(cardService.getCardsProperties(), HttpStatus.OK);
	}

}
