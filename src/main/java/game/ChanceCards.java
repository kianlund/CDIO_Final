package game;

import java.util.Random;

public class ChanceCards {
    public final Language chanceCardsStrings;
    private GameController gameController;
    private String defString;
    private Random rand;
    private final String[] colorStrings;

    public ChanceCards(GameController gameController) {
        chanceCardsStrings = new Language("dkChancecard.txt");
        defString = chanceCardsStrings.getLine(0);
        this.gameController = gameController;
        rand=new Random();
        colorStrings = new String[gameController.getBoard().getTileColor().length];
        for (int i = 0; i < colorStrings.length; i++) {
            colorStrings[i] = chanceCardsStrings.getLine(12 + i);
        }
    }

    public void DrawCard(Player player) {

        int a = rand.nextInt(20);
      // int a = 9;
        switch (a) {
            case (0):
                moveToStart(player);

                break;
            case (1):
                movePlayer(player, 3);
                break;
            case (2):
                giveMoney(player, 500);
                break;
            case (3):
                withdrawMoney(player, 1000);
                break;
            case (4):
                getOutOfJailFree(player);
                break;
            case (5):
                birthday(player);
                break;
            case (6):
                moveToLastTile(player);
                break;
            case (7):
                giveTax(player, 3000);
                break;
            case (8):
                giveMoney2(player, 1000);
             break;
            case (9):
                withdraw(player, 200);
                break;
            case (10):
                movePlayerForward(player,5);
                break;
            case (11):
                giveExtraHusHotel(player, 3100);
                break;
            case (12):
                movePlayerBack(player,37);
                break;
            case (13):
                movePlayerFreAlle(player,11,4000);
                break;
            case (14):
                giveMoneyVogn(player, 3000);
                break;
            case (15):
                giveMoneyTooth(player, 2000);
                break;
            case (16):
                withdrawFurniture(player, 1000);
                break;
            case (17):
                Party(player);
                break;
            case (18):
                OwnBreeding(player,200);
                break;
            case (19):
                SetIPrison(player,10);
                break;
        }
    }

    private void SetIPrison(Player player, int num) {
        String message = chanceCardsStrings.getLine(43);
        displayChanceCard(message);
        message = message.replace("Fængsel", gameController.getBoard().getTile(num).getGui_field().getDescription());
        int moveNum = num - player.getLocation();
        player.moveLocation(moveNum, gameController);

    }

    private void OwnBreeding(Player player, int amountGiven) {
        String message = chanceCardsStrings.getLine(30);
        displayChanceCard(message);
        player.addToBalance(amountGiven);
    }

    private void Party(Player player) {
        String message = chanceCardsStrings.getLine(47);
        displayChanceCard(message);
        for (Player p : gameController.getPlayerList()) {
            if (p != player) {
                p.payRent(p, player, 500);
            }
        }

    }

    private void withdrawFurniture(Player player, int amountGiven) {
        String message = chanceCardsStrings.getLine(42);
        displayChanceCard(message);
        player.addToBalance(amountGiven);
    }

    private void giveMoneyTooth(Player player, int amountTaken) {
        String message = chanceCardsStrings.getLine(41);
        displayChanceCard(message);
        if (!player.withdrawFromBalance(amountTaken)) {
        }
    }

    private void giveMoneyVogn(Player player, int amountTaken) {
        String message = chanceCardsStrings.getLine(29);
        displayChanceCard(message);
        if (!player.withdrawFromBalance(amountTaken)) {
        }
    }

    private void movePlayerFreAlle(Player player,int num,int amountGiven) {
        String message = chanceCardsStrings.getLine(46);
        displayChanceCard(message);
        message = message.replace("F.Alle", gameController.getBoard().getTile(num).getGui_field().getDescription());
        int moveNum = num - player.getLocation();
        player.moveLocation(moveNum, gameController);
        if(moveNum>0)
            player.addToBalance(amountGiven);

    }

    private void movePlayerBack(Player player, int steps) {
        String message = chanceCardsStrings.getLine(40);
        displayChanceCard(message);
        player.moveLocation(steps, gameController);
    }

    private void giveExtraHusHotel(Player player, int amountTaken) {
        String message = chanceCardsStrings.getLine(37);
        displayChanceCard(message);
        if (!player.withdrawFromBalance(amountTaken)) {
        }

    }

    private void movePlayerForward(Player player,int steps) {
        String message = chanceCardsStrings.getLine(2);
        displayChanceCard(message);
        player.moveLocation(steps, gameController);
    }

    private void giveMoney2(Player player, int amountGiven) {
        String message = chanceCardsStrings.getLine(23);
        displayChanceCard(message);
        player.addToBalance(amountGiven);
    }

    private void giveTax(Player player, int amountGiven) {
        String message = chanceCardsStrings.getLine(21);
        displayChanceCard(message);
        player.addToBalance(amountGiven);
        {
    }
    }

    private void giveMoney(Player player, int amountGiven) {
        player.addToBalance(amountGiven);
    }

    private void withdrawMoney(Player player, int amountTaken) {
        if (!player.withdrawFromBalance(amountTaken)) {
        }
    }

    private void movePlayer(Player player, int steps) {
        player.moveLocation(steps, gameController);
    }

    private void moveToStart(Player player) {
        String message = chanceCardsStrings.getLine(1);
        displayChanceCard(message);
        int location = player.getLocation();
        int moveNum = gameController.getNumberOfTiles() - location;
        player.moveLocation(moveNum, gameController);

    }

    private void withdraw(Player player, int amountTaken) {
        String message = chanceCardsStrings.getLine(27);
        displayChanceCard(message);
        if (!player.withdrawFromBalance(amountTaken)) {
        }
    }

    private void birthday(Player player) {
        String message = chanceCardsStrings.getLine(5);
        displayChanceCard(message);
        for (Player p : gameController.getPlayerList()) {
            if (p != player) {
                p.payRent(p, player, 200);
            }
        }
    }

    private void getOutOfJailFree(Player player) {
        String message = chanceCardsStrings.getLine(39);
        displayChanceCard(message);
        player.setGetOutOfJailCards(player.getGetOutOfJailCards() + 1);
        System.out.println(player.getGetOutOfJailCards());
    }


    private void moveToLastTile(Player player) {
        int maxNum = gameController.getNumberOfTiles();
        String message = chanceCardsStrings.getLine(8);
        message = message.replace("[Last Tile]", gameController.getBoard().getTile(maxNum - 1).getGui_field().getDescription());
        int moveNum = maxNum - player.getLocation() - 1;
        displayChanceCard(message);
        player.moveLocation(moveNum, gameController);
    }

    private void displayChanceCard(String message) {
        gameController.getGui().displayChanceCard(message);
        gameController.getGui().showMessage(defString);
        gameController.getGui().displayChanceCard(" ");
    }


    }



