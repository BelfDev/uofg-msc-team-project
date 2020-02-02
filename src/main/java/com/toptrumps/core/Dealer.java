package com.toptrumps.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dealer {
    ArrayList<Card> deck;
    ArrayList<Player> players;

    public Dealer(ArrayList<Player> players){
        DeckParser d = new DeckParser();
        deck = d.getCards();
        this.players = players;
        dealCards();
    }

    private void dealCards(){
        shuffleCards();
		int currentCardIndex = 0;
		
		//loop through while there is cards left in the deck
		while(currentCardIndex < deck.size()) {
			//loop through the players and add a card to their deck
			for(int i=0; i<players.size(); i++) {
				//add additional if statement for inner loop as cards may not divide exactly between players
				if(currentCardIndex < deck.size()) {
					players.get(i).addCardToHand(deck.get(currentCardIndex));
					currentCardIndex++;
				}
			}
		}
    }

    private void shuffleCards(){
        Collections.shuffle(deck, new Random());
        }

}
