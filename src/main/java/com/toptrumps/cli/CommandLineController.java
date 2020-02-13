package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.engine.GameEngine;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.core.utils.MapUtils;
import com.toptrumps.core.utils.ResourceLoader;
import com.toptrumps.db.Statistics;

import java.util.*;

import static com.toptrumps.cli.PrintOptions.IS_TEST_MODE;
import static java.util.stream.Collectors.toCollection;

/**
 * This class controls the command line game state. It orchestrates
 * the game dynamics by requesting core game logic from the GameEngine
 * and presenting output through the CommandLineView. The user is provided
 * with multiple command line options to navigate through the game.
 */
public class CommandLineController {

    private static final int MIN_OPPONENTS = 1;
    private static final int MAX_OPPONENTS = 4;

    private Scanner scanner;
    private CommandLineView view;
    private GameEngine gameEngine;
    private boolean skipPrintAnimation = false;

    /**
     * Constructs a new CommandLineController with a CommandLineView,
     * GameEngine, and Scanner.
     */
    public CommandLineController() {
        // Game view
        this.view = new CommandLineView();
        String deckResource = ResourceLoader.getDeckFileName();
        // Game core engine model
        this.gameEngine = new GameEngine(deckResource);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the Command Line game mode.
     */
    public void start() {
        view.showWelcomeMessage();
        String input = scanner.nextLine();
        // Listens to user input until a supported option is provided
        while (!GameOption.contains(input)) {
            view.showInvalidInput();
            input = scanner.nextLine();
        }
        // Checks the input flag
        switch (GameOption.fromInput(input)) {
            case GAME_MODE:
                // Starts new game with the provided number of opponents
                int numberOfOpponents = requestNumberOfOpponents();
                startNewGame(numberOfOpponents);
                break;
            case STATISTICS_MODE:
                // Shows statistics about past games
                presentStatistics();
                break;
            case QUIT:
                // Quits game
                view.showGoodbyeMessage();
                break;
            default:
                break;
        }
    }

    private void startNewGame(int numberOfOpponents) {
        // Initializes the game state
        ArrayList<Card> communalPile = new ArrayList<>();
        int roundNumber = 0;
        int numberOfDraws = 0;
        Player humanPlayer = null;
        ArrayList<Player> players = gameEngine.startUp(numberOfOpponents);
        Player activePlayer = gameEngine.assignActivePlayer(players);
        HashMap<Player, Integer> roundWinsMap = getRoundWinsMap(players);
        RoundOutcome outcome = null;
        boolean isHumanAlive = true;

        // Checks if the game has come to an end;
        // A game ends when there's only one player left
        while (players.size() != 1) {
            // == GAME START UP ==
            roundNumber++;
            Logger.getInstance().logToFileIfEnabled("Round number: " + roundNumber);
            // Retrieves the human player;
            humanPlayer = getHumanPlayer(players);
            isHumanAlive = humanPlayer != null;
            // Checks if the humanPlayer has not been eliminated
            if(!isHumanAlive){
                skipPrintAnimation = true;
            }
            view.showRoundStart(activePlayer, humanPlayer, roundNumber, communalPile.size(), skipPrintAnimation);
            if (isHumanAlive) {
                view.showHumanInformation(humanPlayer);
            }

            // == ATTRIBUTE SELECTION ==
            // Handles attribute selection:
            // Human Player is asked for an input;
            // AI Player automatically selects a random max attribute;
            Attribute selectedAttribute = getSelectedAttribute(activePlayer);
            // Invokes the lifecycle method
            onAttributeSelected(activePlayer);

            // == ATTRIBUTE COMPARISON ==
            // Evaluate who were the winners of the current round
            List<Player> winners = gameEngine.getWinners(selectedAttribute, players);
            // Retrieve the round cards
            List<Card> winningCards = winners.stream().map(Player::getTopCard).collect(toCollection(ArrayList::new));
            // Extracts a shuffled list of round cards
            List<Card> roundCards = gameEngine.getShuffledRoundCards(players);

            // Remove the topCard from each player
            players.forEach(Player::removeTopCard);

            // == ROUND OUTCOME ==
            // Processes the round outcome
            outcome = gameEngine.processRoundOutcome(winners, players);
            // Removes defeated players
            players.removeAll(outcome.getRemovedPlayers());

            switch (outcome.getResult()) {
                case VICTORY:
                    // Sets new active player
                    activePlayer = outcome.getWinner();
                    // Redistributes cards
                    roundCards.addAll(communalPile);
                    activePlayer.collectCards(roundCards);
                    // Resets communal pile
                    communalPile = new ArrayList<>();
                    // Increments round wins for the new active player
                    MapUtils.increment(roundWinsMap, activePlayer);
                    break;
                case DRAW:
                    // Add cards to the communal pile
                    communalPile.addAll(roundCards);
                    Logger.getInstance().logToFileIfEnabled("Communal cards: \n" + communalPile.toString());
                    numberOfDraws++;
                    break;
                default:
                    break;
            }
            // Invokes the lifecycle method
            onRoundEnd(outcome, winningCards, isHumanAlive);
        }

        // == STATISTICS ==
        // Collects the final game state
        GameStateCollector gameState = GameStateCollector.Builder.newInstance()
                .setFinalWinner(activePlayer)
                .setNumberOfRounds(roundNumber)
                .setNumberOfDraws(numberOfDraws)
                .setRoundWinsMap(roundWinsMap)
                .build();

        // Persists the collected state
        gameEngine.persistGameState(gameState);

        // Invoke the lifecycle method 
        skipPrintAnimation = false;
        onGameOver(activePlayer, roundWinsMap);
    }

    // === LIFECYCLE METHODS ===

    private Attribute onRequestSelection(Card card) {
        try {
            // Requests an attribute selection based on the current card
            final List<Attribute> attributes = card.getAttributes();
            final int numberOfAttributes = attributes.size();
            view.requestSelection(numberOfAttributes);

            // Listens to user input until a valid attribute is selected
            int selectedAttributeIndex = scanner.nextInt();
            while (selectedAttributeIndex < 1 || selectedAttributeIndex > numberOfAttributes) {
                view.showInvalidSelection(numberOfAttributes);
                selectedAttributeIndex = scanner.nextInt();
            }
            scanner.nextLine();

            // Returns the selected attribute
            return attributes.get(selectedAttributeIndex - 1);
        } catch (InputMismatchException e) {
            // In case of error, restarts the request process
            scanner.nextLine();
            view.showNoNumberSelected();
            return onRequestSelection(card);
        }
    }

    private void onAttributeSelected(Player activePlayer) {
        String selectedAttributeName = activePlayer.getSelectedAttribute().getName();
        String playerName = activePlayer.isAIPlayer() ? activePlayer.getName() : "You";

        view.showSelectedAttribute(playerName, selectedAttributeName, skipPrintAnimation);
    }

    private void onRoundEnd(RoundOutcome outcome, List<Card> winningCards, boolean isHumanAlive) {
        view.showRoundResult(outcome, winningCards, skipPrintAnimation);
        List<Player> removedPlayers = outcome.getRemovedPlayers();
        // Shows removed players if there's any
        if (!removedPlayers.isEmpty()) {
            view.showRemovedPlayers(removedPlayers, skipPrintAnimation);
        }
        if (isHumanAlive) {
            selectNextRound();
        }
    }

    private void onGameOver(Player winner, HashMap<Player, Integer> roundWinsMap) {
        view.showGameResult(winner, roundWinsMap);
        selectNextRound();
        start();
    }

    // === CONVENIENCE METHODS ===

    private int requestNumberOfOpponents() {
        try {
            view.showRequestNumberOfOpponents(MIN_OPPONENTS, MAX_OPPONENTS);
            int numberOfOpponents = scanner.nextInt();
            // Listens to user input until a valid number is selected
            while (numberOfOpponents < MIN_OPPONENTS || numberOfOpponents > MAX_OPPONENTS) {
                view.showInvalidNumberOfPlayers(MIN_OPPONENTS, MAX_OPPONENTS);
                numberOfOpponents = scanner.nextInt();
            }
            scanner.nextLine();
            // Returns the selected number of opponents
            return numberOfOpponents;
        } catch (InputMismatchException e) {
            // In case of error, restarts the request process
            scanner.nextLine();
            view.showNotANumber();
            return requestNumberOfOpponents();
        }
    }

    private Attribute getSelectedAttribute(Player activePlayer) {
        Attribute selectedAttribute;
        // Checks if the active player is an AIPlayer instance
        if (activePlayer.isAIPlayer()) {
            // Selects the attribute automatically
            selectedAttribute = ((AIPlayer) activePlayer).selectAttribute();
        } else {
            // Asks human player for new input
            selectedAttribute = onRequestSelection(activePlayer.getTopCard());
            activePlayer.setSelectedAttribute(selectedAttribute);
        }
        return selectedAttribute;
    }

    private void selectNextRound() {
        if (!IS_TEST_MODE) {
            view.showNextRoundMessage();
            scanner.nextLine();
        }
    }

    private HashMap<Player, Integer> getRoundWinsMap(List<Player> players) {
        // Returns a map of each player and their respective number of round wins
        return new HashMap<Player, Integer>() {{
            players.forEach(player -> {
                put(player, 0);
            });
        }};
    }

    private Player getHumanPlayer(ArrayList<Player> players) {
        // By convention, the human player is always the first element;
        // The ternary operator checks if the human player has been eliminated
        return players.get(0).isAIPlayer() ? null : players.get(0);
    }

    private void presentStatistics() {
        Statistics stats = new Statistics();
        view.showStats(stats);
        view.showNextRoundMessage();
        scanner.nextLine();
        start();
    }

}
