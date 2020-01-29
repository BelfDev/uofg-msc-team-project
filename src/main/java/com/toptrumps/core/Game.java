package com.toptrumps.core;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;

    public Game() {
        players = new ArrayList<Player>();
    }

    public void newGame(int numOfPlayers) {
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new AIPlayer("Player" + (i + 1)));
        }
        players.add(new Player());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
