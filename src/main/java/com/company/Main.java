package com.company;

import game.Game;
import game.Language;

public class Main {

    //public static final Language langStrings = new Language("resources/engGameStrings.txt");
    //public static final Language tileStrings = new Language("resources/engTileStrings.txt");

    public static void main(String[] args) {
        boolean test = false;
        if (test){
            Game game = new Game(test);
        }
        else {
            Game game = new Game();
        }
    }
}
