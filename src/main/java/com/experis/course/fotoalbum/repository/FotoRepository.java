package com.experis.course.fotoalbum.repository;

import com.experis.course.fotoalbum.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Integer> {
    // query che filtra le foto per titolo
    List<Foto> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleKeyword, String authorsKeyword);
}
