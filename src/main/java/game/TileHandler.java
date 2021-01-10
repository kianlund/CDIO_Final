package game;

import gui_fields.*;
import gui_main.GUI;

public class TileHandler{
    private int prisonNumber;
    public TileHandler(int prisonAt){
        prisonNumber = prisonAt;
    }
    private GUI gui;
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

        Text textStrings = game.getTextStrings();
        tile.getGui_field().setCar(player, true);

        if(tile.getGui_field() instanceof GUI_Street || tile.getGui_field() instanceof GUI_Brewery || tile.getGui_field() instanceof GUI_Shipping) {
            if (tile.getOwner() == null) {
                askBuyTile(tile,game,player);
            } else if (tile.getOwner() != player) {
                textStrings.TileMessage(player);
                player.payRent(player, tile.getOwner(), tile.getPrice());
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
