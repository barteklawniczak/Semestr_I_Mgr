package com.blawniczak.service;

import com.blawniczak.domain.TicketType;
import com.blawniczak.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Override
    public List<TicketType> findAll() { return ticketTypeRepository.findAll(); }

    @Override
    public Optional<TicketType> findById(Long id) { return ticketTypeRepository.findById(id); }

}
