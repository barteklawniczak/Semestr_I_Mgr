package com.blawniczak.service;

import com.blawniczak.domain.TicketType;

import java.util.List;
import java.util.Optional;

public interface TicketTypeService {

    List<TicketType> findAll();

    Optional<TicketType> findById(Long id);

}
