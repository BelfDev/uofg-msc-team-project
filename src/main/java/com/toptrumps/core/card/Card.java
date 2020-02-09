package com.toptrumps.core.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;

public class Card {

    private String name;
    private ArrayList<Attribute> attributes;
    private ArrayList<Attribute> highestAttributes;

    /**
     * Lazily filter the highest attribute and store it as a final member variable
     */

    public Card() {
        // Jackson deserialization
    }

    public Card(String name, ArrayList<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
        highestAttributes = new ArrayList<>();
        findHighestAttributes();
    }

    public String getName() {
        return name;
    }

    // TODO: Revisit this method to increase efficiency
    public Attribute getAttributeByName(String name) {
        return attributes.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    private void findHighestAttributes() {
        Supplier<Attribute> highestAttribute = () -> Collections.max(attributes);

        for (Attribute a : attributes){
            if (a.getValue() == highestAttribute.get().getValue()){
                highestAttributes.add(a);
            }
        }
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    @JsonIgnore
    public ArrayList<Attribute> getHighestAttributes() {
        return highestAttributes;
    }

    @Override
    public String toString() {
        return String.format("Card => name: %s\tattributes: %s\t", name, attributes);
    }
}
