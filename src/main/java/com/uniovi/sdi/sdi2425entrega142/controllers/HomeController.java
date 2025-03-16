package com.uniovi.sdi.sdi2425entrega142.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    public HomeController() {}

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/home")
    public String home(){ return "home"; }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(HttpServletRequest request)
    {
        if(request.getUserPrincipal() != null)
            return "redirect:/home";
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "login";}
}
