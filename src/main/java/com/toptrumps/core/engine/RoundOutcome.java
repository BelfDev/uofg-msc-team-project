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

}
