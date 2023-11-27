package com.experis.course.fotoalbum.repository;

import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.model.User;
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

    List<Foto> findByUser(User user);

    @Query("SELECT p FROM Foto p WHERE p.user = :user AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :titleKeyword, '%')" +
            ") OR (p.description) LIKE LOWER(CONCAT('%', :descriptionKeyword, '%')))")
    List<Foto> findByUserAndTitleContainingIgnoreCaseOrDescription(User user, String titleKeyword,
                                                                                     String descriptionKeyword);
}
