package com.example.collabapp.services.impl;

import com.example.collabapp.model.event.NoteEditEvent;
import com.example.collabapp.model.event.PresenceEvent;
import com.example.collabapp.model.event.TypingEvent;
import com.example.collabapp.repository.UserRepository;
import com.example.collabapp.services.NoteWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class NoteWebSocketServiceIml implements NoteWebSocketService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void handleNoteEdit(NoteEditEvent event, SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        if(userId == null) return;

        userRepository.findById(userId).ifPresent(user -> {
            event.setEditedBy(userId);
            event.setEditedByName(user.getUsername());
            event.setEditedAt(LocalDateTime.now());

            log.info("Note edit event for noteId: {} by: {}",event.getNoteId(),userId);

            messagingTemplate.convertAndSend(
                    "/topic/note."+event.getNoteId(),
                    event
            );
        });
    }

    @Override
    public void handleTyping(TypingEvent event, SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        if(userId==null) return;

        userRepository.findById(userId).ifPresent(user -> {
            event.setUserId(userId);
            event.setUsername(user.getUsername());

            messagingTemplate.convertAndSend(
                    "/topic/note."+event.getNoteId()+".typing",
                    event
            );
        });

    }

    @Override
    public void handlePresence(PresenceEvent event, SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        if(userId == null) return;

        userRepository.findById(userId).ifPresent(user -> {
            event.setUserId(userId);
            event.setUsername(user.getUsername());

            log.info("Presence event: {} is {} on note {}",user.getUsername(),event.getStatus(),event.getNoteId());

            messagingTemplate.convertAndSend(
                    "/topic/note."+event.getNoteId()+".presence",
                    event
            );
        });
    }

    @Override
    public void test(String msg) {
        log.info("Received {}",msg);

        messagingTemplate.convertAndSend(
                "/topic/test",
                msg
        );
    }


}
