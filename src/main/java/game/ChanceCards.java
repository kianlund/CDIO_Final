package game;

import java.awt.*;
import java.util.Random;

public class ChanceCards {
    public final Language chanceCardsStrings;
    private Game game;
    private String defString ;
    private Random rand;
    private final String[] colorStrings;
    public ChanceCards(Game game){
        chanceCardsStrings = new Language("engChancecard.txt");
        defString = chanceCardsStrings.getLine(0);
        this.game = game;
        rand = new Random();
        colorStrings = new String[game.getBoard().getTileColor().length];
        for (int i = 0; i < colorStrings.length; i++){
            colorStrings[i]=chanceCardsStrings.getLine(12+i);
        }

    }

    public void DrawCard(Player player){

        int a = rand.nextInt(9);
//        int a = 8;
        switch (a){
            case(0):
                moveToStart(player);
                break;
            case(1):
                move5Tiles(player);
                break;
            case(2):
                tooMuchCandy(player);
                break;
            case(3):
                homeworkDone(player);
                break;
            case(4):
                getOutOfJailFree(player);
                break;
            case(5):
                birthday(player);
                break;
            case(6):
                getFreeTileOneColor(player);
                break;
            case(7):
                moveToLastTile(player);
                break;
            case(8):
                getFreeTileTwoColors(player);
                break;
        }


    }

    private void moveToStart(Player player){
        String message = chanceCardsStrings.getLine(1);
        displayChanceCard(message);
        int location = player.getLocation();
        int moveNum = game.getNumberOfTiles() - location;
        player.moveLocation(moveNum, game);

    }

    private void move5Tiles(Player player){
        String message = chanceCardsStrings.getLine(2);
        displayChanceCard(message);
        player.moveLocation(5, game);
    }

    private void tooMuchCandy(Player player){
        String message = chanceCardsStrings.getLine(3);
        displayChanceCard(message);
        if (!player.withdrawFromBalance(2)){
        }
    }

    private void homeworkDone(Player player){
        String message = chanceCardsStrings.getLine(4);
        displayChanceCard(message);
        player.addToBalance(2);
    }

    private void birthday(Player player){
        String message = chanceCardsStrings.getLine(5);
        displayChanceCard(message);
        for (Player p: game.getPlayerList()){
            if(p != player){
                p.payRent(p, player, 1);
            }
        }

    }

    private void getOutOfJailFree(Player player){
        String message = chanceCardsStrings.getLine(6);
        displayChanceCard(message);
        player.setGetOutOfJailCards(player.getGetOutOfJailCards()+1);
        System.out.println(player.getGetOutOfJailCards());
    }

    private void getFreeTileOneColor(Player player){
        GameBoard board = game.getBoard();
        Color[] colors = board.getTileColor();

        int colNum = rand.nextInt(colors.length);
        int[] tilenums = board.getColorArray(colors[colNum]);
        Tile tile = board.getTile(tilenums[0]);
        String message = chanceCardsStrings.getLine(7);
        message = message.replace("[Color]", colorStrings[colNum]);
        displayChanceCard(message);
        boolean noOwner = false;
        if(tile.getOwner()!= null){
            for (int i: tilenums){
                if(board.getTile(i).getOwner() == null){
                    tile = board.getTile(i);
                    noOwner = true;
                }
            }
        }
        else{
            noOwner = true;
        }
        if(noOwner){
            String msg = player.getName() + chanceCardsStrings.getLine(10)+ " " + tile.getGui_field().getTitle() + chanceCardsStrings.getLine(11);
            displayChanceCard(message, msg);
            tile.setOwner(player);
        }
        int from = player.getLocation();
        int to = tile.getNumber();
        if (from < to){
            player.moveLocation(to-from, game);
        }
        else{
            int moveNum = (game.getNumberOfTiles() - from) + to;
            player.moveLocation(moveNum,game);
        }

    }



    private void moveToLastTile(Player player){
        int maxNum = game.getNumberOfTiles();
        String message = chanceCardsStrings.getLine(8);
        message= message.replace("[Last Tile]", game.getBoard().getTile(maxNum-1).getGui_field().getDescription());
        int moveNum = maxNum- player.getLocation() -1;
        displayChanceCard(message);
        player.moveLocation(moveNum, game);

    }

    private void getFreeTileTwoColors(Player player){
        GameBoard board = game.getBoard();
        Color[] colors = board.getTileColor();

        String message = chanceCardsStrings.getLine(9);

        int colNum1 = rand.nextInt(colors.length);
        int colNum2 = rand.nextInt(colors.length);
//        int colNum1 = 0;
//        int colNum2= 7;
        while (colNum1 == colNum2){
            colNum2 = rand.nextInt(colors.length);
        }
        int[] arr1 = board.getColorArray(colors[colNum1]);
        int[] arr2 = board.getColorArray(colors[colNum2]);


        int[] tilenums = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, tilenums, 0, arr1.length);
        System.arraycopy(arr2, 0, tilenums, arr1.length, arr2.length);
        Tile tile = board.getTile(tilenums[0]);
        message = message.replace("[Color1]", colorStrings[colNum1]);
        message = message.replace("[Color2]", colorStrings[colNum2]);
        displayChanceCard(message);
        boolean noOwner = false;
        if(tile.getOwner()!= null){
            for (int i: tilenums){
                if((board.getTile(i).getOwner() == null)&&!(tile.getOwner() == null)){
                    tile = board.getTile(i);
                    noOwner = true;
                }
            }
        }
        else{
            noOwner = true;
        }
        if(noOwner){
            String msg = player.getName() + chanceCardsStrings.getLine(10)+ " " + tile.getGui_field().getTitle() + chanceCardsStrings.getLine(11);
            displayChanceCard(message, msg);
            tile.setOwner(player);
        }
        int from = player.getLocation();
        int to = tile.getNumber();
        if (from < to){
            player.moveLocation(to-from, game);
        }
        else{
            int moveNum = (game.getNumberOfTiles() - from) + to;
            player.moveLocation(moveNum,game);
        }
    }

    private void displayChanceCard(String message){
        game.getGui().displayChanceCard(message);
        game.getGui().showMessage(defString );
        game.getGui().displayChanceCard(" ");
    }

    private void displayChanceCard(String message, String otherMsg){
        game.getGui().displayChanceCard(message);
        game.getGui().showMessage(defString + "\n" + otherMsg);
        game.getGui().displayChanceCard(" ");
    }
}
