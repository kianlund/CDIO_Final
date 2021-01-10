package game;

import com.company.Main;
import gui_fields.*;

import java.awt.*;

public class Tile {

    private Player owner;
    private final int number;
    private String description;
    private int rent;
    private GUI_Field gui_field;
    private Color tileColor;

    public Tile (int number1, String description1, int theRent, GUI_Field gui_field1){
        this.number=number1;
        description=description1;
        rent =theRent;
        gui_field=gui_field1;
        gui_field.setTitle(description);
        gui_field.setSubText(Integer.toString(number));
        owner = null;
    }

    public Tile (int theRent, GUI_Field gui_field1, int num){
        rent =theRent;
        gui_field=gui_field1;
        tileColor=null;
        this.number=num;
        owner = null;
    }

    public int getPrice() {

        return rent;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        if (gui_field instanceof GUI_Ownable) {
            ((GUI_Ownable) gui_field).setOwnerName(owner.getName());
            ((GUI_Ownable) gui_field).setRent(Integer.toString(rent));
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

