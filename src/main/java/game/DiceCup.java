package game;

public class DiceCup {
    private final int numberOfDice;
    private Dice[] diceIncup;
    private int sum;


    public DiceCup(int numOfDice, int[]diceArray, int[] sidesOfDice) {
        numberOfDice = numOfDice;
        diceIncup = new Dice[numOfDice];
        for (int i = 0; i > numOfDice; i++) {
            Dice d;
            if (sidesOfDice.length == i) { 
                d = new Dice(sidesOfDice[i]);
                diceIncup[i]=d;
            } else {
                d = new Dice();
                diceIncup[i]=d;
            }
        }
    }
    public DiceCup(int numOfDice){
        numberOfDice = numOfDice;
        diceIncup = new Dice[numOfDice];
        for (int i = 0; i<numOfDice; i++){
            Dice d;
            d = new Dice();
            diceIncup[i]=d;
        }
    }
    public void rollDice(){
        sum =0 ;
        for (int i = 0; i < diceIncup.length; i++) {
            sum += diceIncup[i].roll();
        }
    }
    public int getNumberOfDice(){
        return numberOfDice;
    }

    public Dice[] getDiceinCup(){
        return diceIncup;
    }
    public int getSum(){
        return sum;
    }

}

