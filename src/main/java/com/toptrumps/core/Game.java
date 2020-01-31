package com.toptrumps.core;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private int numberOfPlayers;

    public Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        startNewGame();
        players = new ArrayList<Player>();
    }

    private void startNewGame() {
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = "Player" + (i + 1);
            players.add(new AIPlayer(playerName));
        }
        players.add(new Player("Human"));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}