package com.experis.course.fotoalbum.api;

import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fotos")
@CrossOrigin
public class FotoRestController {
    // dependency Injection
    @Autowired
    FotoService fotoService;

    // Metodi

    // endopint per lista di tutte le foto
    @GetMapping
    public List<Foto> index(@RequestParam Optional<String> search) {
        return fotoService.getFotoList(search);
    }

}
