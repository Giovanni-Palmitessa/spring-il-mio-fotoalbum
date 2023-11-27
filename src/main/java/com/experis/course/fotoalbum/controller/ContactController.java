package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contacts")
public class ContactController {
    // dep injection
    @Autowired
    ContactRepository contactRepository;
}
