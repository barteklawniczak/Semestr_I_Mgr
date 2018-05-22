package com.blawniczak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="")
public class HomeController {

    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public String homeGET() { return "home"; }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomeGET() {
        return "welcome";
    }
}
