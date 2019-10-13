package com.ConcurrentCartel.GameOfLife.cells;

import com.ConcurrentCartel.GameOfLife.Ecosystem;

import java.util.concurrent.CountDownLatch;

public class AsexualCell extends Cell {

    public AsexualCell(Ecosystem ecosystem){
        super(ecosystem);
    }

    public AsexualCell(Ecosystem ecosystem, CountDownLatch startLatch) {
        super(ecosystem, startLatch);
    }

    protected void reproduce() {

    }

}
