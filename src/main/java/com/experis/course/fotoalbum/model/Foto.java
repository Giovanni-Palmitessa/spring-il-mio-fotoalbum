package com.experis.course.fotoalbum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fotos")
public class Foto {
    // ATTRIBUTI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private boolean visible;

}
