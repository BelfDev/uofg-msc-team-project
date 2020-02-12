package com.toptrumps.core.engine;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.card.Dealer;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.core.utils.RandomGenerator;
import com.toptrumps.db.Statistics;

import java.util.ArrayList;
import java.util.List;

import static com.toptrumps.core.engine.RoundOutcome.Result.*;
import static java.util.stream.Collectors.toCollection;

/**
 * This class represents the core game engine. It provides a number
 * of public methods that execute fundamental game computations important
 * to both the Command Line and the Online applications.
 */
public class GameEngine {

    private static final String DEFAULT_USER_NAME = "Human_Player";

    private final Dealer dealer;

    /**
     * Constructs the GameEngine model that exposes core game
     * logic operations.
     *
     * @param deckFile relative path to the deck text file
     *                 which will compose the game play
     */
    public GameEngine(String deckFile) {
        // Object responsible for shuffling and splitting the deck
        this.dealer = new Dealer(deckFile);
    }

    /**
     * Populates the given players with shuffled decks.
     *
     * @param players player list to whom the card decks must be assigned
     */
    public void assignDecks(ArrayList<Player> players) {
        final int numberOfPlayers = players.size();
        // Gets the shuffled and split decks from the dealer
        final ArrayList<ArrayList<Card>> decks = dealer.dealCards(numberOfPlayers);
        // Distributes the split decks to the game players
        for (int i = 0; i < numberOfPlayers; i++) {
            players.get(i).setDeck(decks.get(i));
        }
    }

    /**
     * Randomly assigns a player from the given array as the
     * active player of the game.
     *
     * @param players player list used to pick the active player
     * @return the player which has been randomly marked as active
     */
    public Player assignActivePlayer(List<Player> players) {
        final int numberOfPlayers = players.size();
        // Generates a random integer that corresponds to a valid index
        int randomIndex = RandomGenerator.getInteger(0, numberOfPlayers - 1);
        // Selects a player from the given collection and marks it as "active"
        Player activePlayer = players.get(randomIndex);
        activePlayer.setActive(true);
        return activePlayer;
    }

    /**
     * Creates the players of this current game based on the numberOfPlayers
     * provided. By convention, the first player is always the human player.
     * In sequence, it distributes shuffled decks between all players.
     *
     * @param numberOfOpponents the number of AI opponents that must be created
     * @return a list with all the initialized game players
     */
    public ArrayList<Player> startUp(int numberOfOpponents) {
        // Creates players
        ArrayList<Player> players = createPlayers(numberOfOpponents);
        // Assigns shuffled decks
        assignDecks(players);
        return players;
    }

    /**
     * Compares the selected attribute value of each player and returns
     * the players who possess the highest value.
     *
     * @param selectedAttribute the attribute selected as the reference for comparison
     * @param players           list of players which are participating in the current round
     * @return a player list containing the players who possessed
     * a top card with the highest selected attribute value
     */
    public List<Player> getWinners(Attribute selectedAttribute, List<Player> players) {
        // Retrieves the selected attribute name
        String attributeName = selectedAttribute.getName();

        /*
        // TODO: Move this away from the core engine.
        for (Player p : players) { // TODO Maybe integrate this for loop with something else to prevent looping.
            Logger.getInstance().logToFileIfEnabled(p.getName() + "'s top card:  " + p.getTopCard().toString() + "\n\n"
                    + p.getName() + "'s deck: \n" + p.toString());
        }
        */

        // Finds the maximum attribute value
        int maxValue = players.stream()
                .map(p -> p.getTopCard().getAttributeByName(attributeName))
                .max(Attribute::compareTo)
                .get().getValue();

        // Returns a list of players with the maximum attribute value
        return players.stream()
                .filter(p -> p.getTopCard().getAttributeByName(attributeName).getValue() == maxValue)
                .collect(toCollection(ArrayList::new));
    }

    /**
     * Returns the game outcome based on the winners and players of a round.
     * The RoundOutcome result can be VICTORY, DRAW, or GAME_OVER.
     *
     * @param winners the list of players which won the round
     * @param players the list of players present on the game
     * @return RoundOutcome an object that encapsulates the round result, winner,
     * number of draws, and list of players to be removed from the game
     */
    public RoundOutcome processRoundOutcome(List<Player> winners, List<Player> players) {
        RoundOutcome outcome;

        // Collects any defeated players
        List<Player> removedPlayers = players
                .stream()
                .filter(p -> p.getDeckCount() == 0)
                .collect(toCollection(ArrayList::new));

        if (players.size() == 1) {
            // Checks if there is only one player left;
            // Declares Game Over
            outcome = new RoundOutcome(GAME_OVER);
        } else if (winners.size() == 1) {
            // Checks if there's only one winner;
            // Sets the winner player as "active"
            Player winner = winners.get(0);
            winner.setActive(true);
            // Prevents the winner player from being included to the removedPlayers list
            removedPlayers.remove(winner);
            // Declares Victory
            outcome = new RoundOutcome(VICTORY, winner, removedPlayers);
        } else {
            // Declares Draw
            outcome = new RoundOutcome(DRAW, winners, removedPlayers);
        }

        return outcome;
    }

    /**
     * Persists the final game state.
     *
     * @param gameState the final game state collected after the match is over
     */
    public void persistGameState(GameStateCollector gameState) {
        Statistics stats = new Statistics();
        stats.persistData(gameState);
    }

    // === CONVENIENCE METHODS ===

    private ArrayList<Player> createPlayers(int numberOfOpponents) {
        // Creates AI Players
        List<AIPlayer> aiPlayers = createAIPlayers(numberOfOpponents);
        // Returns a list of all game players;
        // By convention, the human player is the first element
        return new ArrayList<Player>() {{
            add(new Player(0, DEFAULT_USER_NAME));
            addAll(aiPlayers);
        }};
    }

    private List<AIPlayer> createAIPlayers(int numberOfOpponents) {
        List<AIPlayer> aiPlayers = new ArrayList<>();
        // Populates an array of AI Players
        for (int i = 1; i <= numberOfOpponents; i++) {
            AIPlayer aiPlayer = new AIPlayer(i);
            aiPlayers.add(aiPlayer);
        }
        return aiPlayers;
    }

}