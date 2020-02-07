package com.toptrumps.core.engine;

import com.toptrumps.core.player.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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

    public RoundOutcome(Result result) {
        this(result, null, null, null);
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

    public List<Integer> getRemovedPlayerIds() {
        return Optional.ofNullable(removedPlayers)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(Player::getId)
                .collect(toList());
    }

}
