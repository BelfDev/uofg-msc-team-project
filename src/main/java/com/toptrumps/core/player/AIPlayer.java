package com.toptrumps.core.player;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;

/**
 * Represents an AI player in the game.
 * There can be up to 4 AI players.
 */

public class AIPlayer extends Player {

    private static final String DEFAULT_AI_NAME = "AI_Player";

    public AIPlayer(int id) {
        super(id, String.format("%s_%d", DEFAULT_AI_NAME, id));
    }

    public Attribute selectAttribute() {
        Card topCard = getTopCard();
        Attribute highestAttribute = topCard.getHighestAttribute();
        this.setSelectedAttribute(highestAttribute);
        return highestAttribute;
    }

}