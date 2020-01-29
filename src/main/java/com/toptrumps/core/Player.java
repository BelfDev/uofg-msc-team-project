package com.toptrumps.core;


import java.util.ArrayList;

public class Player{

    private ArrayList<Card> hand;
    private final String name = "Human player"; // Obviously can be changed later

    public Player(){
        hand = new ArrayList<Card>();
    }

}