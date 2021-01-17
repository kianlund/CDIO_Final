package com.company;

import game.GameController;

public class Main {

//    public static final Language langStrings = new Language("resources/engGameStrings.txt");
//    public static final Language tileStrings = new Language("resources/engTileStrings.txt");

    public static void main(String[] args) {
        boolean test = false;
        if (test){
            GameController gameController = new GameController(test);
        }
        else {
            GameController gameController = new GameController();
        }
    }
}
