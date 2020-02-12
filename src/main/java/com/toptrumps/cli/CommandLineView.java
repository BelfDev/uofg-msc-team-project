package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.utils.ResourceLoader;
import com.toptrumps.db.Statistics;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import static com.toptrumps.cli.PrintOptions.*;

public class CommandLineView {

    private static final String WELCOME_BANNER_RESOURCE = "assets/banners/welcome.txt";


    public void showWelcomeMessage() {
        printWelcomeBanner();
        typePrint(SLOW_SPEED, "\nTo start a new game, enter f \nTo see game statistics, enter s \nTo quit, enter q");
        Logger.getInstance().logToFileIfEnabled("Game started");
    }

    public void showInvalidInput(){
        typePrint(SLOW_SPEED, "Invalid input detected - please select f, s or q");
    }

    public void showGoodbyeMessage(){
        typePrint(SLOW_SPEED, "\nThanks for playing!");
    }

    private void printWelcomeBanner() {
        InputStream inputStream = ResourceLoader.getResource(WELCOME_BANNER_RESOURCE);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String banner = br.lines().collect(Collectors.joining("\n"));
        typePrint(LIGHT_SPEED, banner);
    }
      
    public void showStats(Statistics stats){
        ArrayList<String> statsMessages = new ArrayList<String>();
        statsMessages.add("\nThe statistics for your games are:\n");
        statsMessages.add(String.format("%-33s%3d", "Total number of games played:", stats.getGamesPlayed()));
        statsMessages.add(String.format("%-33s%3d", "Number of Human wins:", stats.getHumanWins()));
        statsMessages.add(String.format("%-33s%3d", "Number of AI wins:", stats.getAiWins()));
        statsMessages.add(String.format("%-33s%3d", "Average number of round draws:", stats.getAverageDraws()));
        statsMessages.add(String.format("%-33s%3d", "Maximum number of rounds:", stats.getMaxRounds()));
        for(String message: statsMessages){
            typePrint(MODERATE_SPEED, message);
        }
        System.out.println("\n\n");
    }

    public void showRequestNumberOfOpponents(int MIN_OPPONENTS, int MAX_OPPONENTS) {
        typePrint(SLOW_SPEED, "How many players do you want to play against?");
        typePrint(SLOW_SPEED, "Please select " + MIN_OPPONENTS + " - " + MAX_OPPONENTS);
    }

    public void showInvalidNumberOfPlayers(int MIN_OPPONENTS, int MAX_OPPONENTS) {
        typePrint(MODERATE_SPEED, "Invalid number of players selected. Please select " + MIN_OPPONENTS + "-" + MAX_OPPONENTS + ".");
    }

    public void showNotANumber() {
        typePrint(MODERATE_SPEED, "You didn't enter a number!");
    }

    public void showRoundStart(Player activePlayer, Player humanPlayer, int roundNumber, int communalPileSize) {
        showRoundNumber(roundNumber);
        showActivePlayer(activePlayer, humanPlayer);
        showCommunalPileSize(communalPileSize);
    }

    public void showHumanInformation(Player humanPlayer){
        showNumberOfHumanCards(humanPlayer);
        pausePrinting(SHORT_PAUSE);
        typePrint(SLOW_SPEED, "\nYour card is:");
        showCard(humanPlayer.getTopCard());
    }

    private void showRoundNumber(int roundNumber) {
        typePrint(MODERATE_SPEED, "\n\n--------------------");
        typePrint(MODERATE_SPEED, "    Round " + roundNumber);
        typePrint(MODERATE_SPEED, "--------------------");
    }

    private void showActivePlayer(Player activePlayer, Player humanPlayer) {
        String message = "";
        if (activePlayer == humanPlayer) {
            message += "You are the active player";
        } else {
            message += activePlayer.getName() + " is the active player";
        }
        typePrint(SLOW_SPEED, message);
    }

    private void showCommunalPileSize(int communalPileSize) {
        String message = String.format("There are %d cards in the communal pile", communalPileSize);
        typePrint(SLOW_SPEED, message);
    }

    private void showNumberOfHumanCards(Player humanPlayer) {
        if (!humanPlayer.isAIPlayer()) {
            int numberOfHumanCards = humanPlayer.getDeckCount();
            String cardOrCards = numberOfHumanCards == 1 ? "card" : "cards";
            String message = String.format("You have %d %s in your hand", numberOfHumanCards, cardOrCards);
            typePrint(SLOW_SPEED, message);
        }
    }

    private void showCard(Card card) {
        List<Attribute> attributes = card.getAttributes();
        String name = centreCardName(card.getName());
        pausePrinting(MODERATE_PAUSE);
        typePrint(FAST_SPEED, "\t-----------------------");
        typePrint(FAST_SPEED, String.format("\t|%-21s|", name));
        typePrint(FAST_SPEED, String.format("\t|%21s|", ""));
        typePrint(FAST_SPEED, String.format("\t|%21s|", ""));

        for (int i = 0; i < attributes.size(); i++) {
            String attributeName = attributes.get(i).getName();
            int attributeValue = attributes.get(i).getValue();
            String message = String.format("\t|%d: %-16s%2d|", i + 1, attributeName, attributeValue);
            typePrint(FAST_SPEED, message);
        }

        typePrint(FAST_SPEED, "\t-----------------------");
    }

    private void showCards(List<Card> cards) {
        List<Attribute> attributes = cards.get(0).getAttributes();
        pausePrinting(MODERATE_PAUSE);

        int linesRequired = 5 + attributes.size();
        ArrayList<String> printLines = new ArrayList<String>();
        for (int i = 0; i < linesRequired; i++) {
            printLines.add("");
        }

        for (Card card : cards) {
            attributes = card.getAttributes();
            String name = centreCardName(card.getName());
            printLines.set(0, printLines.get(0) + "\t-----------------------");
            printLines.set(1, printLines.get(1) + String.format("\t|%-21s|", name));
            printLines.set(2, printLines.get(2) + String.format("\t|%21s|", ""));
            printLines.set(3, printLines.get(3) + String.format("\t|%21s|", ""));
            for (int i = 0; i < attributes.size(); i++) {
                String attributeName = attributes.get(i).getName();
                int attributeValue = attributes.get(i).getValue();
                printLines.set(i + 4, printLines.get(i + 4) + String.format("\t|%d: %-16s%2d|", i + 1, attributeName, attributeValue));
            }
            printLines.set(printLines.size() - 1, printLines.get(printLines.size() - 1) + "\t-----------------------");
        }

        for (String line : printLines) {
            typePrint(LIGHT_SPEED, line);
        }
    }

    private String centreCardName(String name) {
        int totalLength = 21;
        int blankSpaceAvailable = totalLength - name.length();
        int blankSpaceRequired;

        if (blankSpaceAvailable % 2 != 0) {
            blankSpaceRequired = blankSpaceAvailable / 2;
        } else {
            blankSpaceRequired = (blankSpaceAvailable / 2) - 1;
        }

        for (int i = 0; i <= blankSpaceRequired; i++) {
            name = " " + name;
        }
        return name;
    }

    public void requestSelection(int numberOfAttributes) {
        typePrint(SLOW_SPEED, "Which attribute would you like to choose?");
        typePrint(SLOW_SPEED, "Please select 1 - " + numberOfAttributes);
    }

    public void showInvalidSelection(int numberOfAttributes) {
        typePrint(MODERATE_SPEED, "Invalid attribute selected. Please select 1 -" + numberOfAttributes + ".");
    }

    public void showNoNumberSelected() {
        typePrint(MODERATE_SPEED, "You didn't enter a number!");
    }

    public void showSelectedAttribute(String playerName, String selectedAttribute) {
        String message = String.format("%s selected the attribute %s", playerName, selectedAttribute);
        typePrint(SLOW_SPEED, message);
    }

    public void showRoundResult(RoundOutcome outcome, List<Card> winningCards) {
        pausePrinting(MODERATE_PAUSE);
        RoundOutcome.Result result = outcome.getResult();
        String outcomeMessage = "\n";
        switch (result) {
            case VICTORY:
                typePrint(SLOW_SPEED, "\nThe winning card is...");
                showCard(winningCards.get(0));
                if (outcome.getWinner().isAIPlayer()) {
                    outcomeMessage += outcome.getWinner().getName() + " is the winner of the round!";
                } else {
                    outcomeMessage += "You win the round!";
                }

                break;
            case DRAW:
                typePrint(SLOW_SPEED, "\nThe winning cards are...");
                showCards(winningCards);
                outcomeMessage += "The round ends in a draw!\nThe score was tied between: ";
                for (Player player : outcome.getDraws()) {
                    String name;
                    if (player.isAIPlayer()) {
                        name = player.getName();
                    } else {
                        name = "You";
                    }
                    if (player == outcome.getDraws().get(outcome.getDraws().size() - 1)) {
                        outcomeMessage += " and " + name;
                    } else if (player == outcome.getDraws().get(outcome.getDraws().size() - 2)) {
                        outcomeMessage += name;
                    } else {
                        outcomeMessage += name + ", ";
                    }
                }
                break;
        }
        typePrint(SLOW_SPEED, outcomeMessage);
    }

    public void showNextRoundMessage() {
        String message = "\nPress ENTER to continue";
        typePrint(SLOW_SPEED, message);
    }

    public void showRemovedPlayers(List<Player> removedPlayers) {
        String removedPlayersString = "\n";
        if (removedPlayers.size() == 1) {
            if (removedPlayers.get(0).isAIPlayer()) {
                removedPlayersString += removedPlayers.get(0).getName() + " has been removed from the game";
            } else {
                removedPlayersString += "Oh no - you haven't got any cards left! You have been removed from the game";
            }
        } else {
            Player lastPlayerInList = removedPlayers.get(removedPlayers.size() - 1);
            Player secondLastPlayerInList = removedPlayers.get(removedPlayers.size() - 2);
            for (Player player : removedPlayers) {
                String name;
                if (!player.isAIPlayer()) {
                    name = "You";
                    typePrint(SLOW_SPEED, "\nOh no - you haven't got any cards left!");
                    removedPlayersString = "";//removes new line character
                } else {
                    name = player.getName();
                }
                if (player == lastPlayerInList) {
                    removedPlayersString += " and " + name + " have been removed from the game";
                } else if (player == secondLastPlayerInList) {
                    removedPlayersString += name;
                } else {
                    removedPlayersString += name + ", ";
                }
            }
        }
        typePrint(SLOW_SPEED, removedPlayersString);
    }

    public void showAutomaticCompletion() {
        String message = "\n\nThe remaining players are completing the game...";
        pausePrinting(SHORT_PAUSE);
        typePrint(SLOW_SPEED, message);
        pausePrinting(MODERATE_PAUSE);
    }

    public void showGameResult(Player winner, Map<Player, Integer> roundWinsMap) {
        String message = "\n\n";
        if (winner.isAIPlayer()) {
            message += winner.getName() + " has won the game!";
        } else {
            message += "Congratulations - you won the game!";
        }
        Logger.getInstance().logToFileIfEnabled(message);
        typePrint(TURTLE_SPEED, message);
        showGameStatistics(roundWinsMap);
        System.out.println("\n\n\n");
        pausePrinting(LONG_PAUSE);
    }

    private void showGameStatistics(Map<Player, Integer> roundWinsMap){
        typePrint(MODERATE_SPEED, "\nThe number of round wins per player were:");
        for(Player player: roundWinsMap.keySet()){
            String playerName = player.getName();
            Integer wins = roundWinsMap.get(player);
            String message = String.format("%-15s%3d", playerName+":", wins);
            typePrint(MODERATE_SPEED, message);
        }
    }

    private void pausePrinting(int pauseTime) {
        if (!IS_TEST_MODE) {
            try {
                Thread.sleep(pauseTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void typePrint(int characterPause, String printLine) {

        for (String s : printLine.split("")) {
            System.out.print(s);
            if (!IS_TEST_MODE) pausePrinting(characterPause);
        }
        System.out.print("\n");

    }

}
