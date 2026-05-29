package com.example.collabapp.exception;

public class NoteNotFoundException extends RuntimeException{
    public NoteNotFoundException(String noteId){
        super("Note not found with id: "+noteId);
    }
}
