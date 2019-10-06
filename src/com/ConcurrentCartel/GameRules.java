package com.ConcurrentCartel;

public class GameRules {
    private int startACells;
    private int startSCells;
    private int fullTime;
    private int starveTime;

    public GameRules(int startACells, int startSCells, int fullTime, int starveTime){
        this.startACells = startACells;
        this.startSCells = startSCells;
        this.fullTime = fullTime;
        this.starveTime = starveTime;
    }

    public int getAsexualCellsStartNumber(){
        return startACells;
    }

    public int getSexualCellsStartNumber(){
        return startSCells;
    }

    public int getFullTime(){
        return fullTime;
    }

    public int getStarveTime(){
        return starveTime;
    }
}
