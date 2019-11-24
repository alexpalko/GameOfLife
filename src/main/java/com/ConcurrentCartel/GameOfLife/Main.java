package com.ConcurrentCartel.GameOfLife;

public class Main {

    public static void main(String[] args) {
        GameRules gameRules = new GameRules(2,10, 70, 2, 5);
        Ecosystem ecosystem = new Ecosystem(gameRules);
        ecosystem.start();
    }
}
