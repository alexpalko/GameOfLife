package com.ConcurrentCartel.cells;

public class SexualCell extends Cell {
    @Override
    public void reproduce() {
        if (ecosystem.findMate())
        this.status = CellStatus.HUNGRY;
    }
}
