package com.toptrumps.cli;

import com.toptrumps.core.Card;
import com.toptrumps.core.Game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineUI {

    private Scanner scanner;
    private OutputLogger logger;
    private Game game;
    private final int MIN_PLAYERS = 1;
    private final int MAX_PLAYERS = 4;
    private final int FIRST_ATTRIBUTE = 1;
    private final int LAST_ATTRIBUTE = 5;


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
            showActivePlayer();

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

            int players = scanner.nextInt();
            while (players < FIRST_ATTRIBUTE || players > LAST_ATTRIBUTE) {
                System.out.println("Invalid attribute selected. Please select " + FIRST_ATTRIBUTE + "-" + LAST_ATTRIBUTE + ".");
                players = scanner.nextInt();
                scanner.nextInt();
            }
            //TODO: retrieve the selected attibute
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("You didn't enter a number!");
            requestAttribute();
        }
    }

    /**
     * Method to show the users card
     */
    public void showPlayerCard(Card c) {
        System.out.println("You drew \'" + c.getDescription() + "\':");
        System.out.println(c.stringAttributes());
    }

    /**
     * Method to show the current active player
     */
    public void showActivePlayer(){
        String activePlayerName = game.getActivePlayer();
        System.out.println("The active player is: " + activePlayerName);
    }

}