package com.bdtransformation.muffinboard.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostMapping("/zoho-connect")
    public ResponseEntity<Map<String, String>> handleZohoConnectWebhook(@RequestBody String payload) {
        try {
            logger.info("Received webhook payload: {}", payload);
            
            JsonNode webhookData = objectMapper.readTree(payload);
            
            String eventType = webhookData.has("eventType") ? webhookData.get("eventType").asText() : "unknown";
            
            if ("task.moved".equals(eventType) || "task.updated".equals(eventType)) {
                handleTaskMovement(webhookData);
            } else {
                logger.info("Received webhook event of type: {}", eventType);
            }
            
            return ResponseEntity.ok(Map.of("status", "received"));
            
        } catch (Exception e) {
            logger.error("Error processing webhook: ", e);
            return ResponseEntity.internalServerError().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    private void handleTaskMovement(JsonNode webhookData) {
        try {
            JsonNode taskNode = webhookData.get("task");
            JsonNode fromSectionNode = webhookData.get("fromSection");
            JsonNode toSectionNode = webhookData.get("toSection");
            
            if (taskNode != null && toSectionNode != null) {
                String muffinName = taskNode.has("title") ? taskNode.get("title").asText() : "Unknown Muffin";
                String toSectionName = toSectionNode.has("name") ? toSectionNode.get("name").asText() : "Unknown Section";
                String fromSectionName = fromSectionNode != null && fromSectionNode.has("name") 
                    ? fromSectionNode.get("name").asText() : "Unknown Section";
                
                if (fromSectionNode != null) {
                    logger.info("🧁 {} moved from '{}' to '{}'", muffinName, fromSectionName, toSectionName);
                } else {
                    logger.info("🧁 {} added to '{}'", muffinName, toSectionName);
                }
                
                if ("Already Baked".equalsIgnoreCase(toSectionName)) {
                    logger.info("🎉 Congratulations! {} has been successfully baked!", muffinName);
                }
            }
        } catch (Exception e) {
            logger.error("Error handling task movement: ", e);
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testWebhook() {
        logger.info("🧁 Test webhook called - Blueberry Muffin moved to 'Already Baked'");
        return ResponseEntity.ok(Map.of("status", "test completed", "message", "Check console for test log"));
    }
}