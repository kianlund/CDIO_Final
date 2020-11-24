package game;

import gui_fields.*;

public class Player extends GUI_Player {

    //player variables
    private int location;
    private boolean bankrupt;
    private boolean prison;
    private int getOutOfJailCards;
    private TileHandler tilehandler;

    /**
     *
     * @param playerName The name of the player string
     * @param playerBalance The amount of money the player starts with
     * @param startLocation The tile the player starts on
     * @param playerCar The GUI implementation for the car
     */
    public Player(
            String playerName, int playerBalance, int startLocation,
            GUI_Car playerCar, TileHandler tileHandler){
        super(playerName, playerBalance, playerCar);
        this.location = startLocation;
        bankrupt = false;
        getOutOfJailCards = 0;
        this.tilehandler = tileHandler;
    }

    public boolean getBankrupt(){ return bankrupt;}

    public void addToBalance(int balanceGain) {
        setBalance(getBalance()+balanceGain) ;
    }

    public boolean withdrawFromBalance(int payment){
        int newBalance = getBalance() - payment;
        if (newBalance < 0){
            bankrupt = true;

            return false;
        }
        else{
            setBalance(newBalance);
            return true;
        }
    }


    //returns player location
    public int getLocation() { return location;}

    //set location
    public void setLocation(int newLocation){
        location = newLocation;
    }

    /**
     *
     * @param moveNumber How many tiles should the player be moved?
     * @param game the game class for redrawing the cars in the gui
     */
    public void moveLocation(int moveNumber, Game game){
        tilehandler.removeOneCar(game, this);
        location += moveNumber;
        while (location >= 24) {
            location -= 24;
            game.getGui().showMessage(game.getTextStrings().passedStart
                    + game.getTextStrings().moneyMessage(2));
            addToBalance(2);
        }
        tilehandler.landOnField(
                game.getBoard().getTiles()[location], game,this);
    }


    public void buyTile(Player player, Tile tile, boolean getFree){
        if(!getFree){
            if(withdrawFromBalance(tile.getRent())){
                tile.setOwner(this);
            }
            else{
                withdrawFromBalance(getBalance());
            }
        }
        else {
            tile.setOwner(this);
        }
    }


    public void payRent(
            Player fromPlayer, Player toPlayer, int amount){
        if (fromPlayer.withdrawFromBalance(amount)){
            toPlayer.addToBalance(amount);
        }
        else{
            int restAmount =fromPlayer.getBalance();
            fromPlayer.withdrawFromBalance(restAmount);
            toPlayer.addToBalance(restAmount);
        }
    }

    public boolean getPrison() {
        return prison;
    }

    public void setInPrison(boolean b) { prison = b;}

    public void setGetOutOfJailCards(int amount){
        getOutOfJailCards = amount;
    }

    public int getGetOutOfJailCards(){return getOutOfJailCards;}

    public void addGetOutofJailCard(int amount){
        getOutOfJailCards += amount;
    }

    public boolean removeGetOutOfJailCard(int amount){
        if (getOutOfJailCards > 0){
            getOutOfJailCards -= amount;
            return true;
        }
        return false;
    }

    public void startFromPrison(Game game){
        try {
            game.getGui().showMessage(game.getTextStrings().playerInprison);
        }
        catch (NullPointerException e) {
            System.err.println("No game initialized");
        }
        if(getOutOfJailCards > 0){
            removeGetOutOfJailCard(1);
            try {
                game.getGui().showMessage(game.getTextStrings().useJailCard);
            }
            catch (NullPointerException e) {
                System.err.println("No game initialized");
            }
            prison = false;
        }
        else{
            if (withdrawFromBalance(2)){
                try {
                    game.getGui().showMessage(game.getTextStrings().payBail);
                }
                catch (NullPointerException e) {
                    System.err.println("No game initialized");
                }
                prison = false;
            }
        }
    }
}


