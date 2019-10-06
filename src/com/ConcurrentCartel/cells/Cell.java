package com.ConcurrentCartel.cells;

import com.ConcurrentCartel.Ecosystem;

import java.util.Random;

public abstract class Cell extends Thread{
    protected Ecosystem ecosystem;
    protected CellStatus status;
    private int eatCount;

    public Cell() {
        ecosystem = Ecosystem.getInstance();
        status = CellStatus.HUNGRY;
    }

    public void run() {
        while(status != CellStatus.DEAD) {
            if (eatCount < 10){
                if (!eat()){
                    die();
                }
                else eatCount++;
            }
            else {
                reproduce();
            }
        }
    }

    private boolean eat() {
        return ecosystem.removeFoodUnit();
    }

    private void die() {
        ecosystem.removeCell(this);
        ecosystem.addFoodUnit(new Random().nextInt(5));
    }

    public abstract void reproduce();
}
