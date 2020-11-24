package game;

import com.company.Main;
import gui_fields.*;

import java.awt.*;
import java.lang.reflect.Array;


/**
 * Should be used for the game board.
 * Tiles are the individual fields
 */
public class GameBoard {
    private Tile[] tiles;
    private final int[] effects={2,1,1,0,1,1,0,2,2,0,2,2,0,3,3,0,3,3,0,4,4,0,5,5};
    private final Color[] tileColor = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN, Color.PINK, Color.WHITE};
    private int[][] colorArr;
    private int colorCounter;
    private int arraySize;

    public GameBoard (int numOfTiles, GUI_Field[] gui_fields) {
        tiles = new Tile[numOfTiles];
        //tiles[0] = new Tile();
        for (int i = 0; i < numOfTiles; i++) {
            tiles[i] = new Tile(effects[i],gui_fields[i], i);
            if (gui_fields[i] instanceof GUI_Street || gui_fields[i] instanceof GUI_Start) {
                tiles[i].getGui_field().setTitle(Main.tileStrings.getLine(i));
                tiles[i].getGui_field().setSubText("M"+effects[i]);
                tiles[i].getGui_field().setDescription(Main.tileStrings.getLine(i));
            } else {
                tiles[i].getGui_field().setSubText(Main.tileStrings.getLine(i));
                tiles[i].getGui_field().setDescription(Main.tileStrings.getLine(i));
            }
        }

        //Setup board colors
        colorCounter = 0;
        arraySize = 2;
        colorArr = new int[tileColor.length][arraySize];
        // Doesn't scale with board size, arraySize is the number of color-grouped tiles
        int j = 0;
        for (int i = 1; i < numOfTiles; i++) {
            if (gui_fields[i] instanceof GUI_Street) {

                tiles[i].setTileColor(tileColor[colorCounter]);
                tiles[i].getGui_field().setBackGroundColor(tileColor[colorCounter]);
                colorArr[colorCounter][j++] = i;
            }
            else {
                j = 0;
                colorCounter++;
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

