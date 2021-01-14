package game;

import gui_fields.*;

import java.awt.*;

public class TileHandler{
    private int prisonNumber;
    public TileHandler(int prisonAt){
        prisonNumber = prisonAt;
    }

    Language tileHandlerText = new Language("dkFieldText2.txt");

    /**
     * removes the players car from the field and redraws the others
     * @param game the game
     */
    public void removeOneCar(Game game, Player player){
        GUI_Field field = game.getGui().getFields()[player.getLocation()];
        Player replaceCars[] = new Player[game.getTotalNumPlayers()];
        int j= 0;
        for(int i =0; i < game.getPlayerList().length; i++ ){
            if(field.hasCar(game.getPlayerList()[i]) &
                    !game.getPlayerList()[i].getName().equals(player.getName())){
                replaceCars[j] = game.getPlayerList()[i];
                j++;
            }
        }
        field.removeAllCars();
        if (replaceCars.length > 0){
            for(int i =0; i < replaceCars.length; i++){
                if(replaceCars[i] != null){
                    field.setCar(replaceCars[i], true);
                }
            }
        }
    }

    /**
     * This is what happens when a player lands on a new field
     * @param tile the tile the player lands on
     */
    public void landOnField(Tile tile, Game game, Player player) {

        tile.getGui_field().setCar(player, true);
        GameBoard b = game.getBoard();

        if(tile.getGui_field() instanceof GUI_Ownable) {
            if (tile.getOwner() == null) {
                askBuyTile(tile,game,player);
            } else if (tile.getOwner() != player) {
                if (tile.getGui_field() instanceof GUI_Street) {
                    if (b.getTilesByColor(tile.getTileColor())[0].getOwner() ==
                            b.getTilesByColor(tile.getTileColor())[1].getOwner() &&
                            b.getTilesByColor(tile.getTileColor())[1].getOwner() ==
                                    b.getTilesByColor(tile.getTileColor())[2].getOwner() &&
                            tile.getProperty() == 0)
                    {
                        game.getGui().showMessage(tileHandlerText.getLine(0) + " " + tile.getGui_field().getTitle() + ", " + tileHandlerText.getLine(3) + " " + tile.getOwner().getName() + tileHandlerText.getLine(4) + " " + tile.getRent()[0] * 2);
                        player.payRent(player, tile.getOwner(), tile.getRent()[0] * 2);
                    } else {
                        game.getGui().showMessage(tileHandlerText.getLine(0) + " " + tile.getGui_field().getTitle() + ", " + tileHandlerText.getLine(3) + " " + tile.getOwner().getName() + tileHandlerText.getLine(4) + " " + tile.getRent()[tile.getProperty()]);
                        player.payRent(player, tile.getOwner(), tile.getRent()[tile.getProperty()]);
                    }
                }
                if (tile.getGui_field() instanceof GUI_Shipping) {
                    int counter = -1;
                    if (tile.getOwner()==game.getBoard().getTiles()[5].getOwner()){counter++;}
                    if (tile.getOwner()==game.getBoard().getTiles()[15].getOwner()){counter++;}
                    if (tile.getOwner()==game.getBoard().getTiles()[20].getOwner()){counter++;}
                    if (tile.getOwner()==game.getBoard().getTiles()[25].getOwner()){counter++;}
                    int[] shippingRent = {500, 1000, 2000, 4000};
                    game.getGui().showMessage(tileHandlerText.getLine(0) + " " + tile.getGui_field().getTitle() + ", " + tileHandlerText.getLine(3) + " " + tile.getOwner().getName() + tileHandlerText.getLine(4) + " " + shippingRent[counter]);
                    player.payRent(player, tile.getOwner(), shippingRent[counter]);
                }
                if (tile.getGui_field() instanceof GUI_Brewery) {
                    if (game.getBoard().getTiles()[12].getOwner()==game.getBoard().getTiles()[28].getOwner()){
                        game.getGui().showMessage(tileHandlerText.getLine(0) + " " + tile.getGui_field().getTitle() + ", " + tileHandlerText.getLine(3) + " " + tile.getOwner().getName() + tileHandlerText.getLine(4) + " " + (game.getCup().getSum()*200));
                        player.payRent(player, tile.getOwner(), (game.getCup().getSum()*200));
                    } else {
                        game.getGui().showMessage(tileHandlerText.getLine(0) + " " + tile.getGui_field().getTitle() + ", " + tileHandlerText.getLine(3) + " " + tile.getOwner().getName() + tileHandlerText.getLine(4) + " " + (game.getCup().getSum()*100));
                        player.payRent(player, tile.getOwner(), (game.getCup().getSum()*100));
                    }
                }
            }
        }
        else if(tile.getGui_field() instanceof GUI_Chance){
            game.drawChance(player);
        }
        else if(tile.getGui_field() instanceof GUI_Jail){
            prisonHandler(tile, player, game);
        }
        else if(tile.getGui_field() instanceof GUI_Refuge) {
            game.getGui().showMessage(game.getTextStrings().freeParking);
        }
        else if(tile.getGui_field() instanceof GUI_Tax) {
            if(tile.getNumber()==game.getBoard().getTiles()[4].getNumber()) {
                if (game.getGui().getUserLeftButtonPressed("Du skal betale indkomstskat. Vil du betale 10% af alle dine værdier eller 4000?","10%","4000")) {
                    int sumOfTiles = 0;
                    for (int i = 0; i < 40; i++) {
                        if (player==game.getBoard().getTiles()[i].getOwner()) {
                            sumOfTiles+=game.getBoard().getTiles()[i].getPrice();
                        }
                    }
                    int tax =(player.getBalance()+sumOfTiles)/10;
                    player.withdrawFromBalance(tax);
                    game.getGui().showMessage("Du betaler " + tax + " kr");

                } else {
                    game.getGui().showMessage("Du betaler 4000 kr");
                    player.withdrawFromBalance(4000);
                }
            }
            else if(tile.getNumber()==game.getBoard().getTiles()[39].getNumber()) {
                game.getGui().showMessage("Du skal betale Ekstraordinær statskat. Du betaler 2000 kr");
                player.withdrawFromBalance(2000);
            }
        }
        askBuyHousing(tile,game,player);
    }

    private void askBuyTile(Tile tile, Game game, Player player) {
        Text textStrings = game.getTextStrings();
        String tileName = tile.getGui_field().getTitle();
        boolean buyOrNot = game.getGui().getUserLeftButtonPressed("Køb "+tileName+"?", "Ja", "Nej"); //skal bruge dkEngStringssomething !!
        if (buyOrNot) {
            textStrings.TileMessage(player);
            player.buyTile(player, tile, false);
        }
    }

    private void askBuyHousing(Tile tile, Game game, Player player) {
        Boolean askToBuyHousing = false;
        String[] ownedTiles = new String[1];
        ownedTiles[0] = "Nej. Slut tur.";
        String[] tempOwnedTiles;
        GameBoard b = game.getBoard();

        if (player.getBalance() > 1000) {       // Make sure players has available funds
            for (int i = 0; i < 8; i++)
            {
                //Check if player owns all tile of the same color. Loops through all colors.
                if (player == b.getTilesByColor(b.getTileColor()[i])[0].getOwner() &&
                        player == b.getTilesByColor(b.getTileColor()[i])[1].getOwner() &&
                        player == b.getTilesByColor(b.getTileColor()[i])[2].getOwner() )
                {
                    for (int j = 0; j < 3; j++) {
                        //Find lowest property setting(Houses. Property 5 = hotel). Add tile name ONLY if it has the lowest property of the color
                        int lowestProperty;
                        lowestProperty = Math.min(b.getTilesByColor(b.getTileColor()[i])[0].getProperty(),
                                b.getTilesByColor(b.getTileColor()[i])[1].getProperty());
                        lowestProperty = Math.min(lowestProperty,b.getTilesByColor(b.getTileColor()[i])[2].getProperty());

                        if (b.getTilesByColor(b.getTileColor()[i])[j].getProperty() == lowestProperty){
                            //Makes sure that tile doesn't have a hotel already
                            if (!ownedTiles[ownedTiles.length - 1].equals(b.getTilesByColor(b.getTileColor()[i])[j].getGui_field().getTitle()) &&
                                    b.getTilesByColor(b.getTileColor()[i])[j].getProperty() < 5)
                            {
                                //Adds one new element to ownedTiles.
                                tempOwnedTiles = ownedTiles.clone();
                                ownedTiles = new String[tempOwnedTiles.length+1];
                                for (int k = 0; k < tempOwnedTiles.length; k++) {
                                    ownedTiles[k] = tempOwnedTiles[k];
                                }
                                ownedTiles[ownedTiles.length-1] = b.getTilesByColor(b.getTileColor()[i])[j].getGui_field().getTitle();
                            }
                        }
                    }
                    askToBuyHousing = true;
                }
            }
        }
        if (askToBuyHousing){
            String selection = game.getGui().getUserSelection("Vil du købe husering på en af følgende felter? 1000kr. pris. ",ownedTiles);
            buyPropertyWithTitle(selection, game, player);
        }
    }

    public void buyPropertyWithTitle(String tileToUpgrade, Game game, Player player) {
        GameBoard b = game.getBoard();
        for (int i = 0; i < game.getNumberOfTiles(); i++) {
            if (b.getTiles()[i].getGui_field().getTitle().equals(tileToUpgrade)){
                b.getTiles()[i].setProperty(b.getTiles()[i].getProperty() + 1);
                player.withdrawFromBalance(1000);
                if (b.getTiles()[i].getProperty() == 5) {
                    ((GUI_Street) b.getTiles()[i].getGui_field()).setHouses(0);
                    ((GUI_Street) b.getTiles()[i].getGui_field()).setHotel(true);
                } else {
                    ((GUI_Street) b.getTiles()[i].getGui_field()).setHouses(b.getTiles()[i].getProperty());
                }
            }
        }

    }
//    public void buyHouse(Tile tile, Player player, Game game) {
//        GameBoard b = game.getBoard();
//            if (b.getTiles()[6].getOwner() == player && b.getTiles()[8].getOwner() == player && b.getTiles()[9].getOwner() == player ){
//                if(gui.getUserLeftButtonPressed("Har du lyst til at købe et gult hus?", "Ja", "Nej")){
//                    String Selection = gui.getUserButtonPressed("Hvor skal huset være?", b.getTiles()[6].getGui_field().getTitle(),b.getTiles()[8].getGui_field().getTitle(), b.getTiles()[9].getGui_field().getTitle());
//                          if (Selection == b.getTiles()[6].getGui_field().getTitle()){
//
//                          }
//                }
//
//                else{}
//
//            }
//
//    }

    public void prisonHandler(Tile tile, Player player, Game game){
        if(tile.getNumber() != prisonNumber) {
            player.setInPrison(true);
            game.getGui().showMessage(game.getTextStrings().goToJail);
            removeOneCar(game, player);
            player.setLocation(prisonNumber);
        }
        else{
            game.getGui().showMessage(game.getTextStrings().visitingJail);
        }
    }
}
