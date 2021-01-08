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
        int tileRent;
        int tempTileNumber;

        if(tile.getGui_field() instanceof GUI_Street) {

            textStrings.TileMessage(player);
            if (tile.getOwner() == null) {
                    player.buyTile(player, tile, false);
                }
            else if (tile.getOwner() != player && tile.getOwner() != null) {
                GameBoard b = game.getBoard();
                tempTileNumber = b.getColorArray(tile.getTileColor())[0];
                if (b.getTiles()[tempTileNumber].getOwner() ==
                        b.getTiles()[(tempTileNumber+1)].getOwner()) {
                    tileRent = tile.getRent() * 2;
                } else {
                    tileRent = tile.getRent();
                }
                player.payRent(player, tile.getOwner(), tileRent);
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
