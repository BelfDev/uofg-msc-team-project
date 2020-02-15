package com.toptrumps.online.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.player.Player;
import com.toptrumps.db.FullStats;
import com.toptrumps.db.Statistics;

import java.util.List;
import java.util.Map;

public class StatisticsContent {

    private int numberOfGames;
    private int numberOfAIWins;
    private int numberOfHumanWins;
    private int numberOfAverageDraws;
    private int numberOfMaxRounds;
    private List<FullStats> performanceHistory;

    public StatisticsContent(Statistics statistics) {
        this.numberOfGames = statistics.getGamesPlayed();
        this.numberOfAIWins = statistics.getAiWins();
        this.numberOfHumanWins = statistics.getHumanWins();
        this.numberOfAverageDraws = statistics.getAverageDraws();
        this.numberOfMaxRounds = statistics.getMaxRounds();
        this.performanceHistory = statistics.getFullStatsList();
    }

    @JsonProperty
    public int getNumberOfGames() {
        return numberOfGames;
    }

    @JsonProperty
    public int getNumberOfAIWins() {
        return numberOfAIWins;
    }

    @JsonProperty
    public int getNumberOfHumanWins() {
        return numberOfHumanWins;
    }

    @JsonProperty
    public int getNumberOfAverageDraws() {
        return numberOfAverageDraws;
    }

    @JsonProperty
    public int getNumberOfMaxRounds() {
        return numberOfMaxRounds;
    }

    @JsonProperty
    public List<FullStats> getPerformanceHistory() {
        return performanceHistory;
    }
}
