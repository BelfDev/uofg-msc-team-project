package com.toptrumps.core;


import java.io.*;
import java.util.ArrayList;


// TODO: Remove this class
public class Deck {

    private ArrayList<Card> cards;

    public Deck() {
        buildDeck(); // calls the build deck method
    }

    private void buildDeck() {
        cards = new ArrayList<>(); // creates a new array list

        // creates a new File called cardsFile at the location of the file to be read in.
        File cardsFile = new File("src/main/resources/assets/StarCitizenDeck.txt");

        try {
            FileReader fr = new FileReader(cardsFile);
            BufferedReader br = new BufferedReader(fr);

            try {
                String header = br.readLine(); // reads the first line of the file (contains the headers).
                String[] categories = header.split(" "); // splits the file based on the spaces into an array called categories.

                while (br.readLine() != null) { // loops until an empty line is encountered.
                    String line = br.readLine(); // takes a single line as a String.
                    String[] currentCards = line.split(" "); // again, splits them based on the presence of spaces.
                    Card card = new Card(currentCards[0]); // creates a new reference to a card object with the description which is held at index 0.

                    for (int i = 1; i < 5; i++) { // loops over the remaining elements in the array.
                        card.addValueToCat(categories[i], Integer.parseInt(currentCards[i])); // adds a card to the HashMap in the card object
                        card.findBestCats(); // finds the card's strongest categorey. If there is more than one it add them to an ArrayList
                    }

                    cards.add(card); // adds a card to the array.

                }

            } catch (IOException e) { // exception handling
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) { // exception handling
            System.err.println("The file could not be found!");
            e.printStackTrace();
        }


        // Loops through the array of cards! !!! Must be deleted before submission!
        for (Card c : cards) {
            System.out.println(c.toString());
            System.out.println(c.getBestCat());
        }


    }

    protected ArrayList<Card> getCards() {
        return cards;
    }

}
