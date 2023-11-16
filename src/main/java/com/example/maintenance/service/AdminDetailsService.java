package com.example.maintenance.service;

import com.example.maintenance.config.AdminDetails;
import com.example.maintenance.entity.Administrator;
import com.example.maintenance.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdministratorRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Administrator> userInfo = repository.findByName(username);
        return userInfo.map(AdminDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
