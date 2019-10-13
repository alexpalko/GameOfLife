package com.ConcurrentCartel.GameOfLife;

public class Main {

    public static void main(String[] args) {
        GameRules gameRules = new GameRules(3,3, 5, 5, 5);
        Ecosystem ecosystem = new Ecosystem(gameRules);
        ecosystem.start();
    }
}
