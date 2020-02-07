package com.toptrumps.core.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

public class Dealer {

    private final ArrayList<Card> deck;

    public Dealer(String deckFile) {
        this.deck = DeckParser.parseDeck(deckFile);
    }

    public ArrayList<ArrayList<Card>> dealCards(int numberOfPlayers) {
        shuffleCards();
        ArrayList<ArrayList<Card>> splitDecks = splitDeck(numberOfPlayers);
        return splitDecks;
    }


    private void shuffleCards() {
        Collections.shuffle(deck, new Random());
    }

    private ArrayList<ArrayList<Card>> splitDeck(int numberOfSplits) {
        int size = this.deck.size();

        // Check if the provided numberOfSplits is plausible
        if (numberOfSplits <= 0) throw new IllegalArgumentException("numberOfsSplits = " + numberOfSplits);

        int chunkSize = size / numberOfSplits;

        // Splits the deck list into sub-listS of chunkSize length
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
