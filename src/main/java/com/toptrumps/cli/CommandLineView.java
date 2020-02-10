package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.player.Player;
import com.toptrumps.core.engine.RoundOutcome;
import com.toptrumps.core.utils.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.stream.Collectors;
import java.lang.Thread;
import java.lang.InterruptedException;


import static java.util.stream.Collectors.toCollection;

public class CommandLineView {

    private static final String WELCOME_BANNER_RESOURCE = "assets/banners/welcome.txt";



    public void showWelcomeMessage() {
        printWelcomeBanner();
        typePrint(20, "\nTo start a new game, press f \nTo see game statistics, press s");
        Logger.getInstance().logToFileIfEnabled("Game started");
    }

    private void printWelcomeBanner() {
        InputStream inputStream = ResourceLoader.getResource(WELCOME_BANNER_RESOURCE);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String banner = br.lines().collect(Collectors.joining("\n"));
        typePrint(2, banner);
    }

    public int requestNumberOfOpponents(int MIN_OPPONENTS, int MAX_OPPONENTS, Scanner scanner) {
        try {
            typePrint(20, "How many players do you want to play against?");
            typePrint(20, "Please select " + MIN_OPPONENTS + " - " + MAX_OPPONENTS);

            int numberOfOpponents = scanner.nextInt();
            while (numberOfOpponents < MIN_OPPONENTS || numberOfOpponents > MAX_OPPONENTS) {
                typePrint(10, "Invalid number of players selected. Please select " + MIN_OPPONENTS + "-" + MAX_OPPONENTS + ".");
                numberOfOpponents = scanner.nextInt();
            }
            return numberOfOpponents;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            typePrint(10, "You didn't enter a number!");
            return requestNumberOfOpponents(MIN_OPPONENTS, MAX_OPPONENTS, scanner);
        }
    }

    public void showRoundStart(Player activePlayer, Player humanPlayer, int roundNumber, int communalPileSize){
        pausePrinting(1000);
        showRoundNumber(roundNumber);
        showActivePlayer(activePlayer, humanPlayer);
        showCommunalPileSize(communalPileSize);
        pausePrinting(500);
        showNumberOfHumanCards(humanPlayer);
        typePrint(20, "\nYour card is:");
        showCard(humanPlayer.getTopCard());
    }

    private void showRoundNumber(int roundNumber){
        typePrint(10, "\n\n--------------------");
        typePrint(10, "    Round " + roundNumber);
        typePrint(10, "--------------------");
    }

    private void showActivePlayer(Player activePlayer, Player humanPlayer){
        String message = "";
        if(activePlayer == humanPlayer){
            message += "You are the active player";
        }else {
            message += activePlayer.getName() + " is the active player";
        }
        typePrint(20, message);
    }

    private void showCommunalPileSize(int communalPileSize){
        String message = String.format("There are %d cards in the communal pile", communalPileSize);
        typePrint(20, message);
    }

    private void showNumberOfHumanCards(Player humanPlayer){
        if(!humanPlayer.isAIPlayer()){
            int numberOfHumanCards = humanPlayer.getDeckCount();
            String cardOrCards = numberOfHumanCards==1 ? "card" : "cards";
            String message = String.format("You have %d %s in your hand", numberOfHumanCards, cardOrCards);
            typePrint(20, message);
        }
    }

    private void showCard(Card card){
        List<Attribute> attributes = card.getAttributes();
        String name = card.getName();
        pausePrinting(1000);

        typePrint(5, "\t--------------------");
        typePrint(5, String.format("\t|%18s|", name));
        typePrint(5, String.format("\t|%18s|", ""));
        typePrint(5, String.format("\t|%18s|", ""));

        for (int i = 0; i < attributes.size(); i++) {
            String attributeName = attributes.get(i).getName();
            int attributeValue = attributes.get(i).getValue();
            String message = String.format("\t|%d: %-13s%2d|", i + 1, attributeName, attributeValue);
            typePrint(5, message);
        }

        typePrint(5, "\t--------------------");
    }

    private void showCards(List<Card> cards){
        List<Attribute> attributes = cards.get(0).getAttributes();
        pausePrinting(1000);

        int linesRequired = 5 + attributes.size();
        ArrayList<String> printLines = new ArrayList<String>();
        for(int i=0; i<linesRequired; i++){printLines.add("");}

        for(Card card: cards){
            attributes = card.getAttributes();
            String name = card.getName();
            printLines.set(0, printLines.get(0) + "\t--------------------");
            printLines.set(1, printLines.get(1) + String.format("\t|%18s|", name));
            printLines.set(2, printLines.get(2) + String.format("\t|%18s|", ""));
            printLines.set(3, printLines.get(3) + String.format("\t|%18s|", ""));
            for (int i = 0; i < attributes.size(); i++) {
                String attributeName = attributes.get(i).getName();
                int attributeValue = attributes.get(i).getValue();
                printLines.set(i+4, printLines.get(i+4) + String.format("\t|%d: %-13s%2d|", i + 1, attributeName, attributeValue));
            }
            printLines.set(printLines.size()-1, printLines.get(printLines.size()-1) + "\t--------------------");
        }

        for(String line: printLines){typePrint(2, line);}
    }

    public void requestSelection(int numberOfAttributes){
        typePrint(20, "Which attribute would you like to choose?");
        typePrint(20, "Please select 1 - " + numberOfAttributes);
    }

    public void showInvalidSelection(int numberOfAttributes){
        typePrint(10, "Invalid attribute selected. Please select 1 -" + numberOfAttributes + ".");
    }

    public void showNoNumberSelected(){
        typePrint(10, "You didn't enter a number!");
    }

    public void showSelectedAttribute(String playerName, String selectedAttribute){
        String message = String.format("%s selected the attribute %s", playerName, selectedAttribute);
        typePrint(20, message);
    }

    public void showRoundResult(RoundOutcome outcome, List<Card> winningCards) {
        pausePrinting(1000);
        RoundOutcome.Result result = outcome.getResult();
        String outcomeMessage = "\n";
        switch (result) {
            case VICTORY:
                typePrint(20, "\nThe winning card is...");
                showCard(winningCards.get(0));
                if(outcome.getWinner().isAIPlayer()){
                    outcomeMessage += outcome.getWinner().getName() + " is the winner of the round!";
                }else{
                    outcomeMessage += "You win the round!";
                }
                
                break;
            case DRAW:
                typePrint(20, "\nThe winning cards are...");
                showCards(winningCards);
                outcomeMessage += "The round ends in a draw!\nThe score was tied between: ";
                for (Player player : outcome.getDraws()) {
                    String name;
                    if(player.isAIPlayer()){
                        name = player.getName();
                    }else{
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
        typePrint(20, outcomeMessage);
    }

    public void showRemovedPlayers(List<Player> removedPlayers) {
        String removedPlayersString = "\n";
        if (removedPlayers.size() == 1) {
            if(removedPlayers.get(0).isAIPlayer()){
                removedPlayersString += removedPlayers.get(0).getName() + " has been removed from the game";
            }else{
                removedPlayersString += "Oh no - you haven't got any cards left! You have been removed from the game";
            }
        } else {
            Player lastPlayerInList = removedPlayers.get(removedPlayers.size() - 1);
            Player secondLastPlayerInList = removedPlayers.get(removedPlayers.size() - 2);
            for (Player player : removedPlayers) {
                String name;
                if(!player.isAIPlayer()){
                    name = "You";
                    typePrint(20, "\nOh no - you haven't got any cards left!");
                    removedPlayersString = "";//removes new line character
                }else{
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
        typePrint(20, removedPlayersString);
    }

    public void showGameResult(Player winner){
        pausePrinting(1000);
        String message = "\n\n";
        if(winner.isAIPlayer()){
            message += winner.getName() + " has won the game!";
        }else {
            message += "Congratulations - you won the game!";
        }
        Logger.getInstance().logToFileIfEnabled(message);
        typePrint(40, message);
        System.out.println("\n\n\n");
        pausePrinting(1500);
    }

    private void pausePrinting(int pauseTime){
        try {
            Thread.sleep(pauseTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void typePrint(int characterPause, String printLine){
        for(String s: printLine.split("")){
            System.out.print(s);
            pausePrinting(characterPause);
        }
        System.out.print("\n");
    }

}
