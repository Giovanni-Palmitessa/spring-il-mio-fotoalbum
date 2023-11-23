package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.exceptions.FotoNotFoundException;
import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.service.CategoryService;
import com.experis.course.fotoalbum.service.FotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/fotos")
public class FotoController {
    // ATTRIBUTI
    @Autowired
    private FotoService fotoService;

    @Autowired
    private CategoryService categoryService;

    // Index mi mostra tutte le foto
    @GetMapping
    public String index(@RequestParam Optional<String> search, Model model) {
        // passo al template la lista di Foto
        model.addAttribute("fotoList", fotoService.getFotoList(search));
        // passo al template la stringa di ricerca per precaricare il valore dell'input
        model.addAttribute("searchKeyword", search.orElse(""));
        return "fotos/index";
    }

    // Show che mostra il libro preso per id
    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            // chiamo il service per avere la foto tramite id
            Foto foto = fotoService.getFotoById(id);
            model.addAttribute("foto", foto);
            // ritorno la vista di dettaglio della foto
            return "fotos/show";
        } catch (FotoNotFoundException e) {
            // se non trovo la foto sollevo eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }

    // metodo che mostra il template per creare una nuova foto
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("foto", new Foto());
        model.addAttribute("categoryList", categoryService.getAllCategories());
        return "fotos/form";
    }

    // metodo che salva le foto inserite nel DB
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("foto") Foto formFoto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // verifico che i dati sono corretti prima di salvare
        if (bindingResult.hasErrors()){
            // ci sono errori quindi devo ricaricare il form
            return "fotos/form";
        }
        // prima di salvare il libro gli setto visibile di default
        formFoto.setVisible(true);
        // Salvo la foto
        Foto savedFoto = fotoService.createFoto(formFoto);
        // reindirizzo allo show del libro appena creato
        // aggiungo attributo per mostrare messaggio di conferma modifica
        redirectAttributes.addFlashAttribute("message",
                "La foto " + savedFoto.getTitle() +" è stata creata con successo!");
        return "redirect:/fotos/show/" + savedFoto.getId();
    }

    // metodo che mi permette di editare una foto esisitente tramite id
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        try {
            // gli passo l'id con model attribute
            model.addAttribute("foto", fotoService.getFotoById(id));
            model.addAttribute("categoryList", categoryService.getAllCategories());
            return "fotos/form";
        } catch (FotoNotFoundException e) {
            // se non trovo la foto sollevo eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!!");
        }
    }

    // metodo che salva la foto modificata
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,@Valid @ModelAttribute Foto formFoto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // valido il form delle foto
        if (bindingResult.hasErrors()){
            // ci sono errori quindi ricarico il form
            return "fotos/form";
        }
        try {
            // prima di salvare il libro gli setto visibile di default
            formFoto.setVisible(true);
            // non ci sono errori quindi salvo la mia foto modificata
            Foto savedFoto = fotoService.editFoto(formFoto);
            // aggiungo attributo per mostrare messaggio di conferma modifica
            redirectAttributes.addFlashAttribute("message",
                    "La foto " + savedFoto.getTitle() +" è stata modificata con successo!");
            // faccio il redirect alla pagina di dettagli della foto modificata
            return "redirect:/fotos/show/" + savedFoto.getId();
        } catch (FotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }

    // metodo che elimina la foto
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            // cerco la foto tramite id e se non la trovo lancio eccezione
            Foto fotoToDelete = fotoService.getFotoById(id);
            // se esiste la foto la elimino
            fotoService.deleteFoto(id);
            // aggiungo attributo per mostrare messaggio di conferma eliminazione
            redirectAttributes.addFlashAttribute("message",
                    "La foto " + fotoToDelete.getTitle() +" è stata eliminata con successo!");
            return "redirect:/fotos";
        } catch (FotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }
}
