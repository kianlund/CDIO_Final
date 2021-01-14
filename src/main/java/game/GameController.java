package game;

import gui_fields.*;
import gui_main.GUI;

import java.awt.*;

public class GameController {
    private final int totalNumPlayers;
    private Player[] playerList;
    private DiceCup cup;
    private GameBoard board;
    private GUI gui;
    private final int startBalance = 30000;
    private final int startLocation = 0;
    private final int numberOfTiles = 40;
    private final int prisonLocation = 10;
    private final int minNumberOfPlayers = 3;
    private final int maxNumberOfPlayers = 6;
    private final Color[] colors= {Color.RED, Color.BLUE, Color.GREEN,
                            Color.YELLOW, Color.CYAN, Color.PINK};
    private ChanceCards chanceCards;
    private GUI_Field[] fields;
    private Text textStrings;
    private static TileHandler tileHandler;

    private Language langStrings = new Language("dkGameString.txt");

    public GameController(Boolean test){
        initGUIFields();
        gui = new GUI(fields);
        board = new GameBoard(gui.getFields().length, gui.getFields());
        chanceCards = new ChanceCards(this);
        textStrings = new Text(this);
        tileHandler = new TileHandler(prisonLocation);
        totalNumPlayers = 4;
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
    public GameController() {
        initGUIFields();
        gui = new GUI(fields);
        board = new GameBoard(gui.getFields().length, gui.getFields());
        chanceCards = new ChanceCards(this);
        textStrings = new Text(this);
        tileHandler = new TileHandler(prisonLocation);
        totalNumPlayers = gui.getUserInteger(langStrings.getLine(0)+". "+minNumberOfPlayers+"-"+maxNumberOfPlayers,minNumberOfPlayers,maxNumberOfPlayers);

        if (totalNumPlayers>maxNumberOfPlayers){
            addPlayers(maxNumberOfPlayers);
        }
        else if(totalNumPlayers<minNumberOfPlayers){
            addPlayers(minNumberOfPlayers);
        }else {
                addPlayers(totalNumPlayers);
        }

        cup = new DiceCup(2);
        playGame();
    }

    private void initGUIFields() {
        fields = new GUI_Field[numberOfTiles];
        //int sideLength = numberOfTiles/4;
        //final int chanceFreq = sideLength/(chancePerSide*2);
        int i =0;
        fields[i] = new GUI_Start();
        for( i = 1; i<numberOfTiles; i++) {
            if (i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36) {
                fields[i] = new GUI_Chance();
            } else if (i == 10 || i == 30) {
                fields[i] = new GUI_Jail();
            } else if (i == 20) {
                fields[i] = new GUI_Refuge();
            } else if (i == 5 || i == 15 || i == 25 || i == 35) {
                fields[i] = new GUI_Shipping();
            } else if (i == 12 || i == 28) {
                fields[i] = new GUI_Brewery();
            } else if (i == 4 || i == 38) {
                fields[i] = new GUI_Tax();
            } else {
                fields[i] = new GUI_Street();
            }
        }
    }

    public void playGame() {
        int winnerID = -1;
        while (winnerID == -1) //Game loop till winner is found
        {
            for (int i = 0; i < playerList.length; i++) {   //A full round
                Player player = playerList[i];
                while (player.getBankrupt()) { // Skip bankrupt players
                    tileHandler.removeOneCar(this,player);
                    i++;
                    if (i > playerList.length) {i = 0;}
                    player = playerList[i];
                }
                if (player.getPrison()){
                    player.startFromPrison(this);
                }
                gui.getUserButtonPressed(playerList[i].getName()+langStrings.getLine(2),langStrings.getLine(4));
                cup.rollDice();
                int a = cup.getDiceinCup()[0].getValue();
                int b = cup.getDiceinCup()[1].getValue();
//                int a = 1;
                gui.setDice(a,b);
                player.moveLocation(a+b, this);
                gui.getFields()[player.getLocation()].setCar(player, true);
                if (player.getBankrupt()) {
                    gui.showMessage(player.getName() + langStrings.getLine(5));
                    resolveGame(player,winnerID);
                    break;
                }
            }
        }
    }

    private void resolveGame(Player p, int winner) {
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

    public DiceCup getCup() {return cup; }

    public void drawChance(Player player){
        chanceCards.DrawCard(player);
    }

    public int getNumberOfTiles(){return  numberOfTiles;}

    public Text getTextStrings(){ return textStrings;}

}
