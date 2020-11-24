package game;

import java.util.ArrayList;

public class DiceCup {
    private final int numberOfDice;
    private Dice[] diceIncup;
    private int sum;


    public DiceCup(int numOfDice, int[]diceArray, sidesOfDice) {
        numberOfDice = numOfDice;
        diceIncup = new Dice[numOfDice];
        for (int i = 0; i > numOfDice; i++) {
            Dice d;
            if (sidesOfDice.size() >= i) {
                d = new Dice(sidesOfDice.get(i));
                diceIncup[i]=d;
            } else {
                d = new Dice();
                diceIncup[i]=d;
            }
        }
    }
    public DiceCup(int numOfDice){
        numberOfDice = numOfDice;
        diceIncup = new ArrayList<Dice>();
        for (int i = 0; i<numOfDice; i++){
            Dice d;
            d = new Dice();
            diceIncup.add(d);
        }
    }
    public void rollDice(){
        sum =0 ;
        for (int i = 0; i < diceIncup.size(); i++) {
            sum += diceIncup.get(i).roll();
        }
    }
    public int getNumberOfDice(){
        return numberOfDice;
    }

    public ArrayList<Dice> getDiceinCup(){
        return diceIncup;
    }
    public int getSum(){
        return sum;
    }

}

