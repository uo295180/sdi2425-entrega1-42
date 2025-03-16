package com.uniovi.sdi.sdi2425entrega142.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/forbidden")
    public String forbidden() {
        return "forbidden"; // Apunta a forbidden.html
    }
}
