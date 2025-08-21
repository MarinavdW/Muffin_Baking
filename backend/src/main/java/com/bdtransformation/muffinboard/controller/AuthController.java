package com.bdtransformation.muffinboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    
    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> getAuthStatus(@AuthenticationPrincipal OAuth2User principal) {
        boolean isAuthenticated = principal != null;
        return ResponseEntity.ok(Map.of("authenticated", isAuthenticated));
    }
    
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }
        
        return ResponseEntity.ok(Map.of(
            "authenticated", true,
            "name", principal.getAttribute("name"),
            "email", principal.getAttribute("email")
        ));
    }
}