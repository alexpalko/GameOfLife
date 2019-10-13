package com.ConcurrentCartel.GameOfLife.cells;

import com.ConcurrentCartel.GameOfLife.Ecosystem;
import java.util.concurrent.CountDownLatch;

public class SexualCell extends Cell {

    public SexualCell(Ecosystem ecosystem){
        super(ecosystem);
    }

    public SexualCell(Ecosystem ecosystem, CountDownLatch startLatch) {
        super(ecosystem, startLatch);
    }

    protected void reproduce() {
        throw new RuntimeException(new UnsupportedOperationException("Method not implemented"));
    }

}
