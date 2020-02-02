package com.toptrumps.core;

public class Dealer {
    Deck deck;

    public Dealer(ArrayList<Player){
        DeckParser d = new DeckParser();
        deck = d.getCards();
    }
}
