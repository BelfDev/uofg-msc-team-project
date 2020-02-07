package com.toptrumps.online.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.engine.RoundOutcome.Result;
import com.toptrumps.core.player.Player;

import java.util.List;

import static java.util.stream.Collectors.toList;

@JsonInclude(Include.NON_NULL)
public class Outcome {

    private Result result;
    private Winner winner;
    private List<Integer> defeatedPlayerIds;
    private Attribute selectedAttribute;

    public Outcome() {
        // Jackson deserialization
    }

    public Outcome(Result result, Winner winner, List<Integer> defeatedPlayerIds, Attribute selectedAttribute) {
        this.result = result;
        this.winner = winner;
        this.defeatedPlayerIds = defeatedPlayerIds;
        this.selectedAttribute = selectedAttribute;
    }

    public Outcome(RoundOutcome roundOutcome, Attribute selectedAttribute) {
        List<Integer> defeatedPlayerIds = roundOutcome.getRemovedPlayers().stream().map(Player::getId).collect(toList());
        Winner winner = new Winner(roundOutcome.getWinner());
        this.result = roundOutcome.getResult();
        this.winner = winner;
        this.defeatedPlayerIds = defeatedPlayerIds;
        this.selectedAttribute = selectedAttribute;
    }

    public Outcome(RoundOutcome roundOutcome) {
        this(roundOutcome, null);
    }

    @JsonProperty
    public Result getResult() {
        return result;
    }

    @JsonProperty
    public Winner getWinner() {
        return winner;
    }

    @JsonProperty
    public List<Integer> getDefeatedPlayerIds() {
        return defeatedPlayerIds;
    }

    @JsonProperty
    public Attribute getSelectedAttribute() {
        return selectedAttribute;
    }

}
