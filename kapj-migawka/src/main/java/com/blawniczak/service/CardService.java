package com.blawniczak.service;

import com.blawniczak.domain.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {

    void save(Card card);

    List<Card> findAll();

    Optional<Card> findById(Long Id);
}
