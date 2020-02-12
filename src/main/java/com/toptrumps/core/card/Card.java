package com.toptrumps.core.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toptrumps.core.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class Card {

    private String name;
    private ArrayList<Attribute> attributes;

    /**
     * Lazily filter the highest attribute and store it as a final member variable
     */
    final private Supplier<Attribute> highestAttribute = () -> Collections.max(attributes);

    public Card() {
        // Jackson deserialization
    }

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

    @JsonIgnore
    public Attribute getHighestAttribute() {
        int maxAttributeValue = highestAttribute.get().getValue();
        List<Attribute> maxAttributes = attributes.stream().filter(a -> a.getValue() == maxAttributeValue).collect(toList());
        return maxAttributes.get(RandomGenerator.getInteger(0, maxAttributes.size() - 1));
    }

    @Override
    public String toString() {
        return String.format("Card => name: %1$-20s attributes: %2$20s\n", name, attributes);
    }
}
