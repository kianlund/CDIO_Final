package game;

import gui_fields.*;

import java.awt.*;


/**
 * Should be used for the game board.
 * Tiles are the individual fields
 */
public class GameBoard {
    private Tile[] tiles;
    private final int[] tilePrice ={
            4000,1200,0,1200,
            0,4000,2000,0,
            2000,2400,0,2800,
            3000,2800,3200,4000,
            3600,0,3600,4000,
            0,4400,0,4400,
            4800,4000,5200,5200,
            3000,5600,0,6000,
            6000,0,6400,4000,
            0,7000,0,8000};

    private final int[][] houseRent ={
            {50,250,750,2250,4000,6000},
            {50,250,750,2250,4000,6000},
            {100,600,1800,5400,8000,11000},
            {100,600,1800,5400,8000,11000},
            {150,600,2000,6000,9000,12000},
            {200,1000,3000,9000,12500,15000},
            {200,1000,3000,9000,12500,15000},
            {250,1250,3750,10000,14000,18000},
            {300,1400,4000,11000,15000,19000},
            {300,1400,4000,11000,15000,19000},
            {350,1600,4400,12000,16000,20000},
            {350,1800,5000,14000,17500,21000},
            {350,1800,5000,14000,17500,21000},
            {400,2000,6000,15000,18500,22000},
            {450,2200,6600,16000,19500,23000},
            {450,2200,6600,16000,19500,23000},
            {500,2400,7200,17000,20500,24000},
            {550,2600,7800,18000,22000,25000},
            {550,2600,7800,18000,22000,25000},
            {600,3000,9000,20000,24000,28000},
            {700,3500,10000,22000,26000,30000},
            {1000,4000,12000,28000,34000,40000}
    };
    private final Color[] colorArray = {Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GRAY, Color.RED, Color.WHITE, Color.PINK, Color.CYAN};
    private int colorCounter;
    private int tileCounter;
    private int rentCounter;

    private Language tileStrings = new Language("dkTileStrings.txt");

    public GameBoard (int numOfTiles, GUI_Field[] gui_fields) {
        tiles = new Tile[numOfTiles];
        colorCounter = 0;
        rentCounter = 0;
        tileCounter = 1;
        for (int i = 0; i < numOfTiles; i++) {
            tiles[i] = new Tile(tilePrice[i],gui_fields[i], i);
            if (gui_fields[i] instanceof GUI_Street || gui_fields[i] instanceof GUI_Brewery || gui_fields[i] instanceof GUI_Shipping) {
                tiles[i].getGui_field().setTitle(tileStrings.getLine(i));
                tiles[i].getGui_field().setSubText("Pris: "+ tilePrice[i]+"kr.");
                tiles[i].getGui_field().setDescription(tileStrings.getLine(i));
                if (gui_fields[i] instanceof GUI_Street) {
                    tiles[i].setTileColor(colorArray[colorCounter]);
                    tiles[i].getGui_field().setBackGroundColor(colorArray[colorCounter]);
                    tiles[i].setRent(houseRent[rentCounter]);
                    rentCounter++;
                    tileCounter++;
                    if (tileCounter == 3) {tileCounter = 0; colorCounter++;}
                }
            } else if (gui_fields[i] instanceof GUI_Start) {
                tiles[i].getGui_field().setTitle(tileStrings.getLine(i));
                tiles[i].getGui_field().setSubText("+"+ tilePrice[i]+"kr.");
                tiles[i].getGui_field().setDescription(tileStrings.getLine(i));
            } else if (gui_fields[i] instanceof GUI_Tax) {
                tiles[i].getGui_field().setTitle(tileStrings.getLine(i));
                tiles[i].getGui_field().setSubText("");
                tiles[i].getGui_field().setDescription(tileStrings.getLine(i));
            } else {
                tiles[i].getGui_field().setSubText(tileStrings.getLine(i));
                tiles[i].getGui_field().setDescription(tileStrings.getLine(i));
            }

        }
    }

    public Tile getTile(int num){
        return tiles[num];
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Tile[] getTilesByColor(Color c) {
        int counter = 0;
        Tile[] coloredTiles = new Tile[3];

        for (int i = 0; i < 40; i++) {
            if (tiles[i].getTileColor() == c) {
                coloredTiles[counter] = tiles[i];
                counter++;
                if (c == Color.BLUE || c == Color.CYAN) {
                    if (counter == 1) {
                        coloredTiles[2] = coloredTiles[1];
                        break;
                    }
                }
            }
        }
        return coloredTiles;
    }

    public Color[] getTileColor() {return colorArray;}

}

