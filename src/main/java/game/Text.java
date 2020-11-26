package game;

import gui_fields.*;
import gui_main.GUI;


public class Text {
    private GUI_Field[] fields;
    private Game game;
    public final Language textStrings;
    private final String landString;
    private final String unOwnedString;
    private final String ownedSelfString;
    private final String ownedOtherStringLast;
    private final String ownedOtherStringFirst;
    private final String currencyPlural;
    private final String currencySingular;
    protected final String playerInprison;
    protected final String payBail;
    protected final String useJailCard;
    protected final String goToJail;
    protected final String freeParking;
    protected final String passedStart;
    protected final String visitingJail;

    private GameBoard board;
    private GUI gui;
    private Player player;
    int location;

    public Text(Game game) {
        textStrings = new Language("engFieldText.txt");
        int i = 0;
        landString = textStrings.getLine(i++);
        unOwnedString = textStrings.getLine(i++);
        ownedSelfString = textStrings.getLine(i++);
        ownedOtherStringFirst = textStrings.getLine(i++);
        ownedOtherStringLast = textStrings.getLine(i++);
        currencyPlural = textStrings.getLine(i++);
        currencySingular = textStrings.getLine(i++);
        playerInprison = textStrings.getLine(i++);
        payBail = textStrings.getLine(i++);
        useJailCard = textStrings.getLine(i++);
        goToJail = textStrings.getLine(i++);
        freeParking= textStrings.getLine(i++);
        passedStart = textStrings.getLine(i++);
        visitingJail = textStrings.getLine(i++);
        this.game = game;
        board = game.getBoard();
        gui = game.getGui();
    }


    public void TileMessage (Player player) {

        Tile tile = board.getTile(player.getLocation());
        String finalmsg = landString + " "+ tile.getGui_field().getTitle();
        if (tile.getOwner()== null){
            finalmsg += unOwnedString;
        }
        else if(tile.getOwner() == player) {
             displayMessage(finalmsg += ownedSelfString);
             return;
        }
        else{
            finalmsg += ownedOtherStringFirst + " " + tile.getOwner().getName() + ownedOtherStringLast;
        }
        finalmsg += " " + Integer.toString(game.getBoard().getRent(tile));
        if(tile.getRent() > 1){
            finalmsg += currencyPlural;
        }
        else{
            finalmsg += currencySingular;
        }
        displayMessage(finalmsg);
    }
    private void displayMessage(String message) {
        game.getGui().showMessage(message);
    }
    
        public GameBoard getBoard() {
            return board;
        }

    public String moneyMessage(int amount){
    String currency;
        if(amount == 1 || amount == -1){
            currency = currencySingular;
        }
        else{
            currency = currencyPlural;
        }
        return " " + Integer.toString(amount) + currency;
    }




}
