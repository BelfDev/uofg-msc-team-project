package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.engine.Game;
import com.toptrumps.core.engine.GameLifeCycle;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.utils.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CommandLineController implements GameLifeCycle {

    private final static String DECK_RESOURCE = "assets/StarCitizenDeck.txt";
    private static final String WELCOME_BANNER_RESOURCE = "assets/banners/welcome.txt";

    private Scanner scanner;
    private OutputLogger logger;

    // TODO: Refactor the controller to use CommandLineView
    private CommandLineView view;
    private Game gameEngine;

    private static final int MIN_OPPONENTS = 1;
    private static final int MAX_OPPONENTS = 4;

    public CommandLineController() {
        this.gameEngine = new Game(DECK_RESOURCE);
        this.view = new CommandLineView();
        this.scanner = new Scanner(System.in);
        this.logger = new OutputLogger();
    }

    public void start() {
        presentWelcomeMessage();

        String input = scanner.nextLine();
        // TODO: Create an Enum to encapsulate the flags
        if (input.equalsIgnoreCase("f")) {
            // Start the game
            int numberOfOpponents = requestNumberOfOpponents();
            startNewGame(numberOfOpponents);
        } else if (input.equalsIgnoreCase("s")) {
            // TODO: Start statistics mode
        }

        scanner.nextLine();
    }

    private void startNewGame(int numberOfOpponents) {
        boolean isGameOver = false;
        int rounderNumber = 0;
        List<Player> players;
        Player activePlayer;

        while (isGameOver) {
            // === GAME START UP ===
            System.out.println("Game Start");
            rounderNumber++;

            players = gameEngine.startUp(numberOfOpponents);
            activePlayer = gameEngine.assignActivePlayer(players);
            // TODO: Double check if we are going to rely on this convention
            Card humanPlayerCard = players.get(0).getTopCard();
            // TODO: We might want to shift to member variables and drop this parameters
            onRoundStart(activePlayer, humanPlayerCard, rounderNumber);

            // == ATTRIBUTE SELECTION ==




        }

    }

    private void presentWelcomeMessage() {
        printWelcomeBanner();

        // TODO: Move this to the view
        System.out.println("To start a new game, press f \n To see game statistics, press s");
        logger.printToLog("Game started");
        logger.printToLog("New line \n new line too.");
    }

    private int requestNumberOfOpponents() {
        try {
            // TODO: Move this to the view class
            System.out.println("How many players do you want to play against?");
            System.out.println("Please select " + MIN_OPPONENTS + " - " + MAX_OPPONENTS);

            int numberOfOpponents = scanner.nextInt();
            while (numberOfOpponents < MIN_OPPONENTS || numberOfOpponents > MAX_OPPONENTS) {
                System.out.println("Invalid number of players selected. Please select " + MIN_OPPONENTS + "-" + MAX_OPPONENTS + ".");
                numberOfOpponents = scanner.nextInt();
            }
            return numberOfOpponents;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("You didn't enter a number!");
            return requestNumberOfOpponents();
        }
    }

    @Override
    public void onRoundStart(Player activePlayer, Card humanPlayerCard, int roundNumber) {
        showRound(roundNumber);
        showPlayerCard(humanPlayerCard);
        showActivePlayer(activePlayer);
    }

    @Override
    public Attribute onRequestSelection(Card card) {
        return requestAttribute(card);
    }

    @Override
    public void onAttributeSelected(Player activePlayer) {
        String selectedAttributeName = activePlayer.getSelectedAttribute().getName();
        String playerName = activePlayer.isAIPlayer() ? activePlayer.getName() : "You";
        String message = String.format("%s selected the attribute %s", playerName, selectedAttributeName);
        System.out.println(message);
    }

    @Override
    public void onRoundEnd(RoundOutcome outcome) {
        showRoundResult(outcome);
        ArrayList<Player> removedPlayers = outcome.getRemovedPlayers();
        if (!removedPlayers.isEmpty()) {
            showRemovedPlayers(removedPlayers);
        }
    }

    private void showRoundResult(RoundOutcome outcome) {
        RoundOutcome.Result result = outcome.getResult();
        String outcomeMessage = "\nThe round resulted in a: ";
        switch (result) {
            case VICTORY:
                outcomeMessage += "Victory!\nThe winner is: " + outcome.getWinner().getName();
                break;
            case DRAW:
                outcomeMessage += "Draw\nThe score was tied between: ";
                for (Player player : outcome.getDraws()) {
                    if (player == outcome.getDraws().get(outcome.getDraws().size() - 1)) {
                        outcomeMessage += " and " + player.getName();
                    } else if (player == outcome.getDraws().get(outcome.getDraws().size() - 2)) {
                        outcomeMessage += player.getName();
                    } else {
                        outcomeMessage += player.getName() + ", ";
                    }
                }
                break;
        }
        System.out.println(outcomeMessage);
    }

    private void showRemovedPlayers(ArrayList<Player> removedPlayers) {
        String removedPlayersString = "";
        if (removedPlayers.size() == 1) {
            removedPlayersString += removedPlayers.get(0).getName() + " has been removed from the game";
        } else {
            Player lastPlayerInList = removedPlayers.get(removedPlayers.size() - 1);
            Player secondLastPlayerInList = removedPlayers.get(removedPlayers.size() - 2);
            for (Player player : removedPlayers) {
                if (player == lastPlayerInList) {
                    removedPlayersString += " and " + player.getName() + " have been removed from the game";
                } else if (player == secondLastPlayerInList) {
                    removedPlayersString += player.getName();
                } else {
                    removedPlayersString += player.getName() + ", ";
                }
            }
        }
        System.out.println(removedPlayersString);
    }

    @Override
    public void onGameOver(Player winner) {
        String message = String.format("\nGAME OVER, %s won", winner.getName());
        System.out.println(message);
    }

    private void showRound(int roundNumber) {
        String message = String.format("\n\nRound %d: Players have drawn their cards", roundNumber);
        System.out.println(message);
    }



    private void showPlayerCard(Card humanPlayerCard) {
        System.out.println("You drew \'" + humanPlayerCard.getName() + "\':");
        printCardAttributes(humanPlayerCard);
    }

    private void printCardAttributes(Card card) {
        List<Attribute> attributes = card.getAttributes();
        for (int i = 0; i < attributes.size(); i++) {
            String attributeName = attributes.get(i).getName();
            int attributeValue = attributes.get(i).getValue();
            String message = String.format("  %d:    %-12s%d\n", i + 1, attributeName, attributeValue);
            System.out.println(message);
        }
    }

    private void showActivePlayer(Player activePlayer) {
        System.out.println("The active player is: " + activePlayer.getName());
    }

    private Attribute requestAttribute(Card card) {
        try {
            final List<Attribute> attributes = card.getAttributes();
            final int numberOfAttributes = attributes.size();
            System.out.println("Which attribute would you like to choose?");
            System.out.println("Please select 1 - " + numberOfAttributes);

            int selectedAttributeIndex = scanner.nextInt();
            while (selectedAttributeIndex < 1 || selectedAttributeIndex > numberOfAttributes) {
                System.out.println("Invalid attribute selected. Please select 1 -" + numberOfAttributes + ".");
                selectedAttributeIndex = scanner.nextInt();
            }

            return attributes.get(selectedAttributeIndex - 1);
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("You didn't enter a number!");
            return requestAttribute(card);
        }
    }

    private void printWelcomeBanner() {
        InputStream inputStream = ResourceLoader.getResource(WELCOME_BANNER_RESOURCE);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String banner = br.lines().collect(Collectors.joining("\n"));
        System.out.println(banner);
    }

}