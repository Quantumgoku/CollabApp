package com.example.collabapp.controller;

import com.example.collabapp.services.RefreshTokenCleanupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
@Profile("dev")
@RequiredArgsConstructor
public class DevController {
    private final RefreshTokenCleanupService cleanupService;

    @DeleteMapping("/cleanup-tokens")
    public ResponseEntity<String> cleanUp(){
        cleanupService.cleanupExpiredTokens();
        return ResponseEntity.ok("Cleanup done");
    }
}
