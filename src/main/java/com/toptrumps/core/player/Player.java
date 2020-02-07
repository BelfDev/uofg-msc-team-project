package com.toptrumps.core.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.online.api.request.PlayerState;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player of the game.
 * There should only ever be one Human player.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player implements Comparable<Player> {

    final protected int id;
    final protected String name;
    private boolean isActive;
    private int deckCount;
    protected ArrayList<Card> deck;

    private Attribute selectedAttribute;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.deck = new ArrayList<>();
        this.isActive = false;
        this.deckCount = 0;
    }

    public Player(PlayerState playerState) {
        this(playerState.getId(), playerState.getName());
        this.deck = new ArrayList<Card>() {{
            add(playerState.getTopCard());
        }};
        this.deckCount = playerState.getDeckCount();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("isAIPlayer")
    public boolean isAIPlayer() {
        return this instanceof AIPlayer;
    }

    public List<Card> getDeck() {
        return deck;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
        this.deckCount = deck.size();
    }

    @JsonIgnore
    public int getDeckCount() {
        return deckCount;
    }

    @JsonIgnore
    public Card getTopCard() {
        if (deck.isEmpty()) throw new NoCardException();
        return deck.get(0);
    }

    public void collectCards(List<Card> cards) {
        deck.addAll(cards);
        deckCount = deck.size();
    }

    public void removeTopCard() {
        if (!deck.isEmpty()) {
            deck.remove(0);
            deckCount--;
        }
    }

    @JsonIgnore
    public Attribute getSelectedAttribute() {
        return selectedAttribute;
    }

    public void setSelectedAttribute(Attribute attribute) {
        this.selectedAttribute = attribute;
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