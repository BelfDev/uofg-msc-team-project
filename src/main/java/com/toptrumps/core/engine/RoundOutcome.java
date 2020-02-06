package com.toptrumps.core.engine;

import com.toptrumps.core.player.Player;

import java.util.ArrayList;

public class RoundOutcome {

    public enum Result {
        VICTORY, DRAW
    }

    private Result result;
    private Player winner;
    private ArrayList<Player> draws;
    private ArrayList<Player> removedPlayers;

    private RoundOutcome(Result result, Player winner, ArrayList<Player> draws, ArrayList<Player> removedPlayers) {
        this.result = result;
        this.winner = winner;
        this.draws = draws;
        this.removedPlayers = removedPlayers;
    }

    public RoundOutcome(Result result, Player winner, ArrayList<Player> removedPlayers) {
        this(result, winner, null, removedPlayers);
    }

    public RoundOutcome(Result result, ArrayList<Player> draws, ArrayList<Player> removedPlayers) {
        this(result, null, draws, removedPlayers);
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

    public ArrayList<Player> getRemovedPlayers(){
        return removedPlayers;
    }

}
