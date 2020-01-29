package com.toptrumps.core;

public class Card {
    // instance variables
    private String description;
    private Attribute[] attributes;

    public Card(String description, Attribute[] attributes) {
        this.description = description;
        this.attributes = attributes;
    }

    protected String getDescription() {
        return description;
    }
}