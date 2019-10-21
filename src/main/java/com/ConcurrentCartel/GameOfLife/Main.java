package com.ConcurrentCartel.GameOfLife;

public class Main {

    public static void main(String[] args) {
        GameRules gameRules = new GameRules(0,7, 5, 5, 5);
        Ecosystem ecosystem = new Ecosystem(gameRules);
        ecosystem.start();
    }
}
