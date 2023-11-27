package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.exceptions.FotoNotFoundException;
import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.model.User;
import com.experis.course.fotoalbum.service.CategoryService;
import com.experis.course.fotoalbum.service.FotoService;
import com.experis.course.fotoalbum.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/fotos")
public class FotoController {
    // ATTRIBUTI
    @Autowired
    private FotoService fotoService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    // Index mi mostra tutte le foto
    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Optional<String> search,
                        Model model, User user) {
        // cerco lo user se esiste con username
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        //istanzio lista di foto vuota
        List<Foto> fotoList;
        // istanzio lista di User vuota
        List<User> userList = userService.getAllUsers();
        if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("SUPER_ADMIN"))) {
            // se lo user è un SuperAdmin passo tutte le foto
            fotoList = fotoService.getFotoList(currentUser, search);
        } else {
            fotoList = fotoService.getFotosByUser(currentUser, search);
        }
        // passo al template la lista di Foto
        model.addAttribute("fotoList", fotoList);
        // passo all'utente i dettagli dello user
        model.addAttribute("users", userList);
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
    /*private User getCurrentUser(Authentication authentication) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.getUserByUsername(userDetails.getUsername());
        }
        return null;
    }*/

    // metodo che salva le foto inserite nel DB
    @PostMapping("/create")
    public String store(@AuthenticationPrincipal UserDetails userDetails,
                        @Valid @ModelAttribute("foto") Foto formFoto, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
        // verifico che i dati sono corretti prima di salvare
        if (bindingResult.hasErrors()){
            // ci sono errori quindi devo ricaricare il form
            model.addAttribute("categoryList", categoryService.getAllCategories());
            return "fotos/form";
        }
        try {
            // Trova l'utente autenticato e collega la foto a quell'utente
            User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
            // prima di salvare il libro gli setto visibile di default
            formFoto.setVisible(true);
            // salvo la foto
            Foto savedFoto = fotoService.createFoto(formFoto, user);
            // aggiungo attributo per mostrare messaggio di conferma modifica
            redirectAttributes.addFlashAttribute("message",
                    "La foto " + savedFoto.getTitle() +" è stata creata con successo!");
            return "redirect:/fotos/show/" + savedFoto.getId();
        } catch (RuntimeException e) {
            bindingResult.addError(new FieldError("foto", "title", e.getMessage(), false, null, null, "Il nome deve" +
                    " essere unico!"));
            return "fotos/form";
        }
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
    public String update(@PathVariable Integer id,@Valid @ModelAttribute Foto formFoto, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, Model model) {
        // valido il form delle foto
        if (bindingResult.hasErrors()){
            // ci sono errori quindi ricarico il form
            model.addAttribute("categoryList", categoryService.getAllCategories());
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

    // visibilità
    @PostMapping("/toggleVisibility/{id}")
    public String toggleVisibility(@PathVariable Integer id, Model model) {
        try {
            Foto foto = fotoService.getFotoById(id);
            foto.setVisible(!foto.isVisible());
            fotoService.editFoto(foto);

            return "redirect:/fotos";
        } catch (FotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }
}
