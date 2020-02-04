package com.toptrumps.core.engine;

import com.toptrumps.core.player.Player;

import java.util.ArrayList;

public class RoundOutcome {

    enum Result {
        VICTORY, DRAW
    }

    private Result result;
    private Player winner;
    private ArrayList<Player> draws;

    private RoundOutcome(Result result, Player winner, ArrayList<Player> draws) {
        this.result = result;
        this.winner = winner;
        this.draws = draws;
    }

    public RoundOutcome(Result result, Player winner) {
        this(result, winner, null);
    }

    public RoundOutcome(Result result, ArrayList<Player> draws) {
        this(result, null, draws);
    }

    public Result getResult() {
        return result;
    }

    public Player getWinner() {
        return winner;
    }

    public ArrayList<Player> getDraws() {
        return draws;
    }

    public String toString(){
        String outcome = "The round resulted in a: "
        switch(result){
            case VICTORY:
                outcome += "Victory!\nThe winner is: " + winner.getName();
                break;
            case DRAW:
                outcome += "Draw\nThe score was tied between: ";
                for(Player player: draws){
                    if(player == draws.get(-1)){
                        outcome += " and " + player.getName();
                    }else if(player == draws.get(-2)){
                        outcome += player.getName();
                    }else{
                        outcome += player.getName() + ", ";
                    }
                }
                break;
        }
        return outcome;
    }

}
