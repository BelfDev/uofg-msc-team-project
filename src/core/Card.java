package core;

import java.util.HashMap;

public class Card {
    // instance variables
    private String description;
    private HashMap<String, Integer> cardValuesByCat;


    public Card(String description) {

        this.description = description;
        cardValuesByCat = new HashMap<>(); // creates new hashmap;

    }

    protected void addValueToCat(String cat, int value) { // adds new item to the HashMap
        cardValuesByCat.put(cat, value);
    }

    public String getDescription() {
        return description;
    }

    protected int getCat(String cat) {
        return cardValuesByCat.get(cat);
    }

    public String toString() {
        String string = description;

        for (String key : cardValuesByCat.keySet()){
            string += " " + key + " " + getCat(key);
        }
        return string;
    }

    public HashMap<String, Integer> getCardValuesByCat() {
        return cardValuesByCat;
    }


}
