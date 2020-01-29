package com.toptrumps.core;


import java.io.*;
import java.util.ArrayList;


public class DeckParser {

    private ArrayList<Card> cards;
    private final String FILE_PATH = "src/main/resources/assets/StarCitizenDeck.txt";


    /**
     * Builds the deck for the game.
     *
     */

    public DeckParser() {
        buildDeck();
    }

    /**
     * Gets the cards from the specified card location,
     * and sets the "deck" in an ArrayList called cards.
     * @return void
     */

    private void buildDeck() {
        cards = new ArrayList<>();


        File cardsFile = new File(FILE_PATH);

        try {
            FileReader fr = new FileReader(cardsFile);
            BufferedReader br = new BufferedReader(fr);

            try {
                String line = br.readLine();
                String[] categories = line.split(" ");

                while ((line = br.readLine()) != null) {
                    String[] currentCards = line.split(" ");
                    Card card = new Card(currentCards[0]);

                    for (int i = 1; i < categories.length; i++) {
                        card.addValueToCat(categories[i], Integer.parseInt(currentCards[i]));
                        card.findBestCats();
                    }

                    cards.add(card);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            System.err.println("The file could not be found!");
            e.printStackTrace();
        }



        for (Card c : cards) {
            System.out.println(c.toString());
        }


    }

    /**
     * Returns an ArrayList of all of the cards read from file.
     * @return ArrayList containing all of the cards.
     */

    protected ArrayList<Card> getCards() {
        return cards;
    }

}
