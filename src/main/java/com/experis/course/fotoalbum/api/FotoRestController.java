package com.experis.course.fotoalbum.api;

import com.experis.course.fotoalbum.exceptions.FotoNotFoundException;
import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    // endpoint per i dettagli del libro preso per id
    @GetMapping("/{id}")
    public Foto details(@PathVariable Integer id) {
        try {
            // se trovo la foto con id mostro i dettagli
            return fotoService.getFotoById(id);
        } catch (FotoNotFoundException e) {
            // tiro un eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }

}
