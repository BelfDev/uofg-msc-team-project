package core;

public class Card {

    private  String description;
    private int height;
    private  int weight;
    private int length;
    private int ferocity;
    private int intelligence;


    public Card(String description, int height, int weight, int length, int ferocity, int intelligence){

        this.description = description;
        this.height = height;
        this.weight = weight;
        this.length = length;
        this.ferocity = ferocity;
        this.intelligence = intelligence;
    }

    public String getDescription() {
        return description;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }

    public int getFerocity() {
        return ferocity;
    }

    public int getIntelligence() {
        return intelligence;
    }
}
