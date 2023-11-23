package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.repository.FotoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/fotos")
public class FotoController {
    // ATTRIBUTI
    @Autowired
    private FotoRepository fotoRepository;

    // Index
    @GetMapping
    public String index(@RequestParam Optional<String> search, Model model) {
        // inizializzo lista di foto
        List<Foto> fotoList;
        if (search.isPresent()) {
            // se il parametro search è presente chiamo il metodo custom
           fotoList = fotoRepository.findByTitleContainingIgnoreCaseOrDescriptionContaining(search.get(), search.get());
        } else {
            // se il parametro search non è presente mostro tutte le foto
            fotoList = fotoRepository.findAll();
        }
        // passo al template la lista di Foto
        model.addAttribute("fotoList", fotoList);
        // passo al template la stringa di ricerca per precaricare il valore dell'input
        model.addAttribute("searchKeyword", search.orElse(""));
        return "fotos/index";
    }

    // Show che mostra il libro preso per id
    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        // aggiungo un opzionale poichè potrei trovare la foto o no
        Optional<Foto> result = fotoRepository.findById(id);
        // verifico se il risultato è presente
        if (result.isPresent()){
            // gli passo l'id con model attribute
            model.addAttribute("foto", result.get());
            return "fotos/show";
        } else {
            // se non trovo la foto sollevo eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }

    // metodo che mostra il template per creare una nuova foto
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("foto", new Foto());
        return "fotos/form";
    }

    // metodo che salva le foto inserite nel DB
    @PostMapping("/create")
    public String store(@Valid Foto formFoto) {
        // prima di salvare il libro gli setto visibile di default
        formFoto.setVisible(true);
        // Salvo la foto
        Foto savedFoto = fotoRepository.save(formFoto);
        // reindirizzo allo show del libro appena creato
        return "redirect:/fotos/show/" + savedFoto.getId();
    }
}
