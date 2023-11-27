package com.experis.course.fotoalbum.service;

import com.experis.course.fotoalbum.exceptions.FotoNotFoundException;
import com.experis.course.fotoalbum.model.Foto;
import com.experis.course.fotoalbum.model.User;
import com.experis.course.fotoalbum.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FotoService {
    // Repository
    @Autowired
    private FotoRepository fotoRepository;

    // Metodi
    // custom non standard
    // Metodo per ottenere le foto di un utente specifico
    public List<Foto> getFotosByUser(User user, Optional<String> search) {
        if (search.isPresent()){
            return fotoRepository.findByUserAndTitleContainingIgnoreCaseOrDescription(user,
                    search.get(), search.get());
        } else {
            return fotoRepository.findByUser(user);
        }
    }

    // metodo che restituisce la lista di foto con o senza filtri
    public List<Foto> getFotoList(Optional<String> search) {
        if (search.isPresent()) {
            // se il parametro search è presente chiamo il metodo custom
            return fotoRepository.findByTitleContainingIgnoreCaseOrDescriptionContaining(search.get(),
                    search.get());
        } else {
            // se il parametro search non è presente mostro tutte le foto
            return fotoRepository.findAll();
        }
    }

    public List<Foto> getFotoList() {
        return fotoRepository.findAll();
    }

    // metodo che restituisce una foto presa per id, altrimenti tira eccezione
    public Foto getFotoById(Integer id) throws FotoNotFoundException{
        // aggiungo un opzionale poichè potrei trovare la foto o no
        Optional<Foto> result = fotoRepository.findById(id);
        // verifico se il risultato è presente
        if (result.isPresent()){
            // gli passo l'id con model attribute
            return result.get();
        } else {
            throw new FotoNotFoundException("Foto con id " + id + " non trovata!");
        }
    }
    // Metodo per ottenere le foto di un utente specifico
    /*public List<Foto> getFotosByUser(User user, Optional<String> search) {
        if (search.isPresent()) {
            return fotoRepository.findByUserAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(user,
                    search.get(), search.get());
        } else {
            return fotoRepository.findByUser(user);
        }
    }*/
    public List<Foto> getAllFotos(Optional<String> search) {
        if (search.isPresent()) {
            return fotoRepository.findByTitleContainingIgnoreCaseOrDescriptionContaining(search.get(), search.get());
        } else {
            return fotoRepository.findAll();
        }
    }

    // metodo che salva la foto
    public Foto createFoto(Foto foto, User user) {
        if (user != null) {
            foto.setUser(user); // Associa l'utente alla foto, se l'utente è fornito
        }
        foto.setId(null); // Imposta l'ID della foto su null
        try{
            return fotoRepository.save(foto);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // metodo che salva il libro modificato preso dall'id
    public Foto editFoto(Foto foto) throws FotoNotFoundException {
        try {
            return fotoRepository.save(foto);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // metodo che elimina il libro passato tramite id
    public void deleteFoto(Integer id){
        fotoRepository.deleteById(id);
    }

    // metodo per vedere solo foto visibili
    public List<Foto> getVisibleFotoList(Optional<String> search) {
        if (search.isPresent()) {
            return fotoRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingAndVisible(search.get(), search.get(),
                    true);
        } else {
            return fotoRepository.findAllVisibleFotos();
        }
    }
}
