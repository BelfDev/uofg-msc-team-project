package com.toptrumps.online.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.player.Player;

public class Winner {

    private int id;
    private String name;
    private boolean isAIPlayer;

    public Winner() {
        // Jackson deserialization
    }

    public Winner(int id, String name, boolean isAIPlayer) {
        this.id = id;
        this.name = name;
        this.isAIPlayer = isAIPlayer;
    }

    public Winner(Player player) {
        this(player.getId(), player.getName(), player.isAIPlayer());
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty("isAIPlayer")
    public boolean isAIPlayer() {
        return isAIPlayer;
    }

}
