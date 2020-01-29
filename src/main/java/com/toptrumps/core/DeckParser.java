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

                String line = br.readLine(); // reads the first line of the file (contains the headers).
                String[] categories = line.split(" "); // splits the file based on the spaces into an array called categories.

                while ((line = br.readLine()) != null) { // loops until an empty line is encountered.
                    String[] currentCards = line.split(" "); // again, splits them based on the presence of spaces.

                    // TODO: Fix the Card class to be compatible with this implementation
                    // Card card = new Card(currentCards[0]); // creates a new reference to a card object with the description which is held at index 0.

                    // TODO: Create card.addValueToCategory() inside the card object. Or even better: absorb these info and add to Card's constructor.
                    /*
                    for (int i = 1; i < categories.length; i++) { // loops over the remaining elements in the array.
                        card.addValueToCat(categories[i], Integer.parseInt(currentCards[i])); // loops over the remaining elements in the array.
                        card.findBestCats();
                    }
                    */

                    // cards.add(card);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            System.err.println("The file could not be found!");
            e.printStackTrace();
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
