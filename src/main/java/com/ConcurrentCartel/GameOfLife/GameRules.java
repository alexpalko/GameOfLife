package com.ConcurrentCartel.GameOfLife;

public class GameRules {
    private int startACells;
    private int startSCells;
    private int startFoodUnits;
    private int fullTime;
    private int starveTime;

    public GameRules(int startACells, int startSCells, int startfoodUnits, int fullTime, int starveTime){
        this.startACells = startACells;
        this.startSCells = startSCells;
        this.startFoodUnits = startfoodUnits;
        this.fullTime = fullTime;
        this.starveTime = starveTime;
    }

    public int getAsexualCellsStartNumber(){
        return startACells;
    }

    public int getSexualCellsStartNumber(){
        return startSCells;
    }

    public int getStartFoodUnits() {return startFoodUnits; }

    public int getFullTime(){
        return fullTime;
    }

    public int getStarveTime(){
        return starveTime;
    }
}
