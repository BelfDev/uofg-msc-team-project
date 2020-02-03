package com.toptrumps.core.card;

import com.toptrumps.core.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Dealer {

    private final static String DECK_RESOURCE = "/assets/StarCitizenDeck.txt";

    ArrayList<Card> deck;
    ArrayList<Card> communalPile;

    public Dealer() {
        this.deck = DeckParser.parseDeck(DECK_RESOURCE);
        this.communalPile = new ArrayList<>();
    }

    private void dealCards(ArrayList<Player> players) {
        int numberOfPlayers = players.size();
        shuffleCards();
        List<List<Card>> splitDecks = splitDeck(numberOfPlayers);

        // Distribute the split decks to the game players
        for (int i = 0; i < numberOfPlayers; i++) {
            ArrayList<Card> currentDeck = (ArrayList<Card>) splitDecks.get(0);
            players.get(0).setDeck(currentDeck);
        }
    }

    private void shuffleCards() {
        Collections.shuffle(deck, new Random());
    }

    private List<List<Card>> splitDeck(int chunkSize) {
        // Check if the provided chunk size is plausible
        if (chunkSize <= 0) throw new IllegalArgumentException("length = " + chunkSize);

        int size = this.deck.size();

        // Returns empty stream if no values were found
        if (size <= 0) return new ArrayList<>();

        int fullChunks = (size - 1) / chunkSize;

        // Splits the deck list into sub-listS of chunkSize length
        return IntStream
                .range(0, fullChunks + 1)
                .mapToObj(n -> this.deck.subList(n * chunkSize, n == fullChunks ? size : (n + 1) * chunkSize))
                .collect(toList());
    }

}
