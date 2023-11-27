package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.model.User;
import com.experis.course.fotoalbum.repository.UserRepository;
import com.experis.course.fotoalbum.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @GetMapping
    public String index(Authentication authentication, Model model) {
        DatabaseUserDetails principal = (DatabaseUserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findById(principal.getId()).get();
        model.addAttribute(loggedUser.getFirstName());
        model.addAttribute(loggedUser.getLastName());
        List<User> users = userRepository.findByRolesName("ADMIN");
        model.addAttribute("users", users);
        // recupero la lista di users e la passo al model
        return "users/index";
    }
}
