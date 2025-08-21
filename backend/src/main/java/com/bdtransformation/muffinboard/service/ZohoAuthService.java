package com.bdtransformation.muffinboard.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZohoAuthService {
    
    @Value("${spring.security.oauth2.client.registration.zoho.client-id}")
    private String clientId;
    
    @Value("${spring.security.oauth2.client.registration.zoho.client-secret}")
    private String clientSecret;
    
    @Value("${spring.security.oauth2.client.registration.zoho.redirect-uri}")
    private String redirectUri;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private String accessToken;
    private String refreshToken;
    
    public ZohoAuthService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    public String getAuthorizationUrl() {
        String scope = "zohopulse.feedList.CREATE,zohopulse.feedList.READ,zohopulse.feedList.UPDATE,zohopulse.feedList.DELETE";
        return String.format(
            "https://accounts.zoho.com/oauth/v2/auth?scope=%s&client_id=%s&response_type=code&redirect_uri=%s&access_type=offline",
            scope, clientId, redirectUri
        );
    }
    
    public boolean exchangeCodeForToken(String code) {
        try {
            String tokenUrl = "https://accounts.zoho.com/oauth/v2/token";
            
            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "authorization_code");
            params.put("client_id", clientId);
            params.put("client_secret", clientSecret);
            params.put("redirect_uri", redirectUri);
            params.put("code", code);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            
            StringBuilder formData = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (formData.length() > 0) formData.append("&");
                formData.append(entry.getKey()).append("=").append(entry.getValue());
            }
            
            HttpEntity<String> request = new HttpEntity<>(formData.toString(), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode tokenResponse = objectMapper.readTree(response.getBody());
                accessToken = tokenResponse.get("access_token").asText();
                refreshToken = tokenResponse.get("refresh_token").asText();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public boolean isAuthenticated() {
        return accessToken != null && !accessToken.isEmpty();
    }
    
    public HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Zoho-oauthtoken " + accessToken);
        headers.add("Content-Type", "application/json");
        return headers;
    }
}