package com.example.collabapp.services;

import com.example.collabapp.model.event.NoteEditEvent;
import com.example.collabapp.model.event.PresenceEvent;
import com.example.collabapp.model.event.TypingEvent;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public interface NoteWebSocketService {
    void handleNoteEdit(NoteEditEvent event, SimpMessageHeaderAccessor headerAccessor);
    void handleTyping(TypingEvent event,SimpMessageHeaderAccessor headerAccessor);
    void handlePresence(PresenceEvent event,SimpMessageHeaderAccessor headerAccessor);
}
