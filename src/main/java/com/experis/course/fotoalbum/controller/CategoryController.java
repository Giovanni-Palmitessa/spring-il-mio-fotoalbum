package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.model.Category;
import com.experis.course.fotoalbum.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("categoryObj")Category formCategory, BindingResult bindingResult,
                       Model model) {
        // se la categoria ha degli errori
        if (bindingResult.hasErrors()){
            // ci sono errori ti ricarico la pagina
            model.addAttribute("categoryList", categoryService.getAllCategories());
            return "categories/index";
        }
        // salvo la nuova categoria
        categoryService.saveCategory(formCategory);
        return "redirect:/categories";
    }
}
