package com.experis.course.fotoalbum.security;

import com.experis.course.fotoalbum.model.User;
import com.experis.course.fotoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    // Dep. injection
    @Autowired
    UserRepository userRepository;

    // Override metodi
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // prendo la mail che l'utente inserisce e lo cerco su db
        Optional<User> loggedUser = userRepository.findByEmail(username);
        if (loggedUser.isPresent()){
            // utente trovato
            // creo un DBUserDeails con dati dell'utente
            return new DatabaseUserDetails(loggedUser.get());
        } else {
            // L'utente non c'è sollevo eccezione
            throw new UsernameNotFoundException(username);
        }
    }
}
