package com.blawniczak.controller;

import com.blawniczak.domain.*;
import com.blawniczak.service.CardService;
import com.blawniczak.service.TicketService;
import com.blawniczak.service.TicketTypeService;
import com.blawniczak.service.UserService;
import com.blawniczak.util.EmailSender;
import com.blawniczak.validator.TicketValidator;
import com.blawniczak.validator.UserFormValidator;
import com.captcha.botdetect.web.servlet.Captcha;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping(value="")
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private UserFormValidator userFormValidator;

    @Autowired
    private TicketValidator ticketValidator;

    @Autowired
    private EmailSender emailSender;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationGET(Model model,  @PathParam("lang") String lang) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationPOST(@ModelAttribute("userForm") User user, BindingResult bindingResult, Model model,
                                   HttpServletRequest request) {

        Captcha captcha = Captcha.load(request, "exampleCaptcha");
        boolean isHuman = captcha.validate(request.getParameter("captchaCode"));
        if (!isHuman) {
            model.addAttribute("invalidCaptcha", "error.captcha");
            bindingResult.addError(new ObjectError("invalidCaptcha","Invalid Captcha"));
        }
        userFormValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        user.setToken(UUID.randomUUID());
        userService.save(user);
        emailSender.sendActivationLink(user);
        model.addAttribute("message", "You have been registered succesfully." +
                " Please confirm registration on email.");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(Model model, String error, String logout) {

        if (isLoggedIn()) return "redirect:/welcome";
        if (error != null)
            model.addAttribute("error", "Your username and password are invalid " +
                    "or you haven't activated your account yet!");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    public String confirmationGET(Model model, @PathParam("id") Long id, @PathParam("token") String token) {
        User user = userService.findById(id).orElse(null);
        if(user==null) {
            model.addAttribute("error", "User does not exist!");
        }
        else if(!UUID.fromString(token).equals(user.getToken())) {
            model.addAttribute("error", "Invalid token!");
        }
        else if(user.isEnabled()) {
            model.addAttribute("error", "This account has already been confirmed!");
        }
        else {
            user.setEnabled(true);
            userService.save(user);
            model.addAttribute("message", "You succesfully confirmed your registration");
        }
        return "login";
    }

    @RequestMapping(value = "/card", method = RequestMethod.GET)
    public String migawkaGET(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        if(user.getCard()==null) {
            model.addAttribute("notExist", "Your card doesn't exist!");
        } else if (!user.getCard().isEnabled()) {
            model.addAttribute("message", "Your card hasn't been enabled yet");
        }
        else {
            model.addAttribute("card", user.getCard());
            model.addAttribute("tickets", user.getCard().getTickets());
        }
        return "migawka";
    }

    @RequestMapping(value = "/card", method= RequestMethod.POST)
    public String migawkaPOST(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        Card card = new Card();
        card.setUser(user);
        cardService.save(card);
        user.setCard(card);
        userService.save(user);
        model.addAttribute("message", "Your card has been registered in system. Wait for confirmation");
        return "migawka";
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String ticketGET(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        if(user.getCard()==null) {
            model.addAttribute("notExist", "Your card doesn't exist!");
            return "migawka";
        } else {
            model.addAttribute("ticketTypes", ticketTypeService.findAll());
            model.addAttribute("ticketForm", new TicketForm());
            return "ticket";
        }
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    public String ticketPOST(@ModelAttribute("ticketForm") TicketForm ticketForm, BindingResult bindingResult, Model model) {
        ticketValidator.validate(ticketForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("ticketTypes", ticketTypeService.findAll());
            return "ticket";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        Ticket ticket = new Ticket();
        ticket.setCard(user.getCard());
        ticket.setStartDate(ticketForm.getStartDate());
        ticket.setHalfPrice(ticketForm.isHalfPrice());
        TicketType ticketType = ticketTypeService.findById(ticketForm.getTicketType()).orElse(null);
        if(ticketType != null) {
            ticket.setTicketType(ticketType);
            if(ticket.isHalfPrice()) {
                ticket.setPrice(ticketType.getPrice()/2);
            } else {
                ticket.setPrice(ticketType.getPrice());
            }
            ticket.setEndDate(new Date(ticket.getStartDate().getTime()+ticketType.getDuration()* 1000 * 60 * 60 * 24));
        }
        ticketService.save(ticket);
        model.addAttribute("message", "You have successfully bought a ticket!");
        return "migawka";
    }

    @RequestMapping(value = "/card/ticket/bill", method = RequestMethod.GET)
    public ModelAndView generatePDF(@PathParam("id") Long id) {
        Ticket ticket = ticketService.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView("pdfView", "ticket", ticket);
        return modelAndView;
    }

    private boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken))
            return true;
        return false;
    }

}
