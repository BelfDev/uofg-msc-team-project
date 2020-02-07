package com.toptrumps.cli;

import com.toptrumps.core.card.Attribute;
import com.toptrumps.core.card.Card;
import com.toptrumps.core.player.Player;

import java.util.List;

public class CommandLineView {

    public CommandLineView() {

    }

    public void showRoundStart(Player activePlayer, Player humanPlayer, int roundNumber){
        showRoundNumber(roundNumber);
        showActivePlayer(activePlayer, humanPlayer);
        showCard(humanPlayer.getTopCard());
    }

    private void showRoundNumber(int roundNumber){
        System.out.println("--------------------");
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
        System.out.println(message);
    }

    private void showCard(Card card){
        List<Attribute> attributes = card.getAttributes();
        String name = card.getName();

        System.out.println("Your card is:");
        System.out.println("\t--------------------");
        System.out.println(String.format("\t|%18s|", name));
        System.out.println(String.format("\t|%18s|", ""));
        System.out.println(String.format("\t|%18s|", ""));

        for (int i = 0; i < attributes.size(); i++) {
            String attributeName = attributes.get(i).getName();
            int attributeValue = attributes.get(i).getValue();
            String message = String.format("\t|%d: %-13s%2d|", i + 1, attributeName, attributeValue);
            System.out.println(message);
        }

        System.out.println("\t--------------------");
    }

}
