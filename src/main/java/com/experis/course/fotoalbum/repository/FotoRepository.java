package com.experis.course.fotoalbum.repository;

import com.experis.course.fotoalbum.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Integer> {
}
