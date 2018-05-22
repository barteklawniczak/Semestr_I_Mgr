package com.blawniczak.controller;


import com.blawniczak.domain.Card;
import com.blawniczak.service.CardService;
import com.blawniczak.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private CardService cardService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/cards", method = RequestMethod.GET)
    public String cardsGET(Model model, String success, String error) {
        if(error != null)
            model.addAttribute("message", "Card with requested id does not exist!");
        if(success != null)
            model.addAttribute("message", "Card was sucessfully enabled!");
        model.addAttribute("cards", cardService.findAll());
        return "cards";
    }

    @RequestMapping(value = "/cards/enable", method = RequestMethod.GET)
    public String enableCardGET(@PathParam("id") Long id, Model model) {
        Card card = cardService.findById(id).orElse(null);
        if(card == null) {
            return "redirect:/employee/cards?error";
        } else {
            card.setEnabled(true);
            cardService.save(card);
            return "redirect:/employee/cards?success";
        }
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public String ticketsGET(Model model, String success, String error) {
        if(error != null)
            model.addAttribute("message", "Card with requested id does not exist!");
        if(success != null)
            model.addAttribute("message", "Card was sucessfully enabled!");
        model.addAttribute("tickets", ticketService.findAll());
        return "tickets";
    }
}
