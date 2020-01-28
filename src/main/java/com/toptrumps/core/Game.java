package com.toptrumps.core;

import java.util.ArrayList;

public class Game {

    private ArrayList<Dealable> players;

    public Game() {
        players = new ArrayList<Dealable>();
    }

    public void newGame(int numOfPlayers) {
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new AIPlayer("Player" + (i + 1)));
        }
        players.add(new Player());
    }

    public ArrayList<Dealable> getPlayers() {
        return players;
    }
}
