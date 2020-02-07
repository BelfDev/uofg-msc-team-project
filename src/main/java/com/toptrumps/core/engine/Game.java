package com.toptrumps.core.engine;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.card.Dealer;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.toptrumps.core.engine.RoundOutcome.Result.DRAW;
import static com.toptrumps.core.engine.RoundOutcome.Result.VICTORY;
import static java.util.stream.Collectors.toList;

public class Game {

    private static final String DEFAULT_USER_NAME = "Human_Player";

    private final Dealer dealer;

    /**
     * Constructor to initialise the number of players and the ArrayList of players
     * Starts the game
     */
    public Game(String deckFile) {
        this.dealer = new Dealer(deckFile);
    }

    private List<Player> createPlayers(int numberOfOpponents) {
        List<AIPlayer> aiPlayers = createAIPlayers(numberOfOpponents);
        return new ArrayList<Player>() {{
            add(new Player(0, DEFAULT_USER_NAME));
            addAll(aiPlayers);
        }};
    }

    private List<AIPlayer> createAIPlayers(int numberOfOpponents) {
        List<AIPlayer> aiPlayers = new ArrayList<>();
        for (int i = 1; i <= numberOfOpponents; i++) {
            AIPlayer aiPlayer = new AIPlayer(i);
            aiPlayers.add(aiPlayer);
        }
        return aiPlayers;
    }

    public void assignDecks(List<Player> players) {
        final int numberOfPlayers = players.size();
        final List<List<Card>> decks = dealer.dealCards(numberOfPlayers);

        // Distribute the split decks to the game players
        for (int i = 0; i < numberOfPlayers; i++) {
            players.get(i).setDeck(decks.get(i));
        }
    }

    public Player assignActivePlayer(List<Player> players) {
        final int numberOfPlayers = players.size();
        int randomIndex = getRandomInteger(0, numberOfPlayers - 1);
        Player activePlayer = players.get(randomIndex);
        activePlayer.setActive(true);
        return activePlayer;
    }

    public List<Player> startUp(int numberOfOpponents) {
        List<Player> players = createPlayers(numberOfOpponents);
        assignDecks(players);
        return players;
    }

    public List<Player> getWinners(Attribute selectedAttribute, List<Player> players) {
        // Attribute Comparison
        // TODO: Optimize this process -> try to loop only once and retrieve the player with max selected attribute value
        String attributeName = selectedAttribute.getName();
        // Find maxValue
        int maxValue = players.stream()
                .map(p -> p.getTopCard().getAttributeByName(attributeName))
                .max(Attribute::compareTo)
                .get().getValue();

        // Find playersWithMaxValue
        ArrayList<Player> winners = (ArrayList<Player>) players.stream()
                .filter(p -> p.getTopCard().getAttributeByName(attributeName).getValue() == maxValue)
                .collect(toList());

        return winners;
    }

    public RoundOutcome processRoundOutcome(List<Player> winners, List<Player> players) {
        // Round outcome
        RoundOutcome outcome;

        //collect any defeated players and remove them from the game
        List<Player> removedPlayers = players
                .stream()
                .filter(p -> p.getDeck().isEmpty())
                .collect(toList());

        if (winners.size() == 1) {
            Player winner = winners.get(0);
            winner.setActive(true);
            outcome = new RoundOutcome(VICTORY, winner, removedPlayers);
        } else {
            outcome = new RoundOutcome(DRAW, winners, removedPlayers);
        }

        return outcome;
    }

    private int getRandomInteger(int min, int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt((max - min) + 1) + min;
    }

}