package com.toptrumps.core;

import java.util.ArrayList;

/**
 * Represents an AI player in the game.
 * There can be many AI players. AIPlayer
 * extends the super class Player.
 */

public class AIPlayer extends Player {

    /**
     * The name of the AIPlayer, set in the constructor.
     */
    private final String NAME;


    /**
     * Holds the AIPlayer's current cards.
     */
    private ArrayList<Card> hand;

    /**
     * Creates a new AIPlayer with the given name.
     */
    protected AIPlayer(String name) {
        hand = new ArrayList<>();
        NAME = name;

    }
}