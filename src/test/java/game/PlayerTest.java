package game;

import gui_fields.GUI_Field;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
public  Player testplayer;
String testName = "Testplayer";
int location = 0;
int balance ;
    @Test
    public void constructorTest(){
        assertEquals(testName, testplayer.getName());
        assertEquals(balance, testplayer.getBalance());
        assertEquals(location, testplayer.getLocation());

    }

    @BeforeEach
    public void beforeTestsMethod(){
        balance= 1000;
        testplayer = new Player(testName, balance, location, null, null);
    }

    @Test
    void addToBalance() {
        int addedToBalance = 100;
        testplayer.addToBalance(100);
        assertEquals(balance+addedToBalance, testplayer.getBalance());
    }

    @Test
    void withdrawFromBalanceTest(){
        int withdrawAmount = 100;
        testplayer.withdrawFromBalance(withdrawAmount);
        assertEquals(testplayer.getBalance(), balance -100);
        int tooBigamount =100000;
        testplayer.withdrawFromBalance(tooBigamount);
        assertEquals(testplayer.getBankrupt(), true);
    }

    @Test
    void payRenttest(){
        int rentAmount = 200;
        Player otherPlayer = new Player("otherPlayer2", balance, location, null, null);
        testplayer.payRent(testplayer, otherPlayer, rentAmount);
        assertEquals(testplayer.getBalance(), balance-rentAmount);
        assertEquals(otherPlayer.getBalance(), balance+rentAmount);
        int tooMuchRent = 2000;
        testplayer.payRent(testplayer, otherPlayer, tooMuchRent);
        assertEquals(testplayer.getBankrupt(), true);
        assertEquals(testplayer.getBalance(), 0);
        assertEquals(otherPlayer.getBalance(), 2000);
    }

    @Test
    void buyTileTest() throws NullPointerException{
        int tilerent = 100;
        int num = 0;
        GUI_Field gui_field = null;
        Tile testTile = new Tile(tilerent, gui_field, num);
        Tile newTile = new Tile(tilerent, gui_field, num+1);
        Tile expensiveTile = new Tile(tilerent*100, gui_field, num+2);
        try {
            testplayer.buyTile(testplayer, testTile, false);
            }
        catch (NullPointerException E){}
        assertEquals(testTile.getOwner(), testplayer);
        assertEquals(testplayer.getBalance(), balance - tilerent);
        try {
            testplayer.buyTile(testplayer, newTile, true);
        }
        catch (NullPointerException E){}
        assertEquals(testplayer.getBalance(), balance - tilerent);
        assertEquals(testplayer, newTile.getOwner());
        try {
            testplayer.buyTile(testplayer, expensiveTile, false);
        }
        catch (NullPointerException e){}
        assertNotEquals(testplayer, expensiveTile.getOwner());
        assertEquals(testplayer.getBalance(), 0);
        assertEquals(testplayer.getBankrupt(), true);
    }
    @Test
    void prisonTest(){
        testplayer.setInPrison(true);
        assertEquals(testplayer.getPrison(), true );
        testplayer.addGetOutofJailCard(1);
        assertEquals(testplayer.getGetOutOfJailCards(), 1);
        testplayer.setGetOutOfJailCards(2);
        assertEquals(testplayer.getGetOutOfJailCards(), 2);
        testplayer.removeGetOutOfJailCard(1);
        assertEquals(testplayer.getGetOutOfJailCards(), 1);
        testplayer.startFromPrison(null);
        assertEquals(testplayer.getGetOutOfJailCards(), 0);
        assertEquals(testplayer.getPrison(), false);
        testplayer.setInPrison(true);
        int balance = testplayer.getBalance();
        testplayer.startFromPrison(null);
        assertNotEquals(testplayer.getBalance(), balance);
    }

    @Test
    void setLocationTest(){
        testplayer.setLocation(4);
        assertEquals(testplayer.getLocation(), 4);
        assertEquals(testplayer.removeGetOutOfJailCard(2), false);
    }
}
