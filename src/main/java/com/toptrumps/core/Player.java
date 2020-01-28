package com.toptrumps.core;

import java.util.ArrayList;

public class Player implements Dealable {

    private ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }

    @Override
    public void dealCard(Card c) {
        hand.add(c);
    }

    @Override
    public void printHand() {
        for (Card c : hand) {
            System.out.println(c.toString());
        }
    }
}
