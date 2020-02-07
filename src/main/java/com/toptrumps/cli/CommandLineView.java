package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.player.Player;

import java.util.List;
import java.lang.Thread;
import java.lang.InterruptedException;

public class CommandLineView {

    public CommandLineView() {

    }

    public void showRoundStart(Player activePlayer, Player humanPlayer, int roundNumber){
        pausePrinting(1000);
        showRoundNumber(roundNumber);
        showActivePlayer(activePlayer, humanPlayer);
        if(!humanPlayer.isAIPlayer()){
            pausePrinting(500);
            showNumberOfHumanCards(humanPlayer);
            showCard(humanPlayer.getTopCard());
        }
    }

    private void showRoundNumber(int roundNumber){
        System.out.println("\n\n--------------------");
        System.out.println("    Round " + roundNumber);
        System.out.println("--------------------");
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

        typePrint(20, "\nYour card is:");
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
