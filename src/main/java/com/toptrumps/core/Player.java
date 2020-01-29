package com.toptrumps.core;

import java.util.ArrayList;

public class Player {

    private boolean currentPlayer;
    private ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }


    public void dealCard(Card c) {
        hand.add(c);
    }


    public void printHand() {
        for (Card c : hand) {
            System.out.println(c.toString());
        }
    }

    public void isCurrentPlayer(){

    }

    public Card getTopCard(){
        return hand.get(hand.size()-1);
    }
}
