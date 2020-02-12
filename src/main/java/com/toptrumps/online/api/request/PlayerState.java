package com.toptrumps.online.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerState {

    private int id;
    private boolean isAIPlayer;
    private String name;
    private int deckCount;
    private Card topCard;
    private int numberOfRoundWins;

    public PlayerState() {
        // Jackson deserialization
    }

    public PlayerState(Player player) {
        this.id = player.getId();
        this.name = player.getName();
    }

    public PlayerState(int id, boolean isAIPlayer, String name, int deckCount, Card topCard, int numberOfRoundWins) {
        this.id = id;
        this.isAIPlayer = isAIPlayer;
        this.name = name;
        this.deckCount = deckCount;
        this.topCard = topCard;
        this.numberOfRoundWins = numberOfRoundWins;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public boolean getIsAIPlayer() {
        return isAIPlayer;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public int getDeckCount() {
        return deckCount;
    }

    @JsonProperty
    public Card getTopCard() {
        return topCard;
    }

    @JsonProperty("numberOfWins")
    public int getNumberOfRoundWins() {
        return numberOfRoundWins;
    }

    public Player toPlayer() {
        return isAIPlayer ? new AIPlayer(this) : new Player(this);
    }

}
