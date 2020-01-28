package com.toptrumps.core;

import java.util.ArrayList;

public class Game {

    private ArrayList<AIPlayer> aiPlayers;

    public Game(){
        aiPlayers = new ArrayList<AIPlayer>();
    }

    public void newGame(int numOfPlayers){
        for (int i = 0; i < numOfPlayers ; i++){
            aiPlayers.add(new AIPlayer("Player" + (i+1)));
        }
    }


}
