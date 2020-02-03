package com.toptrumps.core.card;

public class Attribute implements Comparable<Attribute> {

    private final String name;
    private final int value;

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
        return String.format("Attribute => name: %s\tvalue: %d", name, value);
    }

}