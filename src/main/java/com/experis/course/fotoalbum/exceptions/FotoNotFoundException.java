package com.experis.course.fotoalbum.exceptions;

public class FotoNotFoundException extends RuntimeException{
    public FotoNotFoundException(String message) {
        super(message);
    }
}
