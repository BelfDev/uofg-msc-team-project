package com.toptrumps.core;

import java.util.ArrayList;

public class Card {

    private String description;
    private ArrayList<Attribute> attributes;

    /**
     * Constructor to initiate the description and attributes array
     * @param String description
     * @param ArrayList<Attribute> attributes
     */
    public Card(String description, ArrayList<Attribute> attributes) {
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

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    // Commented out as may be required later
    
    // public String stringAttributes(){
    //     String attributesString = "";
    //     for(Attribute x : attributes){
    //         attributesString += "   >" + x.getName() + ": " + x.getValue() +"\n";
    //     }
    //     return attributesString;
    // }

    public String stringAttributes(){
        String attributesString = "";
        for(int i=0; i< attributes.size(); i++){
            Attribute a = attributes.get(i);
            attributesString += String.format("  %d:    %-12s%d\n", i+1, a.getName(), a.getValue());
        }
        return attributesString;
    }

}

