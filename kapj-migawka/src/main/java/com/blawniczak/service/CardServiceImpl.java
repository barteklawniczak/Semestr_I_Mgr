package com.blawniczak.service;

import com.blawniczak.domain.Card;
import com.blawniczak.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public void save(Card card) { cardRepository.save(card); }

    @Override
    public List<Card> findAll() { return cardRepository.findAll(); }

    @Override
    public Optional<Card> findById(Long Id) { return cardRepository.findById(Id); }
}
