package com.toptrumps.core.engine;

import com.toptrumps.cli.Logger;
import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.card.Dealer;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.utils.RandomGenerator;
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.db.IndividualGameDAOImpl;
import com.toptrumps.db.PersistentDataDAOImpl;
import com.toptrumps.db.RoundWinnersDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static com.toptrumps.core.engine.RoundOutcome.Result.*;
import static java.util.stream.Collectors.toCollection;

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

    private ArrayList<Player> createPlayers(int numberOfOpponents) {
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

    public void assignDecks(ArrayList<Player> players) {
        final int numberOfPlayers = players.size();
        final ArrayList<ArrayList<Card>> decks = dealer.dealCards(numberOfPlayers);

        // Distribute the split decks to the game players
        for (int i = 0; i < numberOfPlayers; i++) {
            players.get(i).setDeck(decks.get(i));
        }
    }

    public Player assignActivePlayer(List<Player> players) {
        final int numberOfPlayers = players.size();
        int randomIndex = RandomGenerator.getInteger(0, numberOfPlayers - 1);
        Player activePlayer = players.get(randomIndex);
        activePlayer.setActive(true);
        return activePlayer;
    }

    public List<Player> startUp(int numberOfOpponents) {
        ArrayList<Player> players = createPlayers(numberOfOpponents);
        assignDecks(players);
        return players;
    }

    public List<Player> getWinners(Attribute selectedAttribute, List<Player> players) {
        // Attribute Comparison
        // TODO: Optimize this process -> try to loop only once and retrieve the player with max selected attribute value
        String attributeName = selectedAttribute.getName();

        for (Player p : players){ // TODO Maybe integrate this for loop with something else to prevent looping.
            Logger.getInstance().logToFileIfEnabled(p.getName() + "'s top card:  "+ p.getTopCard().toString() + "\n\n"
                    + p.getName() + "'s deck: \n" + p.toString());
        }

        // Find maxValue
        int maxValue = players.stream()
                .map(p -> p.getTopCard().getAttributeByName(attributeName))
                .max(Attribute::compareTo)
                .get().getValue();

        // Find playersWithMaxValue
        return (ArrayList<Player>) players.stream()
                .filter(p -> p.getTopCard().getAttributeByName(attributeName).getValue() == maxValue)
                .collect(toCollection(ArrayList::new));
    }

    public RoundOutcome processRoundOutcome(List<Player> winners, List<Player> players) {
        // Round outcome
        RoundOutcome outcome;


        //collect any defeated players and remove them from the game
        List<Player> removedPlayers = players
                .stream()
                .filter(p -> p.getDeckCount() == 0)
                .collect(toCollection(ArrayList::new));

        if (players.size() == 1) {
            outcome = new RoundOutcome(GAME_OVER);
        } else if (winners.size() == 1) {
            Player winner = winners.get(0);
            winner.setActive(true);

            if (removedPlayers.contains(winner)) {
                removedPlayers.remove(winner);
            }

            outcome = new RoundOutcome(VICTORY, winner, removedPlayers);
        } else {
            outcome = new RoundOutcome(DRAW, winners, removedPlayers);
        }

        return outcome;
    }

    public void persistGameState(GameStateCollector gameState) {
        // TODO: Update with new DAOs
        IndividualGameDAOImpl individualGameDAO = new IndividualGameDAOImpl();
        PersistentDataDAOImpl persistentDataDAO = new PersistentDataDAOImpl();
        RoundWinnersDAOImpl roundWinnersDAO = new RoundWinnersDAOImpl();

        individualGameDAO.create(gameState);
        persistentDataDAO.update();
        // TODO: Merge DAOs and simplify methods
        roundWinnersDAO.create();
    }


}