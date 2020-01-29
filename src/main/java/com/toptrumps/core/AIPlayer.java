package com.toptrumps.core;


import java.util.ArrayList;

public class AIPlayer extends Player{

    private String name;
    private ArrayList<Card> hand;

    public AIPlayer(String name){
        this.name = name;
        hand = new ArrayList<Card>();
    }

}