package com.bdtransformation.muffinboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MuffinList {
    private String id;
    private String name;
    private List<MuffinCard> cards = new ArrayList<>();
    
    public MuffinList() {}
    
    public MuffinList(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<MuffinCard> getCards() { return cards; }
    public void setCards(List<MuffinCard> cards) { this.cards = cards; }
}