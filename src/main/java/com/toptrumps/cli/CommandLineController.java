package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.engine.Game;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import static java.util.stream.Collectors.toCollection;

public class CommandLineController {

    private final static String DECK_RESOURCE = "assets/StarCitizenDeck.txt";

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
            int numberOfOpponents = view.requestNumberOfOpponents(MIN_OPPONENTS, MAX_OPPONENTS, scanner);
            startNewGame(numberOfOpponents);
        } else if (input.equalsIgnoreCase("s")) {
            // TODO: Start statistics mode
        }

        scanner.nextLine();
    }

    private void startNewGame(int numberOfOpponents) {
        List<Card> communalPile = new ArrayList<>();
        int roundNumber = 0;
        ArrayList<Player> players = (ArrayList<Player>) gameEngine.startUp(numberOfOpponents);
        Player activePlayer = gameEngine.assignActivePlayer(players);

        while (players.size() != 1) {
            // === GAME START UP ===
            roundNumber++;

            // TODO: Double check if we are going to rely on this convention
            Player humanPlayer = players.get(0);
            // TODO: We might want to shift to member variables and drop this parameters
            if(!humanPlayer.isAIPlayer()){
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

            if(!humanPlayer.isAIPlayer()){
                onAttributeSelected(activePlayer);
            }

            // == ATTRIBUTE COMPARISON ==

            List<Player> winners = gameEngine.getWinners(selectedAttribute, players);
            List<Card> winningCards = winners.stream().map(Player::getTopCard).collect(toCollection(ArrayList::new));
            List<Card> roundCards = players.stream().map(Player::getTopCard).collect(toCollection(ArrayList::new));
            players.forEach(Player::removeTopCard);

            // == ROUND OUTCOME ==

            RoundOutcome outcome = gameEngine.processRoundOutcome(winners, players);
            players.removeAll(outcome.getRemovedPlayers());

            switch (outcome.getResult()) {
                case VICTORY:
                    activePlayer = outcome.getWinner();
                    roundCards.addAll(communalPile);
                    communalPile = new ArrayList<>();
                    activePlayer.collectCards(roundCards);
                    break;
                case DRAW:
                    communalPile.addAll(roundCards);
                    Logger.getInstance().logToFileIfEnabled("Communal cards: \n" +communalPile.toString());
                    break;
                default:
                    break;
            }

            if(!humanPlayer.isAIPlayer()){
                onRoundEnd(outcome, winningCards);
            }

        }

        onGameOver(activePlayer);
    }

    // === START OF LIFE CYCLE METHODS ===

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
    }

    private void onGameOver(Player winner) {
        Logger.getInstance().logToFileIfEnabled(winner.getName() + " won the game! \nGAME OVER!");
        view.showGameResult(winner);
        scanner.nextLine(); //clear the scanner ready for new game selection
        start();
    }

}