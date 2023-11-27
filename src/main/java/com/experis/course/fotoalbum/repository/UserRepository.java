package com.experis.course.fotoalbum.repository;

import com.experis.course.fotoalbum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // metodo che cerca le email
    Optional<User> findByEmail(String email);

    // metodo che trova i ruoli
    List<User> findByRolesName(String name);
}
