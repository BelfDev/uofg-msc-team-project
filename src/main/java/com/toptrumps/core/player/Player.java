package com.toptrumps.core.player;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player of the game.
 * There should only ever be one Human player.
 */
public class Player implements Comparable<Player> {

    final protected int id;
    final protected String name;
    private boolean isActive;
    protected List<Card> deck;

    private Attribute selectedAttribute;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.deck = new ArrayList<>();
        this.isActive = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public Card getTopCard() {
        if (deck.isEmpty()) throw new NoCardException();
        return deck.get(0);
    }

    public void collectCards(List<Card> cards) {
        deck.addAll(cards);
    }

    public void removeTopCard() {
        if (!deck.isEmpty()) {
            deck.remove(0);
        }
    }

    public Attribute getSelectedAttribute() {
        return selectedAttribute;
    }

    public void setSelectedAttribute(Attribute attribute) {
        this.selectedAttribute = attribute;
    }

//    public int compareCardTo(Card opponentCard) {
//        if (selectedAttribute == null) throw new NoSelectionException();
//        Attribute opponentAttribute = opponentCard.getAttributeByName(selectedAttribute.getName());
//        if (opponentAttribute == null) throw new IncompatibleComparisonException();
//        return selectedAttribute.compareTo(opponentAttribute);
//    }

    public boolean isAIPlayer() {
        return this instanceof AIPlayer;
    }

    @Override
    public int compareTo(Player opponent) {
        Attribute opponentAttribute = opponent.getSelectedAttribute();
        if (opponentAttribute == null || selectedAttribute == null) throw new NoSelectionException();
        return selectedAttribute.compareTo(opponentAttribute);
    }

    // TODO: Come up with a better format
    @Override
    public String toString() {
        return String.format("Player => id: %d\tname: %s\tdeck: %s", id, name, deck.toString());
    }

}