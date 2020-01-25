package core;

import java.util.*;

public class Card {
    // instance variables
    private String description;
    private HashMap<String, Integer> cardValuesByCat;
    private ArrayList<String> bestCat;


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

    public String getBestCat() {
        if(bestCat.size() == 1){
               String best =  bestCat.get(0);
               return best;
        }
        else{
            int index = (int)(Math.random()*bestCat.size());
            String best = bestCat.get(index);
            return best;
        }
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

    protected void findBestCats() {
        int max = Collections.max(cardValuesByCat.values());

        ArrayList<String> keys = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cardValuesByCat.entrySet()) {
            if (entry.getValue()==max) {
                keys.add(entry.getKey());
            }
        }
        bestCat = keys;
    }


}
