package com.experis.course.fotoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {
    // metodi
    @Bean
    public DatabaseUserDetailsService userDetailsService(){
        return new DatabaseUserDetailsService();
    }
}
