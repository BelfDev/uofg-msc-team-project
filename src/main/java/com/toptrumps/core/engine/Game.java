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
    private static final int DEFAULT_NUMBER_OF_HUMAN_PLAYERS = 1;

    private final ArrayList<Player> players;
    private int numberOfPlayers;
    private final Dealer dealer;
    private final GameEventListener listener;

    private Player activePlayer;
    private int rounderNumber;
    private RoundOutcome lastRoundOutcome;

    /**
     * Constructor to initialise the number of players and the ArrayList of players
     * Starts the game
     */
    public Game(int numberOfPlayers, GameEventListener listener) {
        this.numberOfPlayers = numberOfPlayers;
        this.listener = listener;
        this.rounderNumber = 0;
        this.dealer = new Dealer();
        this.lastRoundOutcome = null;

        this.players = new ArrayList<Player>() {{
            add(new Player(0, DEFAULT_USER_NAME));
        }};

        createAIPlayers(numberOfPlayers - DEFAULT_NUMBER_OF_HUMAN_PLAYERS);

//        chooseStartingPlayer();
    }

    private void createAIPlayers(int numberOfAIPlayers) {
        for (int i = 1; i <= numberOfAIPlayers; i++) {
            AIPlayer aiPlayer = new AIPlayer(i);
            players.add(aiPlayer);
        }
    }

    public void start() {

        // Start Up

        dealer.dealCards(players);
        assignRandomActivePlayer();

        while (numberOfPlayers > 1) {
            rounderNumber++;

            Card humanPlayerCard = getHumanPlayer().getTopCard();
            listener.onRoundStart(activePlayer, humanPlayerCard, rounderNumber);

            // Attribute Selection
            Attribute selectedAttribute;
            if (activePlayer.isAIPlayer()) {
                selectedAttribute = ((AIPlayer) activePlayer).selectAttribute();
            } else {
                selectedAttribute = listener.onRequestSelection(activePlayer.getTopCard());
                activePlayer.setSelectedAttribute(selectedAttribute);
            }

            listener.onAttributeSelected(activePlayer);
            // TODO: Revisit the attribute comparison to increase efficiency
            // Attribute Comparison
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

            // Round outcome
            RoundOutcome outcome;
            // TODO: Revisit round cards to avoid new stream
            List<Card> roundCards = players.stream().map(Player::getTopCard).collect(toList());
            // TODO: Revisit this to avoid another loop
            players.forEach(Player::removeTopCard);

            //collect any defeated players and remove them from the game
            ArrayList<Player> removedPlayers = (ArrayList<Player>) players.stream().filter(p -> p.getDeck().isEmpty()).collect(toList());
            players.removeAll(removedPlayers);
            numberOfPlayers = players.size();

            if (winners.size() == 1) {
                // There's a winner
                activePlayer.setActive(false);
                Player winner = winners.get(0);
                winner.setActive(true);
                outcome = new RoundOutcome(VICTORY, winner, removedPlayers);
                // Collect all player's cards
                List<Card> communalPile = dealer.dealCommunalPile();
                roundCards.addAll(communalPile);
                winner.collectCards(roundCards);
                activePlayer = winner;
            } else {
                // Draw
                outcome = new RoundOutcome(DRAW, winners, removedPlayers);
                dealer.putCardsOnCommunalPile(roundCards);
            }

            listener.onRoundEnd(outcome);
        }

        listener.onGameOver(activePlayer);
    }

    private void assignRandomActivePlayer() {
        int randomIndex = getRandomInteger(0, numberOfPlayers - 1);
        Player activePlayer = players.get(randomIndex);
        activePlayer.setActive(true);
        this.activePlayer = activePlayer;
    }

    private int getRandomInteger(int min, int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt((max - min) + 1) + min;
    }

    public ArrayList<Card> getRoundCards() {
        return (ArrayList<Card>) players.stream().map(Player::getTopCard).collect(toList());
    }

    private Player getHumanPlayer() {
        return players.get(0);
    }

}