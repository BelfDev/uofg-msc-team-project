package com.toptrumps.core;

import java.util.ArrayList;

public class AIPlayer {

    private ArrayList<Card> hand;
    private String name;

    public AIPlayer(String name){
        hand = new ArrayList<Card>();
        this.name = name;
    }

//    public void playBestMove(){
//        ArrayList bestMoves =
//            if (bestCat.size() == 1) {
//                String best = bestCat.get(0);
//                return best;
//            } else {
//                int index = (int) (Math.random() * bestCat.size());
//                String best = bestCat.get(index);
//                return best;
//            }
//        }
//    }

}
