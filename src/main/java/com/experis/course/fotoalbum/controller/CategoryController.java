package com.experis.course.fotoalbum.controller;

import com.experis.course.fotoalbum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    // Service
    @Autowired
    CategoryService categoryService;

    // Metodi
    public String index() {
        return "categories/index";
    }

}
