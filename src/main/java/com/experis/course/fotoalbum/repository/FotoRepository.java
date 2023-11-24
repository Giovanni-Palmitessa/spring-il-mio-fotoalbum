package com.experis.course.fotoalbum.repository;

import com.experis.course.fotoalbum.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Integer> {
    // query che filtra le foto per titolo
    List<Foto> findByTitleContainingIgnoreCaseOrDescriptionContaining(String titleKeyword,
                                                                                String descriptionKeyword);

    @Query("SELECT p FROM Foto p WHERE (p.title LIKE %?1% OR p.description LIKE %?2%) AND p.visible = true")
    List<Foto> findByTitleContainingIgnoreCaseOrDescriptionContainingAndVisible(String titleKeyword,
                                                                                 String descriptionKeyword, boolean visible);

    List<Foto> findByVisible(boolean visible);
    @Query("SELECT p FROM Foto p WHERE p.visible = true")
    List<Foto> findAllVisibleFotos();
}
