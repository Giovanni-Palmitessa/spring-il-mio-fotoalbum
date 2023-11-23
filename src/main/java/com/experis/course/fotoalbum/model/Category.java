package com.experis.course.fotoalbum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {
    // Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String name;

    @Lob
    private String description;
}
