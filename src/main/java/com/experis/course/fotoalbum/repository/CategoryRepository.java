package com.experis.course.fotoalbum.repository;

import com.experis.course.fotoalbum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
