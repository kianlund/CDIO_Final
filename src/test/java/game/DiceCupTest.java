package game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiceCupTest {
    private int testNumberOfDice;
    private int testSum;
    private ArrayList<Dice> testDiceIncup;


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void rollDice() {
        testSum = 0;
    }

    @Test
    byte getNumberOfDice() {

        return 0;
    }

    @Test
    void getDiceinCup() {
        assertEquals(testNumberOfDice, getNumberOfDice());
    }

    @Test
    void getSum() {
    }
}