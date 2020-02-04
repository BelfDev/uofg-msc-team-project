package com.toptrumps.core.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;

public class Card {

    final private String name;
    private ArrayList<Attribute> attributes;

    /**
     * Lazily filter the highest attribute and store it as a final member variable
     */
    final private Supplier<Attribute> highestAttribute = () -> Collections.max(attributes);

    public Card(String name, ArrayList<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
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

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public Attribute getHighestAttribute() {
        return this.highestAttribute.get();
    }

    @Override
    public String toString() {
        return String.format("Card => name: %s\tattributes: %s\t", name, attributes);
    }
}
