package com.ConcurrentCartel.cells;

public class AsexualCell extends Cell {
    @Override
    public void reproduce() {
        this.ecosystem.addCell(new AsexualCell());
        this.status = CellStatus.HUNGRY;
    }
}
