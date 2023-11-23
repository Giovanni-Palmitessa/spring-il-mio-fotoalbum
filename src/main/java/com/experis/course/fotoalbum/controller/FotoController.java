package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/fotos")
public class FotoController {
    // ATTRIBUTI
    @Autowired
    private FotoRepository fotoRepository;
    // Index
    @GetMapping
    public String index() {
        List<Foto> fotoList = fotoRepository.findAll();
        return "fotos/index";
    }
}
