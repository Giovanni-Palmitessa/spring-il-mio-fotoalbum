package com.experis.course.fotoalbum.repository;

import com.experis.course.fotoalbum.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository <Contact, Integer> {
}
