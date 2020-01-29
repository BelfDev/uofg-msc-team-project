package com.toptrumps.core;

public class Attribute {
    private String name;
    private int value;

    public Attribute(String name, int value){
        this.name = name;
        this.value = value;
    }

    public String getAttName(){
        return name;
    }

    public int getAttValue(){
        return value;
    }
}