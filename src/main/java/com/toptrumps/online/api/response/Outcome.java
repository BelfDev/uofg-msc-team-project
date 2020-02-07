package com.toptrumps.online.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.engine.RoundOutcome.Result;

import java.util.List;

@JsonInclude(Include.NON_NULL)
public class Outcome {

    private Result result;
    private Winner winner;
    private List<Integer> removedPlayerIds;
    private Attribute selectedAttribute;

    public Outcome() {
        // Jackson deserialization
    }

    public Outcome(Result result, Winner winner, List<Integer> removedPlayerIds, Attribute selectedAttribute) {
        this.result = result;
        this.winner = winner;
        this.removedPlayerIds = removedPlayerIds;
        this.selectedAttribute = selectedAttribute;
    }

    public Outcome(RoundOutcome roundOutcome, Attribute selectedAttribute) {
        Winner winner = null;
        if (roundOutcome.getWinner() != null) {
            winner = new Winner(roundOutcome.getWinner());
        }
        List<Integer> defeatedPlayerIds = roundOutcome.getRemovedPlayerIds();

        this.result = roundOutcome.getResult();
        this.winner = winner;
        this.removedPlayerIds = defeatedPlayerIds;
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
    public List<Integer> getRemovedPlayerIds() {
        return removedPlayerIds;
    }

    @JsonProperty
    public Attribute getSelectedAttribute() {
        return selectedAttribute;
    }

}
