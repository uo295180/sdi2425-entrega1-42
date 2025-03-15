package com.uniovi.sdi.sdi2425entrega142.logging;

import com.uniovi.sdi.sdi2425entrega142.services.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthenticationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEventListener.class);
    private final LoggingService loggingService;

    public AuthenticationEventListener(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Component
    public static class RequestLoggingFilter extends OncePerRequestFilter {

        private final LoggingService loggingService;

        // Lista de rutas que queremos registrar (ajústala según tu sistema)
        private static final List<String> CONTROLADOR_PATHS = Arrays.asList(
                "/login", "/logout", "/empleado", "/admin", "/vehiculo", "/trayecto", "/repostajes"
        );

        public RequestLoggingFilter(LoggingService loggingService) {
            this.loggingService = loggingService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {
            String method = request.getMethod();
            String uri = request.getServletPath(); // Obtener solo la ruta del controlador
            String params = request.getQueryString() != null ? request.getQueryString() : "Sin parámetros";

            // Filtrar solo las peticiones a controladores específicos
            if (shouldLog(uri)) {
                String logMessage = "Solicitud HTTP: " + method + " " + uri + " | Parámetros: " + params;
                loggingService.logRequest(uri, method, params);
                logger.info(logMessage);
            }

            filterChain.doFilter(request, response);
        }


        private boolean shouldLog(String uri) {
            // Evita logs de recursos estáticos y endpoints internos de Spring
            if (uri.startsWith("/static/") || uri.startsWith("/resources/") ||
                    uri.startsWith("/css/") || uri.startsWith("/js/") ||
                    uri.startsWith("/images/") || uri.startsWith("/favicon.ico") ||
                    uri.startsWith("/error") || uri.startsWith("/h2-console")) {
                return false;
            }

            // Solo permitir logs si la ruta comienza con un prefijo de controlador válido
            return CONTROLADOR_PATHS.stream().anyMatch(uri::startsWith);
        }
    }

    @Component
    public static class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
        private final LoggingService loggingService;

        public AuthenticationSuccessListener(LoggingService loggingService) {
            this.loggingService = loggingService;
        }

        @Override
        public void onApplicationEvent(AuthenticationSuccessEvent event) {
            Authentication authentication = event.getAuthentication();
            String username = authentication.getName();
            loggingService.logLoginSuccess(username);
            logger.info("✅ Inicio de sesión exitoso: {}", username);
        }
    }

    @Component
    public static class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
        private final LoggingService loggingService;

        public AuthenticationFailureListener(LoggingService loggingService) {
            this.loggingService = loggingService;
        }

        @Override
        public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
            Object principal = event.getAuthentication().getPrincipal();
            String username = (principal instanceof String) ? (String) principal : "desconocido";

            loggingService.logLoginFailure(username);
            logger.warn("❌ Intento fallido de login: {}", username);
        }
    }


    public void logUserCreation(String dni) {
        loggingService.logUserCreation(dni);
        logger.info("👤 Nuevo usuario registrado: {}", dni);
    }

    @Component
    public static class LogoutEventListener implements LogoutHandler, LogoutSuccessHandler {

        private static final Logger logger = LoggerFactory.getLogger(LogoutEventListener.class);
        private final LoggingService loggingService;

        public LogoutEventListener(LoggingService loggingService) {
            this.loggingService = loggingService;
        }

        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            if (authentication != null) {
                String username = authentication.getName();
                logger.info("🔹 logout() ejecutado para usuario: {}", username);
                loggingService.logLogout(username);
            } else {
                logger.warn("⚠️ logout() ejecutado pero authentication es NULL");
            }
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            String username = (authentication != null) ? authentication.getName() : "desconocido";

            logger.info("✅ Logout exitoso para: {}", username);
            loggingService.logLogout(username);

            // Redirigir a la página de login con mensaje de logout exitoso
            response.sendRedirect("/login?logout");
        }
    }


}
