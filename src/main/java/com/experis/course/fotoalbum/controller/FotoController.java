package com.experis.course.fotoalbum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fotos")
public class FotoController {
    // Index
    @GetMapping
    public String index() {
        return "fotos/index";
    }
}
