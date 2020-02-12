package com.toptrumps.core.card;

import com.toptrumps.cli.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

/**
 * This class is responsible for handling fundamental card collection operations.
 * It parses a text file into a list of cards (deck), and provides methods for
 * shuffling and splitting the deck into smaller chunks.
 */
public class Dealer {

    private final ArrayList<Card> deck;

    /**
     * Constructs a Dealer object that encapsulates parsing, shuffling,
     * and deck-splitting logic.
     *
     * @param deckFile relative path to the deck text file
     *                 which will compose the game play
     */
    public Dealer(String deckFile) {
        this.deck = DeckParser.parseDeck(deckFile);
        // Logs the parsed deck
        Logger.getInstance().logToFileIfEnabled("Deck before shuffling: \n" + printDeck());
    }

    /**
     * Returns shuffled split decks bases on the number of players present in the game.
     *
     * @param numberOfPlayers the number of players present in the game
     * @return a list of shuffled smaller decks (list of cards)
     */
    public ArrayList<ArrayList<Card>> dealCards(int numberOfPlayers) {
        shuffleCards();
        return splitDeck(numberOfPlayers);
    }

    /**
     * Prints the cards within the deck
     *
     * @return String representation of the deck cards
     */
    public String printDeck() {
        StringBuilder deckString = new StringBuilder();
        for (Card c : deck) {
            deckString.append(c.toString());
        }
        return deckString.toString();
    }

    // === CONVENIENCE METHODS ===

    private void shuffleCards() {
        // Randomly reorder the list of cards
        Collections.shuffle(deck, new Random());
        // Logs the shuffled deck if enabled
        Logger.getInstance().logToFileIfEnabled("Shuffled cards: \n" + printDeck());
    }

    private ArrayList<ArrayList<Card>> splitDeck(int numberOfSplits) {
        int size = this.deck.size();
        // Checks if the provided numberOfSplits is plausible
        if (numberOfSplits <= 0) throw new IllegalArgumentException("numberOfsSplits = " + numberOfSplits);
        // Calculates the target chunk size
        int chunkSize = size / numberOfSplits;
        // Splits the deck list into sub-lists of chunkSize length
        return IntStream
                .range(0, numberOfSplits)
                .mapToObj(n ->
                {
                    int fromIndex = n * chunkSize;
                    int toIndex = n == (numberOfSplits - 1) ? size : (n + 1) * chunkSize;
                    return new ArrayList<Card>(this.deck.subList(fromIndex, toIndex));
                })
                .collect(toCollection(ArrayList::new));
    }

}
