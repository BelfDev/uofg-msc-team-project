package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.engine.Game;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.core.utils.MapUtils;
import com.toptrumps.db.Statistics;

import java.util.*;

import static com.toptrumps.cli.PrintOptions.IS_TEST_MODE;
import static java.util.stream.Collectors.toCollection;

public class CommandLineController {

    private final static String DECK_RESOURCE = "assets/WitcherDeck.txt";

    private Scanner scanner;

    // TODO: Refactor the controller to use CommandLineView
    private CommandLineView view;
    private Game gameEngine;
    private static final int MIN_OPPONENTS = 1;
    private static final int MAX_OPPONENTS = 4;

    public CommandLineController() {
        this.gameEngine = new Game(DECK_RESOURCE);
        this.view = new CommandLineView();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        view.showWelcomeMessage();

        String input = scanner.nextLine();
        // TODO: Create an Enum to encapsulate the flags
        if (input.equalsIgnoreCase("f")) {
            // Start the game
            int numberOfOpponents = requestNumberOfOpponents();
            startNewGame(numberOfOpponents);
        } else if (input.equalsIgnoreCase("s")) {
            // TODO: Start statistics mode
            Statistics stats = new Statistics();
            view.showStats(stats);
            start();
        }

        scanner.nextLine();
    }

    private void startNewGame(int numberOfOpponents) {
        List<Card> communalPile = new ArrayList<>();
        int roundNumber = 0;
        int numberOfDraws = 0;
        ArrayList<Player> players = (ArrayList<Player>) gameEngine.startUp(numberOfOpponents);
        Player activePlayer = gameEngine.assignActivePlayer(players);
        HashMap<Player, Integer> roundWinsMap = new HashMap<Player, Integer>() {{
            players.forEach(player -> {
                put(player, 0);
            });
        }};
        RoundOutcome outcome = null;

        while (players.size() != 1) {
            // === GAME START UP ===
            roundNumber++;

            // TODO: Double check if we are going to rely on this convention
            Player humanPlayer = players.get(0);
            // TODO: We might want to shift to member variables and drop this parameters
            if (!humanPlayer.isAIPlayer()) {
                view.showRoundStart(activePlayer, humanPlayer, roundNumber, communalPile.size());
            }

            // == ATTRIBUTE SELECTION ==

            Attribute selectedAttribute;
            if (activePlayer.isAIPlayer()) {
                selectedAttribute = ((AIPlayer) activePlayer).selectAttribute();
            } else {
                selectedAttribute = onRequestSelection(activePlayer.getTopCard());
                activePlayer.setSelectedAttribute(selectedAttribute);
            }

            if (!humanPlayer.isAIPlayer()) {
                onAttributeSelected(activePlayer);
            }

            // == ATTRIBUTE COMPARISON ==

            List<Player> winners = gameEngine.getWinners(selectedAttribute, players);
            List<Card> winningCards = winners.stream().map(Player::getTopCard).collect(toCollection(ArrayList::new));
            List<Card> roundCards = players.stream().map(Player::getTopCard).collect(toCollection(ArrayList::new));
            players.forEach(Player::removeTopCard);

            // == ROUND OUTCOME ==

            outcome = gameEngine.processRoundOutcome(winners, players);
            players.removeAll(outcome.getRemovedPlayers());

            switch (outcome.getResult()) {
                case VICTORY:
                    activePlayer = outcome.getWinner();
                    roundCards.addAll(communalPile);
                    communalPile = new ArrayList<>();
                    activePlayer.collectCards(roundCards);
                    MapUtils.increment(roundWinsMap, activePlayer);
                    break;
                case DRAW:
                    communalPile.addAll(roundCards);
                    numberOfDraws++;
                    break;
                default:
                    break;
            }

            if (!humanPlayer.isAIPlayer()) {
                onRoundEnd(outcome, winningCards);
            }

        }

        // == STATISTICS ==

        GameStateCollector gameState = GameStateCollector.Builder.newInstance()
                .setFinalWinner(activePlayer)
                .setNumberOfRounds(roundNumber)
                .setNumberOfDraws(numberOfDraws)
                .setRoundWinsMap(roundWinsMap)
                .build();

        gameEngine.persistGameState(gameState);

        if(outcome.getRemovedPlayers().get(0).isAIPlayer() && activePlayer.isAIPlayer()){
            view.showAutomaticCompletion();
        }
        onGameOver(activePlayer, roundWinsMap);
    }

    // === START OF LIFE CYCLE METHODS ===

    private int requestNumberOfOpponents() {
        try {
            view.showRequestNumberOfOpponents(MIN_OPPONENTS, MAX_OPPONENTS);

            int numberOfOpponents = scanner.nextInt();
            while (numberOfOpponents < MIN_OPPONENTS || numberOfOpponents > MAX_OPPONENTS) {
                view.showInvalidNumberOfPlayers(MIN_OPPONENTS, MAX_OPPONENTS);
                numberOfOpponents = scanner.nextInt();
            }
            scanner.nextLine();
            return numberOfOpponents;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            view.showNotANumber();
            return requestNumberOfOpponents();
        }
    }

    private Attribute onRequestSelection(Card card) {
        try {
            final List<Attribute> attributes = card.getAttributes();
            final int numberOfAttributes = attributes.size();
            view.requestSelection(numberOfAttributes);

            int selectedAttributeIndex = scanner.nextInt();
            while (selectedAttributeIndex < 1 || selectedAttributeIndex > numberOfAttributes) {
                view.showInvalidSelection(numberOfAttributes);
                selectedAttributeIndex = scanner.nextInt();
            }
            scanner.nextLine();

            return attributes.get(selectedAttributeIndex - 1);
        } catch (InputMismatchException e) {
            scanner.nextLine();
            view.showNoNumberSelected();
            return onRequestSelection(card);
        }
    }

    private void onAttributeSelected(Player activePlayer) {
        String selectedAttributeName = activePlayer.getSelectedAttribute().getName();
        String playerName = activePlayer.isAIPlayer() ? activePlayer.getName() : "You";
        view.showSelectedAttribute(playerName, selectedAttributeName);
    }

    private void onRoundEnd(RoundOutcome outcome, List<Card> winningCards) {
        view.showRoundResult(outcome, winningCards);
        List<Player> removedPlayers = outcome.getRemovedPlayers();
        if (!removedPlayers.isEmpty()) {
            view.showRemovedPlayers(removedPlayers);
        }
        selectNextRound();
    }

    private void selectNextRound() {
        if (!IS_TEST_MODE) {
            view.showNextRoundMessage();
            scanner.nextLine();
        }
    }

    private void onGameOver(Player winner, Map<Player, Integer> roundWinsMap) {
        view.showGameResult(winner, roundWinsMap);
        start();
    }

}