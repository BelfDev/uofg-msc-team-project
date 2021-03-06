package com.toptrumps.core.card;

import com.toptrumps.core.utils.ResourceLoader;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class DealerTest {

    private Dealer dealer;

    @BeforeAll
    void oneTimeSetUp() {
        String fileName = ResourceLoader.getDeckFileName();
        this.dealer = new Dealer(fileName);
    }

    @Test
    @DisplayName("Dealer initialization populates deck")
    void newDealer_DeckResourcePath_ShouldPopulateCardList() {
        assertFalse(dealer.getDeck().isEmpty());
    }

    @Test
    @DisplayName("Dealing cards shuffles the deck")
    void dealCards_NumberOfPlayers_ShuffledDeck() {
        List<Card> originalDeck = new ArrayList<>(dealer.getDeck());
        dealer.dealCards(4);
        ArrayList<Card> shuffledDeck = dealer.getDeck();
        Assertions.assertNotEquals(originalDeck, shuffledDeck);
    }

    @Test
    @DisplayName("Dealing cards splits the deck between the number of players")
    void dealCards_NumberOfPlayers_SplitDecks() {
        int numberOfPlayers = 4;
        ArrayList<ArrayList<Card>> decks = dealer.dealCards(numberOfPlayers);
        assertEquals(numberOfPlayers, decks.size());
    }

}
