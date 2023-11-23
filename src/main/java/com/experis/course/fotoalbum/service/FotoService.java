package com.experis.course.fotoalbum.service;

import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotoService {
    // Repository

    @Autowired
    private FotoRepository fotoRepository;

    // Metodi

    // metodo che restituisce la lista di foto con o senza filtri
    public List<Foto> getFotoList(Optional<String> search) {
        if (search.isPresent()) {
            // se il parametro search è presente chiamo il metodo custom
            return fotoRepository.findByTitleContainingIgnoreCaseOrDescriptionContaining(search.get(), search.get());
        } else {
            // se il parametro search non è presente mostro tutte le foto
            return fotoRepository.findAll();
        }
    }

    // metodo che restituisce una foto presa per id, altrimenti tira eccezione
    public Foto getFotoById(Integer id){

    }
}
