package com.example.collabapp.exception;

public class StaleNoteException extends RuntimeException {
    public StaleNoteException() {
        super("Note has been modified by someone else. Please reload and try again.");
    }
}