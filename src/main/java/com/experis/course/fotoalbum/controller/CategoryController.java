package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.exceptions.CategoryNameUniqueExeption;
import com.experis.course.fotoalbum.exceptions.CategoryNotFoundException;
import com.experis.course.fotoalbum.model.Category;
import com.experis.course.fotoalbum.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    // Service
    @Autowired
    CategoryService categoryService;

    // Metodi
    // mostrare il form della categoria e lista delle categorie
    @GetMapping
    public String index(Model model) {
        // passo al model il categoryList con la lista delle categorie
        model.addAttribute("categoryList", categoryService.getAllCategories());
        // passo al model una categoria vuota come attributo
        model.addAttribute("categoryObj", new Category());
        return "categories/index";
    }

    // salvare la categoria
    @PostMapping
    public String save(@Valid @ModelAttribute("categoryObj")Category formCategory, BindingResult bindingResult,
                       Model model, RedirectAttributes redirectAttributes) {
        // se la categoria ha degli errori
        if (bindingResult.hasErrors()){
            // ci sono errori ti ricarico la pagina
            model.addAttribute("categoryList", categoryService.getAllCategories());
            return "categories/index";
        }
        try {
            // salvo la nuova categoria
            categoryService.saveCategory(formCategory);
            // messaggio creazione categoria
            redirectAttributes.addFlashAttribute("messageSuccess",
                    "La categoria " + formCategory.getName() +" è stata creata con successo!");
            return "redirect:/categories";
        } catch (CategoryNameUniqueExeption e) {
            // do un feedback all'utente che mi dice che la categoria esiste già
            bindingResult.addError(new FieldError("category", "name", e.getMessage(), false, null, null,
                    "La categoria esiste gia!"));
            // ricarico le categorie
            model.addAttribute("categoryList", categoryService.getAllCategories());
            return "categories/index";
        }
    }

    // Metodo che gestisce la richiesta di eliminazione
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        try {
            categoryService.deleteCategory(id);
            return "redirect:/categories";
        } catch (CategoryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
