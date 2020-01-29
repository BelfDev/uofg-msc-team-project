package com.toptrumps.core;

import java.util.ArrayList;

public class AIPlayer extends Player {

    private ArrayList<Card> hand;
    private String name;

    public AIPlayer(String name) {
        hand = new ArrayList<Card>();
        this.name = name;
    }

    @Override
    public void dealCard(Card c) {
        hand.add(c);
    }

    @Override
    public void printHand() {
        for (Card c : hand) System.out.println(name + " " + c.toString());
    }

    public String playBestMove() {
        ArrayList<String> bestMoves = hand.get(0).getBestCat();
        if (bestMoves.size() == 1) {
            String best = bestMoves.get(0);
            return best;
        } else {
            int index = (int) (Math.random() * bestMoves.size());
            String best = bestMoves.get(index);
            return best;
        }
    }
}

