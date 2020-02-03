package com.toptrumps.core.player;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;

import java.util.ArrayList;

/**
 * Represents a player of the game.
 * There should only ever be one Human player.
 */
public class Player {

    final protected String name;
    protected ArrayList<Card> deck;

    private Attribute selectedAttribute;

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public Card getTopCard() {
        if (deck.isEmpty()) throw new NoCardException();
        return deck.get(0);
    }

    public void setSelectedAttribute(Attribute attribute) {
        this.selectedAttribute = attribute;
    }

    public int compareSelectedAttributeTo(Attribute remoteAttribute) {
        if (selectedAttribute == null) throw new NoSelectionException();
        if (!selectedAttribute.getName().equals(remoteAttribute.getName())) throw new IncompatibleComparisonException();
        return selectedAttribute.compareTo(remoteAttribute);
    }

}