package com.uniovi.sdi.sdi2425entrega142.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
    String[] roles = {"ROLE_ESTANDAR", "ROLE_ADMIN"};

    public String[] getRoles() {
        return roles;
    }
}
