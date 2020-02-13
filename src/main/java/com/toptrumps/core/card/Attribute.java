package com.toptrumps.core.card;

public class Attribute implements Comparable<Attribute> {

    private String name;
    private int value;

    public Attribute() {
        // Jackson deserialization
    }

    public Attribute(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Attribute attribute) {
        return Integer.compare(this.value, attribute.getValue());
    }

    @Override
    public String toString() {
        return String.format("Attribute => name: %1$-2s value: %2$2s", name, value);
    }


}