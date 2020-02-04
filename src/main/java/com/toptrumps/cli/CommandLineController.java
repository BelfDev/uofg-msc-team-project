package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.engine.Game;
import com.toptrumps.core.engine.GameEventListener;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.utils.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CommandLineController implements GameEventListener {

    private static final String WELCOME_BANNER_RESOURCE = "assets/banners/welcome.txt";

    private Scanner scanner;
    private OutputLogger logger;

    // TODO: Refactor the controller to use CommandLineView
    private CommandLineView view;

    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 4;

    public CommandLineController() {
        this.view = new CommandLineView();
        this.scanner = new Scanner(System.in);
        this.logger = new OutputLogger();
    }

    public void startGame() {
        String input;
        printWelcomeBanner();

        System.out.println("To start a new game, press f \n To see game statistics, press s");
        logger.printToLog("Game started");
        logger.printToLog("New line \n new line too.");
        input = scanner.nextLine();

        if (input.equalsIgnoreCase("f")) {
            int numberOfPlayers = requestNumberOfPlayers();
            Game game = new Game(numberOfPlayers, this);
            System.out.println("Game Start");
            game.start();
        }

        if (input.equalsIgnoreCase("s")) {

        }
        scanner.nextLine(); // clears the scanner.
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
        System.out.println("\n"+outcome+"\n");
    }

    @Override
    public void onRoundEnd(RoundOutcome outcome, Player eliminatedPlayer){
        System.out.println("\n"+outcome);
        System.out.println(eliminatedPlayer.getName() + " has been eliminated from the game\n");
    }

    @Override
    public void onGameOver(Player winner) {
        String message = String.format("GAME OVER, %s won", winner.getName());
        System.out.println(message);
    }

    private void showRound(int roundNumber) {
        String message = String.format("Round %d: Players have drawn their cards", roundNumber);
        System.out.println(message);
    }

    private int requestNumberOfPlayers() {
        try {
            System.out.println("How many players do you want to play against?");
            System.out.println("Please select " + MIN_PLAYERS + " - " + MAX_PLAYERS);

            int numberOfPlayers = scanner.nextInt();
            while (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
                System.out.println("Invalid number of players selected. Please select " + MIN_PLAYERS + "-" + MAX_PLAYERS + ".");
                numberOfPlayers = scanner.nextInt();
            }

            return numberOfPlayers;

        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("You didn't enter a number!");
            return requestNumberOfPlayers();
        }
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