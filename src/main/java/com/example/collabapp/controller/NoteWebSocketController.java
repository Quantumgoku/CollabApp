package com.example.collabapp.controller;

import com.example.collabapp.model.event.NoteEditEvent;
import com.example.collabapp.model.event.PresenceEvent;
import com.example.collabapp.model.event.TypingEvent;
import com.example.collabapp.services.NoteWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NoteWebSocketController {

    private final NoteWebSocketService noteWebSocketService;

    //call when user edits a note
    @MessageMapping("/note.edit")
    public void handleNoteEdit(@Payload NoteEditEvent event,
                               SimpMessageHeaderAccessor headerAccessor){
        log.info("WS message received: noteId={}", event.getNoteId());
        noteWebSocketService.handleNoteEdit(event,headerAccessor);
    }

    @MessageMapping("/note.typing")
    public void handleNoteTyping(@Payload TypingEvent event,SimpMessageHeaderAccessor headerAccessor){
        noteWebSocketService.handleTyping(event,headerAccessor);
    }

    @MessageMapping("/note.presence")
    public void handleNotePresence(@Payload PresenceEvent event,SimpMessageHeaderAccessor headerAccessor){
        noteWebSocketService.handlePresence(event,headerAccessor);
    }

    @MessageMapping("/test")
    public void test(String msg){
        noteWebSocketService.test(msg);
    }

}
