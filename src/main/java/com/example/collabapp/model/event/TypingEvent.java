package com.example.collabapp.model.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypingEvent {
    private String notedId;
    private String userId;
    private String username;
    private boolean typing;
}
