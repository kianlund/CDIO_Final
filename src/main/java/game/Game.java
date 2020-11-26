package game;

import com.company.Main;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;

public class Game {
    private final int totalNumPlayers;
    private Player[] playerList;
    private DiceCup cup;
    private GameBoard board;
    private GUI gui;
    private int startBalance ;
    private final int startLocation = 0;
    private final int numberOfTiles = 24;
    private final int prisonLocation = 6;
    private final int goToJailLocation = 18;
    private final int maxNumberOfPlayers = 4;
    private final int chancePerSide = 1;
    private final Color[] colors= {Color.RED, Color.BLUE, Color.GREEN,
                            Color.YELLOW,/*, Color.CYAN, Color.PINK};*/};
    private ChanceCards chanceCards;
    private GUI_Field[] fields;
    private Text textStrings;
    private static TileHandler tileHandler;

    private Language langStrings = new Language("engGameStrings.txt");

    public Game(Boolean test){
        initGUIFields();
        gui = new GUI(fields);
        board = new GameBoard(gui.getFields().length, gui.getFields());
        chanceCards = new ChanceCards(this);
        textStrings = new Text(this);
        tileHandler = new TileHandler(prisonLocation);
        totalNumPlayers = 4;
        decideStartBalance(totalNumPlayers);
        String[] playerNames = {"Thor", "Tobias", "Kian", "Sume"};
        playerList = new Player[totalNumPlayers];
        for(int i = 0; i < totalNumPlayers; i++){
            GUI_Car car = new GUI_Car();
            car.setPrimaryColor(colors[i]);
            Player p = new Player(playerNames[i], startBalance, startLocation, car, tileHandler);
            gui.addPlayer(p);
            playerList[i] = p;
            gui.getFields()[p.getLocation()].setCar(p, true);
        }
        cup = new DiceCup(1);
        playGame();
    }
    public Game() {
        initGUIFields();
        gui = new GUI(fields);
        board = new GameBoard(gui.getFields().length, gui.getFields());
        chanceCards = new ChanceCards(this);
        textStrings = new Text(this);
        tileHandler = new TileHandler(prisonLocation);
        totalNumPlayers = gui.getUserInteger(langStrings.getLine(0)+". 1-"+maxNumberOfPlayers,1,maxNumberOfPlayers);

        if (totalNumPlayers>4){
            decideStartBalance(maxNumberOfPlayers);
            addPlayers(maxNumberOfPlayers);
        }
        else if(totalNumPlayers<1){
            decideStartBalance(1);
            addPlayers(1);
        }else {
                decideStartBalance(totalNumPlayers);
                addPlayers(totalNumPlayers);
        }



        cup = new DiceCup(1);
        playGame();
    }

    private void initGUIFields() {
        fields = new GUI_Field[numberOfTiles];
        int sideLength = numberOfTiles/4;
        final int chanceFreq = sideLength/(chancePerSide*2);
        int i =0;
        fields[i] = new GUI_Start();
        for( i = 1; i<numberOfTiles; i++){
            if(i%chanceFreq == 0){
                if(i%2 == 0 ){ //Only works with this size
                    switch (i){
                        case(0):
                            fields[i] =  new GUI_Start();
                            break;
                        case(prisonLocation):
                            fields[i] = new GUI_Jail();
                        break;
                        case(12):
                            fields[i] = new GUI_Refuge();
                        break;
                        case(goToJailLocation):
                            fields[i] = new GUI_Jail();
                        break;
                    }
                }
                else {
                    fields[i] = new GUI_Chance();
                }
            }
            else{
                fields[i]= new GUI_Street();
            }
        }
    }

    public void playGame() {
        int winnerID = -1;
        while (winnerID == -1) //Game loop till winner is found
        {
            for (int i = 0; i < playerList.length; i++) {   //A full round
                Player player = playerList[i];
                if (player.getPrison()){
                    player.startFromPrison(this);
                }
                gui.getUserButtonPressed(playerList[i].getName()+langStrings.getLine(2),langStrings.getLine(4));
                cup.rollDice();
                int a = cup.getDiceinCup().get(0).getValue();
//                int a = 1;
                gui.setDie(a);
                player.moveLocation(a, this);
                gui.getFields()[player.getLocation()].setCar(player, true);
                if (player.getBankrupt()) {
                    gui.showMessage(player.getName() + langStrings.getLine(5));
                    resolveGame(player,winnerID);
                    winnerID=100; //Why wont the loop stop without this?!?!?!
                    break;
                }
            }
        }
    }

    private void resolveGame(Player p, int winner) {
        int highestBalance;
        int numWinners;
        int[] totalValue = new int[playerList.length];

        highestBalance = 0;
        numWinners = 0;
        for (int j = 0; j < playerList.length; j++) { highestBalance = Math.max(highestBalance,playerList[j].getBalance()); }
        for (int j = 0; j < playerList.length; j++) {
            if (playerList[j].getBalance() == highestBalance){ numWinners++; winner = j; }
        }
        if (numWinners == 1) {
            gui.showMessage(playerList[winner].getName()+langStrings.getLine(3));
        } else {
            for (int i = 0; i < playerList.length; i++) {
                totalValue[i] = playerList[i].getBalance();
                for (int j = 0; j < numberOfTiles; j++) {
                    if (board.getTiles()[j].getOwner() == playerList[i]) {
                        totalValue[i] += board.getTiles()[i].getRent();
                    }
                }
            }
            highestBalance = 0;
            for (int j = 0; j < playerList.length; j++) { highestBalance = Math.max(highestBalance,totalValue[j]); }
            for (int j = 0; j < playerList.length; j++) {
                if (totalValue[j] == highestBalance){
                    gui.showMessage(langStrings.getLine(6));
                    gui.showMessage(playerList[j].getName()+langStrings.getLine(3));
                    break;
                }
            }
        }
    }

    private void addPlayers(int a) {
        playerList = new Player[a];
        for (int i = 0; i < a; i++) {
            GUI_Car car = new GUI_Car();
            car.setPrimaryColor(colors[i]);

            Player p = new Player(gui.getUserString(langStrings.getLine(1)+" "+(i+1)+"."), startBalance, startLocation, car, tileHandler);
            gui.addPlayer(p);
            playerList[i] = p;
            gui.getFields()[p.getLocation()].setCar(p, true);
        }
    }

    public Player[] getPlayerList() { return playerList; }

    public GUI getGui() {
        return gui;
    }

    public int getTotalNumPlayers() {
        return totalNumPlayers;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void drawChance(Player player){
        chanceCards.DrawCard(player);
    }

    public int getNumberOfTiles(){return  numberOfTiles;}

    public Text getTextStrings(){ return textStrings;}

    public void decideStartBalance(int numberOfPlayers){
        switch (numberOfPlayers){
            case(1):
                startBalance = 22;
            break;
            case(2):
                startBalance = 20;
            break;
            case(3):
                startBalance = 18;
            break;
            case(4):
                startBalance = 16;
            break;
            default:
                startBalance = 12;
            break;
        }
    }
}
