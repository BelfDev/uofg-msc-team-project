package com.toptrumps.core;

/**
 * Represents the player (user) of the game.
 * There should only ever be one player.
 */

import java.util.ArrayList;

public class Player{

    protected String name;
    protected ArrayList<Card> hand;

    /**
     * Creates a new Player and creates a new ArrayList to hold the cards.
     * The ArrayList is referenced by the instance variable hands.
     */
    public Player(String name){
        this.name = name;
        this.hand = new ArrayList<Card>();
    }

    /**
     * Method to return the name of the player
     */
    public String getName(){
        return name;
    }

}