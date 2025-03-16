package com.uniovi.sdi.sdi2425entrega142;

import com.uniovi.sdi.sdi2425entrega142.errors.CustomAccessDeniedHandler;
import com.uniovi.sdi.sdi2425entrega142.services.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoggingService loggingService;  // ✅ Inyección de dependencia

    public WebSecurityConfig(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/script/**", "/", "/login/**").permitAll()
                .antMatchers("/empleado/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/vehiculo/add", "/vehiculo/list").hasAuthority("ROLE_ADMIN")
                .antMatchers("/vehiculo/listForEmpleadoEstandar").hasAuthority("ROLE_ESTANDAR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    boolean isAdmin = authentication.getAuthorities().stream()
                            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
                    if (isAdmin) {
                        response.sendRedirect("/empleado/list");
                    } else {
                        response.sendRedirect("/trayecto/list");
                    }
                })
                .and()
                .logout()
                .logoutUrl("/logout")  // Ruta explícita para logout
                .logoutSuccessHandler((request, response, authentication) -> {
                    if (authentication != null) {
                        String username = authentication.getName();
                        Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
                        logger.info("✅ Logout exitoso para usuario: {}", username);
                        loggingService.logLogout(username); // ✅ Se usa la instancia inyectada
                    }
                    response.sendRedirect("/login?logout");
                })
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
