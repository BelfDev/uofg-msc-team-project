package core;

import java.util.HashMap;
import java.util.Set;

public class Card {

    private String description;

    private HashMap<String, Integer> cardValuesByCat;


    public Card(String description) {

        this.description = description;


        cardValuesByCat = new HashMap<>();

    }

    void addValueToCat(String cat, int value) {
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
