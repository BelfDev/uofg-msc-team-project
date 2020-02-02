package com.toptrumps.core;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private ArrayList<Player> players;
    private int numberOfPlayers;
    private int activePlayerIndex;
    private Attribute selectedAttribute;
    private Player user;

    /**
     * Constructor to initialise the number of players and the ArrayList of players
     * Starts the game
     */
    public Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<Player>();
        startNewGame();
        chooseStartingPlayer();
    }

    /**
     * Method to add the correct number of players to the ArrayList
     */
    private void startNewGame() {
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = "Player" + (i + 1); //automatically generates AI names
            players.add(new AIPlayer(playerName));
        }
        user = new Player("Human");
        players.add(user); 
        Dealer d = new Dealer(players); //deal out the cards
    }

    /**
     * Method to randomly select the starting player
     */
    private void chooseStartingPlayer(){
        Random rand = new Random();
        activePlayerIndex = rand.nextInt(numberOfPlayers+1);
    }

    /**
     * Method to move onto the next player
     */
    public void nextPlayer(){
        if(activePlayerIndex == numberOfPlayers){
            activePlayerIndex = 0; //if player is last in the list, reset to the start
        }else{
            activePlayerIndex++; 
        }
    }

    /**
     * Method to get the ArrayList of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Method to return the name of the current active player
     */
    public String getActivePlayer(){
        return players.get(activePlayerIndex).getName();
    }

    /**
     * Method to set the selected attribute for the round
     */
    public void setSelectedAttribute(Attribute selectedAttribute){
        this.selectedAttribute = selectedAttribute;
    }

    /**
     * Method to retrieve the user player object
     */
    public Player getUser(){
        return user;
    }
}