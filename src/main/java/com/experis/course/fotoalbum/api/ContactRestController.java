package com.experis.course.fotoalbum.api;

import com.experis.course.fotoalbum.model.Contact;
import com.experis.course.fotoalbum.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin
public class ContactRestController {
    // dependency Injection
    @Autowired
    ContactService contactService;

    @PostMapping
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return contactService.savedContact(contact);
    }
}
