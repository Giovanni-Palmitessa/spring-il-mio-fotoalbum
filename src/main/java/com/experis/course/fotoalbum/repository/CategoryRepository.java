package com.experis.course.fotoalbum.repository;

import com.experis.course.fotoalbum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // metodo per cercare le categorie e ordinarle
    List<Category> findByOrderByName();

    // metodo per cercare le categorie per nome
    Optional<Category> findByName(String name);
}
