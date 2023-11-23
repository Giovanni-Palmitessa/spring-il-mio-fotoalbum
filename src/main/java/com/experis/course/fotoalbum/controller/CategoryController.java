package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "categories/index";
    }

}
