package com.experis.course.fotoalbum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "fotos")
public class Foto {
    // ATTRIBUTI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotBlank(message = "Il titolo non può essere un campo vuoto!")
    @Size(max = 255, message = "Il titolo deve essere > di 255 caratteri!")
    private String title;
    @Lob
    private String description;
    @Lob
    @Column(nullable = false)
    @URL(message = "Il link deve essere un URL valido!")
    private String imageUrl;
    @Column(nullable = false)
    private boolean visible;

    // GETTER E SETTER
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
