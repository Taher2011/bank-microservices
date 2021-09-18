package com.udemy.card.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.udemy.card.config.CardServiceConfig;
import com.udemy.card.entity.Card;
import com.udemy.card.exception.CardServiceException;
import com.udemy.card.exception.ErrorCode;
import com.udemy.card.model.CardDTO;
import com.udemy.card.model.Property;
import com.udemy.card.repository.CardRepository;

@Service
public class CardService {

	private static Logger logger = LoggerFactory.getLogger(CardService.class);

	private final CardRepository cardRepository;

	private final CardServiceConfig cardServiceConfig;

	private final Random random;

	public CardService(CardRepository cardRepository, CardServiceConfig cardServiceConfig, Random random) {
		super();
		this.cardRepository = cardRepository;
		this.cardServiceConfig = cardServiceConfig;
		this.random = random;
	}

	public List<CardDTO> getCardsForCustomer(String traceId, int customerId) throws CardServiceException {
		logger.debug("traceId is {} ", traceId);
		logger.info("started getting customer card details for customerId {} ", customerId);
		List<Card> cards = cardRepository.findByCustomerId(customerId);
		List<CardDTO> cardsDTO = null;
		if (!ObjectUtils.isEmpty(cards)) {
			cardsDTO = cards.stream().map(e -> {
				CardDTO cardDTO = new CardDTO();
				cardDTO.setCardId(e.getCardId());
				cardDTO.setCustomerId(e.getCustomerId());
				cardDTO.setCardNumber(e.getCardNumber());
				cardDTO.setCardType(e.getCardType());
				cardDTO.setTotalLimit(e.getTotalLimit());
				cardDTO.setAmountUsed(e.getAmountUsed());
				cardDTO.setAvilableAmount(e.getAvilableAmount());
				cardDTO.setCreateDate(e.getCreateDate());
				return cardDTO;
			}).collect(Collectors.toList());
			logger.info("completed getting customer card details for customerId {} ", customerId);
			return cardsDTO;
		} else {
			logger.error("Customer id {} not found", customerId);
			throw new CardServiceException(ErrorCode.CUSTOMER_NOT_FOUND);
		}
	}

	public void createCard(CardDTO cardDTO) {
		logger.info("started creating customer card details for customerId {} ", cardDTO.getCustomerId());
		Card card = new Card();
		card.setCustomerId(cardDTO.getCustomerId());
		card.setCardId(random.nextInt(1000));
		card.setAmountUsed(cardDTO.getAmountUsed());
		card.setAvilableAmount(cardDTO.getAvilableAmount());
		card.setCardNumber(UUID.randomUUID().toString());
		card.setCardType(cardDTO.getCardType());
		card.setTotalLimit(cardDTO.getTotalLimit());
		card.setCreateDate(cardDTO.getCreateDate());
		logger.info("completed creating customer card details for customerId {} ", cardDTO.getCustomerId());
		cardRepository.save(card);
	}

	public void createCards(List<CardDTO> cardDTOs) {
		logger.info("started creating customer card details for customers");
		List<Card> cards = cardDTOs.stream().map(e -> {
			Card card = new Card();
			card.setCustomerId(e.getCustomerId());
			card.setCardId(random.nextInt(1000));
			card.setAmountUsed(e.getAmountUsed());
			card.setAvilableAmount(e.getAvilableAmount());
			card.setCardNumber(UUID.randomUUID().toString());
			card.setCardType(e.getCardType());
			card.setTotalLimit(e.getTotalLimit());
			card.setCreateDate(e.getCreateDate());
			return card;
		}).collect(Collectors.toList());
		logger.info("completed creating customer card details for customerId");
		cardRepository.saveAll(cards);
	}

	public List<CardDTO> getCards() {
		logger.info("started getting customer card details");
		List<Card> cards = cardRepository.findAll();
		if (ObjectUtils.isNotEmpty(cards)) {
			List<CardDTO> cardDTOs = new ArrayList<>();
			cards.stream().forEach(e -> {
				CardDTO cardDTO = new CardDTO();
				cardDTO.setCustomerId(e.getCustomerId());
				cardDTO.setAmountUsed(e.getAmountUsed());
				cardDTO.setAvilableAmount(e.getAvilableAmount());
				cardDTO.setCardId(e.getCardId());
				cardDTO.setCardNumber(e.getCardNumber());
				cardDTO.setCardType(e.getCardType());
				cardDTO.setTotalLimit(e.getTotalLimit());
				cardDTO.setCreateDate(e.getCreateDate());
				cardDTOs.add(cardDTO);
			});
			logger.info("completed getting customer card details");
			return cardDTOs;
		}
		logger.info("completed getting customer card details");
		return Collections.emptyList();
	}

	public String getCardsProperties() throws JsonProcessingException {
		logger.info("started getting card properties");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Property property = Property.builder().msg(cardServiceConfig.getMsg())
				.buildVersion(cardServiceConfig.getBuildVersion()).mailDetails(cardServiceConfig.getMailDetails())
				.activeBranches(cardServiceConfig.getActiveBranches()).build();
		logger.info("completed getting card properties");
		return ow.writeValueAsString(property);
	}

}
