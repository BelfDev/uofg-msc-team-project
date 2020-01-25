package core;


import java.io.*;
import java.util.ArrayList;


public class Deck {

    private ArrayList<Card> cards;

    public Deck() {

    }

    public void getCardHeader() {
        ArrayList<Card> cards = new ArrayList<Card>();

        File cardsFile = new File("StarCitizenDeck.txt");

        try {
            FileReader fr = new FileReader(cardsFile);
            BufferedReader br = new BufferedReader(fr);

            try {
                String header = br.readLine();
                String categories[] = header.split(" ");
                for (String x : categories) {
                    System.out.println(x);
                }
                while (br.readLine() != null) {
                    String line = br.readLine();
                    String currentCards[] = line.split(" ");
                    Card card = new Card(currentCards[0]);

                    for (int i = 1; i < 5; i++) {
                        card.addValueToCat(categories[i], Integer.parseInt(currentCards[i]));
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

        for(Card c : cards){
            System.out.println(c.getDescription());
        }



    }


}
