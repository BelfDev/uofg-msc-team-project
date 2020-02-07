package com.toptrumps.online.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;

public class PlayerState {

    private int id;
    private boolean isAIPlayer;
    private String name;
    private int deckCount;
    private Card topCard;

    public PlayerState() {
        // Jackson deserialization
    }

    public PlayerState(int id, boolean isAIPlayer, String name, int deckCount, Card topCard) {
        this.id = id;
        this.isAIPlayer = isAIPlayer;
        this.name = name;
        this.deckCount = deckCount;
        this.topCard = topCard;
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

    public Player toPlayer() {
        return isAIPlayer ? new AIPlayer(id) : new Player(id, name);
    }

}
