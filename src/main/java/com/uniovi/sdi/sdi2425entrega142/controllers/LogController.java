package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.LogEntry;
import com.uniovi.sdi.sdi2425entrega142.repository.LogRepository;
import com.uniovi.sdi.sdi2425entrega142.services.LoggingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LogController {
    private final LoggingService loggingService;

    public LogController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @RequestMapping("/admin/logs/list")
    public String getLogs(@RequestParam(required = false) String tipo, Model model) {
        if (tipo != null) {
            model.addAttribute("logs", loggingService.getLogs(tipo));
        }

        return "admin/logs";

    }

    @RequestMapping("/admin/logs/delete")
    public String deleteLogs(@RequestParam String tipo, Model model) {
        if (tipo != null) {
            loggingService.deleteLogs(tipo);
            model.addAttribute("logs", loggingService.getLogs(tipo));
        }

        return "admin/logs";
    }
}

