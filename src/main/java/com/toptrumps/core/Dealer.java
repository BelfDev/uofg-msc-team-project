package com.toptrumps.core;

import com.sun.xml.internal.bind.v2.TODO;

public class Dealer {

    private DeckParser deckParser;
    private Game game;

    public Dealer(DeckParser deckParser, Game game) {
        this.deckParser = deckParser;
        this.game = game;
    }

    public void dealCards() {
        while (!deckParser.getCards().isEmpty()) {
            for (Player d : game.getPlayers()) {
                d.dealCard(deckParser.getCards().get(deckParser.getCards().size() - 1));
                deckParser.getCards().remove(deckParser.getCards().size() - 1);
            }
        }

        for (Player d : game.getPlayers()) {
            d.printHand();
            System.out.println();
        }
    }

//    TODO: Requires a shuffle method.


}


