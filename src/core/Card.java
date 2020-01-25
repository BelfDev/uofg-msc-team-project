package core;

import java.util.HashMap;

public class Card {

    private String description;

    private HashMap<String, Integer> cardValuesByCat;



    public Card(String description) {

        this.description = description;


         cardValuesByCat = new HashMap<>();

    }

    public void addValueToCat(String cat, int value){
        cardValuesByCat.put(cat, value);
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, Integer> getCardValuesByCat() {
        return cardValuesByCat;
    }



}
