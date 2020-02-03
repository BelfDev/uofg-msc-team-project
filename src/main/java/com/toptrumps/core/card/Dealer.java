package com.toptrumps.core.card;

import com.toptrumps.core.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Dealer {

    private final static String DECK_RESOURCE = "assets/StarCitizenDeck.txt";

    private final ArrayList<Card> deck;
    private ArrayList<Card> communalPile;

    public Dealer() {
        this.deck = DeckParser.parseDeck(DECK_RESOURCE);
        this.communalPile = new ArrayList<>();

        System.out.println(deck);
    }

    public void dealCards(ArrayList<Player> players) {
        int numberOfPlayers = players.size();
        shuffleCards();
        List<List<Card>> splitDecks = splitDeck(numberOfPlayers);

        // Distribute the split decks to the game players
        for (int i = 0; i < numberOfPlayers; i++) {
            ArrayList<Card> currentDeck = new ArrayList<>(splitDecks.get(i));
            players.get(i).setDeck(currentDeck);
        }
    }

    private void shuffleCards() {
        Collections.shuffle(deck, new Random());
    }

    private List<List<Card>> splitDeck(int numberOfSplits) {
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
                    return this.deck.subList(fromIndex, toIndex);
                })
                .collect(toList());
    }

}
