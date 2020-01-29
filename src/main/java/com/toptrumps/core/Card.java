package com.toptrumps.core;

public class Card {
    // instance variables
    private String description;
    private Attribute[] attributes;

    /**
     * Constructor to initiate the description and attributes array
     * @param String description
     * @param Attribute[] attributes
     */
    public Card(String description, Attribute[] attributes) {
        this.description = description;
        this.attributes = attributes;
    }

    /**
     * Method to retrieve the card description
     * @return String description
     */
    public String getDescription() {
        return description;
    }
}
