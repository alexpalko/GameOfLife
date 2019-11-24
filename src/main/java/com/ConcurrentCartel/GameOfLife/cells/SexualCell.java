package com.ConcurrentCartel.GameOfLife.cells;

import com.ConcurrentCartel.GameOfLife.Ecosystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

public class SexualCell extends Cell {

    Object beingCourted = new Object();

    public SexualCell(Ecosystem ecosystem){
        super(ecosystem);
    }

    public SexualCell(Ecosystem ecosystem, CountDownLatch startLatch) {
        super(ecosystem, startLatch);
    }

    protected void reproduce() {
        while (super.status == CellStatus.MATING) {
            // Get a collection of all living sexual cells in the ecosystem
            Collection<SexualCell> cells = ecosystem.getSexualCells();

            // The collection contains only one cell, the one requesting it
            // In this case the sexual cell will be unable to reproduce
            // and will continue to look for food until none is left and it dies.
            if (cells.size() == 1){
                status = CellStatus.HUNGRY;
                eatCount = 0;
            }

            for (SexualCell cell : cells) {
                // Check if the current status did not change.
                // This may happen if another mating cell signaled this cell first.
                if (super.status != CellStatus.MATING) break;
                if (cell == this) continue;
                if(Signal(cell)){
                    logger.info("{} reproduced with {}", this.getName(), cell.getName());
                    break;
                }
            }
        }
    }

    // A lock is used to prevent multiple cells signaling the current cell at one time
    // If he signaled cell is in mating state, a new cell is born and added to the ecosystem
    // and the status of both the current cell and the one being signaled change to hungry.
    private boolean Signal(Cell cell){
        synchronized (beingCourted) {
            if (cell.status == CellStatus.MATING) {
                ecosystem.addCell(new SexualCell(ecosystem));
                cell.status = CellStatus.HUNGRY;
                this.status = CellStatus.HUNGRY;
                return true;
            } else
                return false;
        }
    }
}
