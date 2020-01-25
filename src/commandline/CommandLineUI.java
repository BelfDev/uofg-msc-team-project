package commandline;

import java.util.Scanner;

public class CommandLineUI {

    private Scanner scanner;
    private OutputLogger logger;


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

}
