package com.toptrumps.core.statistics;

import com.toptrumps.core.player.Player;

import java.util.Map;

public final class GameStateCollector {

    private final int numberOfDraws;
    private final Player finalWinner;
    private final int numberOfRounds;
    private final Map<Player, Integer> roundWinsMap;

    public int getNumberOfDraws() {
        return numberOfDraws;
    }

    public Player getFinalWinner() {
        return finalWinner;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public Map<Player, Integer> getRoundWinsMap() {
        return roundWinsMap;
    }

    public GameStateCollector(Builder builder) {
        this.numberOfDraws = builder.numberOfDraws;
        this.finalWinner = builder.finalWinner;
        this.numberOfRounds = builder.numberOfRounds;
        this.roundWinsMap = builder.roundWinsMap;
    }

    public static class Builder {

        private int numberOfDraws;
        private Player finalWinner;
        private int numberOfRounds;
        private Map<Player, Integer> roundWinsMap;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder setNumberOfDraws(int numberOfDraws) {
            this.numberOfDraws = numberOfDraws;
            return this;
        }

        public Builder setFinalWinner(Player finalWinner) {
            this.finalWinner = finalWinner;
            return this;
        }

        public Builder setNumberOfRounds(int numberOfRounds) {
            this.numberOfRounds = numberOfRounds;
            return this;
        }

        public Builder setRoundWinsMap(Map<Player, Integer> roundWinsMap) {
            this.roundWinsMap = roundWinsMap;
            return this;
        }

        public GameStateCollector build() {
            return new GameStateCollector(this);
        }

    }
}
