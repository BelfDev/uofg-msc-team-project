package com.toptrumps.cli;

import com.toptrumps.core.Card;
import com.toptrumps.core.Attribute;
import com.toptrumps.core.Game;
import com.toptrumps.core.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineUI {

    private Scanner scanner;
    private OutputLogger logger;

    private Game game;
    private Card userCard;

    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 4;
    private static final int FIRST_ATTRIBUTE = 1;
    private static final int LAST_ATTRIBUTE = 5;


    public CommandLineUI() {
        scanner = new Scanner(System.in);
        logger = new OutputLogger();
    }

    public void startGame() {
        boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application. Does not have to exist outside the loop.

        String input;

        System.out.println("  _______            _______                               \n" +
                " |__   __|          |__   __|                              \n" +
                "    | | ___  _ __      | |_ __ _   _   _ __ ___  _ __  ___ \n" +
                "    | |/ _ \\| '_ \\     | | '__| | | | | '_ ` _ \\| '_ \\/ __|\n" +
                "    | | (_) | |_) |    | | |  | |_| |_| | | | | | |_) \\__ \\\n" +
                "    |_|\\___/| .__/     |_|_|   \\__,_( |_| |_| |_| .__/|___/\n" +
                "            | |                     |/          | |        \n" +
                "            |_|                                 |_|    \n" + "" +
                "\n");

        System.out.println("To start a new game, press f \n To see game statistics, press s");
        logger.printToLog("Game started");
        logger.printToLog("New line \n new line too.");
        input = scanner.nextLine();
        if (input.equalsIgnoreCase("f")) {
            requestNumberOfPlayers();
            showRound();
            showPlayerCard();

            //while statement to test user select attribute functionality
            while(game.getActivePlayer() != game.getUser().getName()){
                game.nextPlayer();
            }
            showActivePlayer();
            requestAttribute();

            while (!userWantsToQuit) {


                userWantsToQuit = true; // use this when the user wants to exit the game
            }
        }
        if (input.equalsIgnoreCase("s")) {

        }
        scanner.nextLine(); // clears the scanner.

        // Loop until the user wants to exit the game

        System.out.println("Thanks for playing!");
    }


    /**
     * Method to ask the user to select a number of players to play against
     */
    public void requestNumberOfPlayers() {
        try {

            System.out.println("How many players do you want to play against?");
            System.out.println("Please select " + MIN_PLAYERS + "-" + MAX_PLAYERS);

            int players = scanner.nextInt();
            while (players < MIN_PLAYERS || players > MAX_PLAYERS) {
                System.out.println("Invalid number of players selected. Please select " + MIN_PLAYERS + "-" + MAX_PLAYERS + ".");
                players = scanner.nextInt();
            }

            System.out.println("Game Start");
            game = new Game(players);
          
          
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("You didn't enter a number!");
            requestNumberOfPlayers();
        }
    }

    /**
     * Method to ask the user to select an Attribute
     */
    public void requestAttribute(){
        try {

            System.out.println("Which attribute would you like to choose?");
            System.out.println("Please select " + FIRST_ATTRIBUTE + "-" + LAST_ATTRIBUTE);

            int attribute = scanner.nextInt();
            while (attribute < FIRST_ATTRIBUTE || attribute > LAST_ATTRIBUTE) {
                System.out.println("Invalid attribute selected. Please select " + FIRST_ATTRIBUTE + "-" + LAST_ATTRIBUTE + ".");
                attribute = scanner.nextInt();
            }

            Attribute selectedAttribute = userCard.getAttributes().get(attribute-1);
            System.out.println("You selected " + selectedAttribute.getName());
            game.setSelectedAttribute(selectedAttribute);
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("You didn't enter a number!");
            requestAttribute();
        }
    }

    /**
     * Method to show the users card
     */
    public void showPlayerCard() {
        Player user = game.getUser();
        userCard = user.getTopCard();
        System.out.println("You drew \'" + userCard.getDescription() + "\':");
        System.out.println(userCard.stringAttributes());
    }

    /**
     * Method to show the current active player
     */
    public void showActivePlayer(){
        String activePlayerName = game.getActivePlayer();
        System.out.println("The active player is: " + activePlayerName);
    }

    public void showRound(){
        String round = "Round " + game.getRoundCounter();
        System.out.println(round);
        System.out.println(round + ": Players have drawn their cards");
        game.incrementRoundCounter();
    }

}