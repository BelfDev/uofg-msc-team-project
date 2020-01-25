package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Deck {

    private ArrayList<Card> cards;

    public Deck(){
        ArrayList<Card> cards = new ArrayList<Card>();

        File cardsFile = new File("StarCitizenDeck.txt");

        try (Scanner scanner = new Scanner(cardsFile)) {
        }catch (FileNotFoundException e){
            System.err.println("The file could not be found!");
            e.printStackTrace();
        }



    }



}
