package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.model.Category;
import com.experis.course.fotoalbum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    // Service
    @Autowired
    CategoryService categoryService;

    // Metodi
    @GetMapping
    public String index(Model model) {
        // passo al model il categoryList con la lista delle categorie
        model.addAttribute("categoryList", categoryService.getAllCategories());
        // passo al model una categoria vuota come attributo
        model.addAttribute("categoryObj", new Category());
        return "categories/index";
    }

    @PostMapping("/save")
    public String save(Category formCategory) {
        // salvo la nuova categoria
        return "redirect:/categories";
    }
}
