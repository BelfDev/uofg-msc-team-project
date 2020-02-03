package com.toptrumps.core.player;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;

/**
 * Represents an AI player in the game.
 * There can be up to 4 AI players.
 */

public class AIPlayer extends Player {

    private final static String DEFAULT_AI_NAME = "AI_Player";
    private int id;

    public AIPlayer(int id) {
        super(String.format("%s_%d", DEFAULT_AI_NAME, id));
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void selectAttribute() {
        Card topCard = getTopCard();
        Attribute highestAttribute = topCard.getHighestAttribute();
        this.setSelectedAttribute(highestAttribute);
    }

}