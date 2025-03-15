package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.LogEntry;
import com.uniovi.sdi.sdi2425entrega142.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);
    private final LogRepository logRepository;

    public LoggingService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void logRequest(String mapping, String method, String params) {
        String message = "[PET] " + LocalDateTime.now() + " - Mapping: " + mapping + ", Method: " + method + ", Params: " + params;
        logger.info(message);
        saveToDatabase("PET", message);
    }

    public void logUserCreation(String userDni) {
        String message = "[ALTA] " + LocalDateTime.now() + " - Alta de nuevo empleado: " + userDni;
        logger.info(message);
        saveToDatabase("ALTA", message);
    }

    public void logLoginSuccess(String userDni) {
        String message = "[LOGIN-EX] " + LocalDateTime.now() + " - Usuario autenticado: " + userDni;
        logger.info(message);
        saveToDatabase("LOGIN-EX", message);
    }

    public void logLoginFailure(String userDni) {
        String message = "[LOGIN-ERR] " + LocalDateTime.now() + " - Fallo en autenticación: " + userDni;
        logger.warn(message);
        saveToDatabase("LOGIN-ERR", message);
    }

    public void logLogout(String userDni) {
        String message = "[LOGOUT] " + LocalDateTime.now() + " - Cierre de sesión de: " + userDni;
        logger.info(message);
        saveToDatabase("LOGOUT", message);
    }

    private void saveToDatabase(String logType, String message) {
        LogEntry logEntry = new LogEntry();
        logEntry.setTipoLog(logType);
        logEntry.setDescripcion(message);
        logEntry.setFechaHora(LocalDateTime.now());
        logRepository.save(logEntry);
    }

    public List<LogEntry> getLogs(String tipo) {
        if (tipo.equals("ALL")) {
            return logRepository.findAll();
        }
        return logRepository.findByTipoLogOrderByFechaHoraDesc(tipo);

    }

    public void deleteLogs(String tipo) {
        logRepository.deleteAll(logRepository.findByTipoLogOrderByFechaHoraDesc(tipo));
    }
}
