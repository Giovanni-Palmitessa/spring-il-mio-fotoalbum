package com.experis.course.fotoalbum.service;

import com.experis.course.fotoalbum.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoService {
    // Repository

    @Autowired
    private FotoRepository fotoRepository;
}
