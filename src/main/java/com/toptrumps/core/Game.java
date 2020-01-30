package com.toptrumps.core;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;

    public Game() {
        players = new ArrayList<Player>();
    }

    public void startNewGame(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = "Player" + (i + 1);
            players.add(new AIPlayer(playerName));
        }
        players.add(new Player());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}