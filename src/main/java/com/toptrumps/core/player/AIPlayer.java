package com.toptrumps.core.player;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.online.api.request.PlayerState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents an AI player in the game.
 * There can be up to 4 AI players.
 */

public class AIPlayer extends Player {

    private static final String DEFAULT_AI_NAME = "AI_Player";

    public AIPlayer(int id) {
        super(id, String.format("%s_%d", DEFAULT_AI_NAME, id));
    }

    public AIPlayer(PlayerState playerState) {
        super(playerState);
    }

    public Attribute selectAttribute() {
        Card topCard = getTopCard();
        ArrayList<Attribute> highestAttributes = topCard.getHighestAttributes();
        Random rand = new Random();
        Attribute selectedHighestAttribute = highestAttributes.get(rand.nextInt((highestAttributes.size())));
        this.setSelectedAttribute(selectedHighestAttribute);
        return  selectedHighestAttribute;
    }

}