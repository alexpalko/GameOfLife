package com.ConcurrentCartel.GameOfLife.cells;

import com.ConcurrentCartel.GameOfLife.Ecosystem;
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
            Collection<SexualCell> cells = ecosystem.getSexualCells();
            for (SexualCell cell : cells) {
                if (super.status != CellStatus.MATING) break;
                if (cell == this) continue;
                if(Signal(cell)){
                    logger.info("{} reproduced with {}", this.getName(), cell.getName());
                    break;
                }
            }
        }
    }

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
