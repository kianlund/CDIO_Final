package game;

import gui_fields.*;

import java.awt.*;


/**
 * Should be used for the game board.
 * Tiles are the individual fields
 */
public class GameBoard {
    private Tile[] tiles;
    private final int[] tilePrice ={4000,1200,0,1200,
            0,4000,2000,0,
            2000,2400,0,2800,
            3000,2800,3200,4000,
            3600,0,3600,4000,
            0,4400,0,4400,
            4800,4000,5200,5200,
            3000,5600,0,6000,
            6000,0,6400,4000,
            0,7000,0,8000};
    private final Color[] colorArray = {Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GRAY, Color.RED, Color.WHITE, Color.PINK, Color.CYAN};
    private int[][] colorArr;
    private int colorCounter;
    private int tileCounter;
    private int arraySize;

    private Language tileStrings = new Language("dkTileStrings.txt");

    public GameBoard (int numOfTiles, GUI_Field[] gui_fields) {
        tiles = new Tile[numOfTiles];
        colorCounter = 0;
        tileCounter = 1;
        //tiles[0] = new Tile();
        for (int i = 0; i < numOfTiles; i++) {
            tiles[i] = new Tile(tilePrice[i],gui_fields[i], i);
            if (gui_fields[i] instanceof GUI_Street || gui_fields[i] instanceof GUI_Brewery || gui_fields[i] instanceof GUI_Shipping) {
                tiles[i].getGui_field().setTitle(tileStrings.getLine(i));
                tiles[i].getGui_field().setSubText("Pris: "+ tilePrice[i]+"kr.");
                tiles[i].getGui_field().setDescription(tileStrings.getLine(i));
                if (gui_fields[i] instanceof GUI_Street) {
                    tiles[i].setTileColor(colorArray[colorCounter]);
                    tiles[i].getGui_field().setBackGroundColor(colorArray[colorCounter]);
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

    public int[] getColorArray(Color c) { //Returns 1-dimensional array with the color appropriate tiles
        int[] tempArr = new int[arraySize];
        tempArr[0] = 0; //default
        tempArr[1] = 0; //default
        for (int i = 0; i < colorArray.length; i++) {
            if (c == colorArray[i]) {
                for (int j = 0; j < arraySize; j++) {
                    tempArr[j] = colorArr[i][j];
                }
            }
        }
        return tempArr;
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

    public int getRent(Tile tile){
        if(tile.getOwner() != null){
            Player owner = tile.getOwner();
            int[] checklist = getColorArray(tile.getTileColor());
            for (int i = 0; i < checklist.length; i++){
                Tile otherTile;
                if(checklist[i] != tile.getNumber()){
                    otherTile = tiles[checklist[i]];
                    if(tile.getOwner() == otherTile.getOwner()){
                        return tile.getRent()*2;
                    }
                }
            }
        }
        return tile.getRent();
    }


}

