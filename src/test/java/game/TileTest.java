package game;

import gui_fields.GUI_Field;
import gui_fields.GUI_Street;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    public  Tile testTile;
    String testName = "TestTile";
    int number;
    int rent;
    GUI_Field gui_field;
    Color color;
    Player owner;
    int pBalance = 5;


    @BeforeEach
    public void beforeTestsMethod(){
        gui_field = new GUI_Street();
        number = 1;
        rent = 5;
        color = Color.RED;
        owner = new Player("owner", pBalance, 0, null, null);

        testTile = new Tile(rent, gui_field, number);
    }

    @Test
    void constuctorTest(){
        testTile =null;
        testTile = new Tile(number, testName, rent, gui_field);

        assertEquals(testTile.getDescription(), testName);
        assertEquals(testTile.getNumber(), number);
        assertEquals(testTile.getRent(), rent);
        assertEquals(testTile.getGui_field().getTitle(), testName);
        assertEquals(testTile.getGui_field().getSubText(), Integer.toString(number));
        assertNull(testTile.getOwner());
    }
    @Test
    void getRent() {
        assertEquals(rent, testTile.getRent());
    }

    @Test
    void setAndGetOwner() {
        testTile.setOwner(owner);
        assertEquals(owner, testTile.getOwner());
    }

    @Test
    void setAndGetTileColor() {
        testTile.setTileColor(Color.BLUE);
        assertEquals(testTile.getTileColor(), Color.BLUE);
    }

    @Test
    void getGui_field() {
        assertEquals(testTile.getGui_field(), gui_field);
    }

    @Test
    void getNumber() {
        assertEquals(testTile.getNumber(), number);
    }
}