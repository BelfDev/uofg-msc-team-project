package com.toptrumps.core;

public class Attribute {
    private String attName;
    private int attValue;

    public Attribute(String attName, int attValue){
        this.attName = attName;
        this.attValue = attValue;
    }

    public String getAttName(){
        return attName;
    }

    public String getAttValue(){
        return attValue;
    }
}
