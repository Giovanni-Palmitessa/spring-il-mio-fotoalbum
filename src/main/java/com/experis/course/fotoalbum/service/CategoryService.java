package com.experis.course.fotoalbum.service;

import com.experis.course.fotoalbum.exceptions.CategoryNameUniqueExeption;
import com.experis.course.fotoalbum.model.Category;
import com.experis.course.fotoalbum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    // ATTRIBUTI
    // istanzio category repos.
    @Autowired
    CategoryRepository categoryRepository;

    // METODI
    // metodo che mi restituisce la lista di tutte le categorie
    public List<Category> getAllCategories() {
        return categoryRepository.findByOrderByName();
    }

    // metodo per salvare la nuova categoria
    public Category saveCategory(Category category) throws CategoryNameUniqueExeption{
        // controllo se il nome della categoria non esiste già
        if (categoryRepository.existsByName(category.getName())){
            throw new CategoryNameUniqueExeption(category.getName());
        }
        category.setName(category.getName());
        return categoryRepository.save(category);
    }

    // metodo per eliminare la categoria
    public void deleteCategory(Integer id){
        categoryRepository.deleteById(id);
    }
}
