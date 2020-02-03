package com.toptrumps.core.card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


class DeckParser {

    private final static String ITEM_SEPARATOR = " ";

    private DeckParser() {
    }

    /**
     * Parses the content from a local text resource
     * into a collection of cards (deck)
     *
     * @return <code>ArrayList<Card></code> parsed deck
     */
    public static ArrayList<Card> parseDeck(String resource) {
        ArrayList<Card> deck = new ArrayList<>();

        try {
            InputStream inputStream = getResource(resource);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            // Reads the first line of the resource (contains the headers)
            String line = br.readLine();
            // Splits the resource based on the spaces into an array of headers
            String[] headers = line.split(ITEM_SEPARATOR);

            // Loops until an empty line is encountered.
            while ((line = br.readLine()) != null) {
                // Splits the card values based on the presence of spaces
                String[] currentCardValues = line.split(ITEM_SEPARATOR);
                Card currentCard = parseCard(headers, currentCardValues);
                // Adds the parsed card to the deck collection
                deck.add(currentCard);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return deck;
    }

    private static Card parseCard(String[] headers, String[] cardValues) {
        ArrayList<Attribute> attributes = new ArrayList<>();
        // Traverses the card values based on the quantity of attributes
        // Index starts at 1 in order to ignore the first header value
        for (int i = 1; i < headers.length; i++) {
            // Extracts current attribute name and value
            String attributeName = headers[i];
            int attributeValue = Integer.parseInt(cardValues[i]);
            // Create new attribute with the parsed values
            Attribute currentAttribute = new Attribute(attributeName, attributeValue);
            attributes.add(currentAttribute);
        }
        return new Card(cardValues[0], attributes);
    }

    private static InputStream getResource(String fileName) {
        ClassLoader classLoader = DeckParser.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found");
        } else {
            return classLoader.getResourceAsStream(fileName);
        }
    }

}
