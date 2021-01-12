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
        chanceCardsStrings = new Language("dkChancecard.txt");
        defString = chanceCardsStrings.getLine(0);
        this.game = game;
        rand = new Random();
        colorStrings = new String[game.getBoard().getTileColor().length];
        for (int i = 0; i < colorStrings.length; i++){
            colorStrings[i]=chanceCardsStrings.getLine(12+i);
        }

    }

    public void DrawCard(Player player){

        int a = rand.nextInt(7);
//        int a = 8;
        switch (a){
            case(0):
                moveToStart(player);
                break;
            case(1):
                movePlayer(player,3);
                break;
            case(2):
                giveMoney(player,500);
                break;
            case(3):
                withdrawMoney(player,1000);
                break;
            case(4):
                getOutOfJailFree(player);
                break;
            case(5):
                birthday(player);
                break;
            case(6):
                moveToLastTile(player);
                break;
        }


    }

    private void giveMoney(Player player, int amountGiven){
        player.addToBalance(amountGiven);
    }

    private void withdrawMoney(Player player, int amountTaken){
        if (!player.withdrawFromBalance(amountTaken)){
        }
    }

    private void movePlayer(Player player, int steps){
        player.moveLocation(steps, game);
    }

    private void moveToStart(Player player){
        String message = chanceCardsStrings.getLine(1);
        displayChanceCard(message);
        int location = player.getLocation();
        int moveNum = game.getNumberOfTiles() - location;
        player.moveLocation(moveNum, game);

    }

    private void tooMuchCandy(Player player){
        String message = chanceCardsStrings.getLine(3);
        displayChanceCard(message);
        if (!player.withdrawFromBalance(2)){
        }
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



    private void moveToLastTile(Player player){
        int maxNum = game.getNumberOfTiles();
        String message = chanceCardsStrings.getLine(8);
        message= message.replace("[Last Tile]", game.getBoard().getTile(maxNum-1).getGui_field().getDescription());
        int moveNum = maxNum- player.getLocation() -1;
        displayChanceCard(message);
        player.moveLocation(moveNum, game);

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
