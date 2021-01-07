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
    private final Color[] tileColor = {Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GRAY, Color.RED, Color.WHITE, Color.PINK, Color.CYAN};
    private int[][] colorArr;
    private int colorCounter;
    private int tileCounter;
    private int arraySize;

    private Language tileStrings = new Language("dkTileStrings.txt");

    public GameBoard (int numOfTiles, GUI_Field[] gui_fields) {
        tiles = new Tile[numOfTiles];
        //tiles[0] = new Tile();
        for (int i = 0; i < numOfTiles; i++) {
            tiles[i] = new Tile(tilePrice[i],gui_fields[i], i);
            if (gui_fields[i] instanceof GUI_Street || gui_fields[i] instanceof GUI_Brewery || gui_fields[i] instanceof GUI_Shipping) {
                tiles[i].getGui_field().setTitle(tileStrings.getLine(i));
                tiles[i].getGui_field().setSubText("Pris: "+ tilePrice[i]+"kr.");
                tiles[i].getGui_field().setDescription(tileStrings.getLine(i));
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

        //Setup board colors
        colorCounter = 1;
        arraySize = 3;
        tileCounter = 0;
        colorArr = new int[tileColor.length][arraySize];

        //Set colors manually of tiles of 2
        tiles[1].setTileColor(tileColor[0]);
        tiles[1].getGui_field().setBackGroundColor(tileColor[0]);
        tiles[3].setTileColor(tileColor[0]);
        tiles[3].getGui_field().setBackGroundColor(tileColor[0]);
        tiles[37].setTileColor(tileColor[7]);
        tiles[37].getGui_field().setBackGroundColor(tileColor[7]);
        tiles[39].setTileColor(tileColor[7]);
        tiles[39].getGui_field().setBackGroundColor(tileColor[7]);

        //Set colors of tiles of 3
        int j = 0;
        for (int i = 5; i < 35; i++) {
            if (gui_fields[i] instanceof GUI_Street) {
                tiles[i].setTileColor(tileColor[colorCounter]);
                tiles[i].getGui_field().setBackGroundColor(tileColor[colorCounter]);
                colorArr[colorCounter][j++] = i;
                tileCounter++;
                if (tileCounter == 3) {
                    colorCounter++;
                    tileCounter = 0;
                }
            }
            else {
                j = 0;
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
        for (int i = 0; i < tileColor.length; i++) {
            if (c == tileColor[i]) {
                for (int j = 0; j < arraySize; j++) {
                    tempArr[j] = colorArr[i][j];
                }
            }
        }
        return tempArr;
    }

    public Color[] getTileColor() {return tileColor;}

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

