package game;

import gui_fields.*;

import java.awt.*;

public class Tile {

    private Player owner;
    private final int number;
    private String description;
    private int price;
    private int[] rent;
    private int property;
    private GUI_Field gui_field;
    private Color tileColor;

    public Tile (int number1, String description1, int theRent, GUI_Field gui_field1){
        this.number=number1;
        description=description1;
        price =theRent;
        gui_field=gui_field1;
        gui_field.setTitle(description);
        gui_field.setSubText(Integer.toString(number));
        owner = null;
    }

    public Tile (int priceInput, GUI_Field gui_field1, int num){
        price = priceInput;
        rent = null;
        property = 0;
        gui_field = gui_field1;
        tileColor = null;
        this.number = num;
        owner = null;
    }

    public int getPrice() {
        return price;
    }

    public int[] getRent() {
        return rent;
    }

    public void setRent(int[] input) {
        rent = input;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int input) {
        property = input;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        if (gui_field instanceof GUI_Ownable && owner != null) {
            ((GUI_Ownable) gui_field).setOwnerName(owner.getName());
            ((GUI_Ownable) gui_field).setRent(Integer.toString(price));
            ((GUI_Ownable) gui_field).setBorder(owner.getPrimaryColor());
        }
        if (gui_field instanceof GUI_Ownable && owner == null) {
            ((GUI_Ownable) gui_field).setOwnerName("");
            ((GUI_Ownable) gui_field).setBorder(null);
        }

    }

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }

    public GUI_Field getGui_field() {
        return gui_field;
    }

    public int getNumber() {return number;}

    public String getDescription(){return description;}
}

