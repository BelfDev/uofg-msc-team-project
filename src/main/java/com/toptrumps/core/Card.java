package com.toptrumps.core;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// TODO: Refactor this class
public class Card implements Comparable {
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

    protected String getDescription() {
        return description;
    }

    protected ArrayList<String> getBestCat() {
        return bestCat;
    }

    protected HashMap<String, Integer> getCardValuesByCat() {
        return cardValuesByCat;
    }

    protected int getCat(String cat) {
        return cardValuesByCat.get(cat);
    }

    public String toString() {
        String string = description;

        for (String key : cardValuesByCat.keySet()) {
            string += " " + key + " " + getCat(key);
        }
        return string;
    }


    protected void findBestCats() {
        int max = Collections.max(cardValuesByCat.values());

        ArrayList<String> keys = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cardValuesByCat.entrySet()) {
            if (entry.getValue() == max) {
                keys.add(entry.getKey());
            }
        }
        bestCat = keys;
    }

    //   TODO: Finish this method off.
    @Override
    public int compareTo(Object o) {
        if (o instanceof Card) {
            return +1;
        } else {
            return -1;
        }
    }
}
