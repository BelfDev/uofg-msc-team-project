package com.toptrumps.core.engine;

import com.toptrumps.core.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RoundOutcome {

    public enum Result {
        VICTORY, DRAW, GAME_OVER
    }

    private Result result;
    private Player winner;
    private List<Player> draws;
    private List<Player> removedPlayers;

    private RoundOutcome(Result result, Player winner, List<Player> draws, List<Player> removedPlayers) {
        this.result = result;
        this.winner = winner;
        this.draws = draws;
        this.removedPlayers = removedPlayers;
    }

    public RoundOutcome(Result result, Player winner, List<Player> removedPlayers) {
        this(result, winner, null, removedPlayers);
    }

    public RoundOutcome(Result result, List<Player> draws, List<Player> removedPlayers) {
        this(result, null, draws, removedPlayers);
    }

    public Result getResult() {
        return result;
    }

    public Player getWinner() {
        return winner;
    }

    public List<Player> getDraws() {
        return draws;
    }

    public List<Player> getRemovedPlayers() {
        return removedPlayers;
    }

}
