package com.bdtransformation.muffinboard.controller;

import com.bdtransformation.muffinboard.model.MuffinCard;
import com.bdtransformation.muffinboard.model.MuffinList;
import com.bdtransformation.muffinboard.service.ZohoConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@CrossOrigin(origins = "http://localhost:3000")
public class MuffinBoardController {
    
    @Autowired
    private ZohoConnectService zohoConnectService;
    
    @GetMapping("/lists")
    public ResponseEntity<List<MuffinList>> getBoardLists(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        List<MuffinList> lists = zohoConnectService.getBoardLists();
        return ResponseEntity.ok(lists);
    }
    
    @PostMapping("/cards")
    public ResponseEntity<MuffinCard> createMuffinCard(@RequestBody Map<String, String> request, @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        String muffinName = request.get("name");
        String listId = request.get("listId");
        
        if (listId == null) {
            listId = zohoConnectService.getToBakeListId();
        }
        
        if (muffinName == null || muffinName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        
        MuffinCard newCard = zohoConnectService.createMuffinCard(listId, muffinName);
        if (newCard != null) {
            return ResponseEntity.ok(newCard);
        } else {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}