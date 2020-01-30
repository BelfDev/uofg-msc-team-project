package com.toptrumps.core;

/**
 * Represents the player (user) of the game.
 * There should only ever be one player.
 */

import java.util.ArrayList;

public class Player{

    /**
     * The name of the player, set by default.
     */
    private final String NAME = "Human player"; // Obviously can be changed later

    /**
     * Holds the player's current cards.
     */
    private ArrayList<Card> hand;

    /**
     * Creates a new Player and creates a new ArrayList to hold the cards.
     * The ArrayList is referenced by the instance variable hands.
     */

    protected Player(){
        hand = new ArrayList<>();
    }


}