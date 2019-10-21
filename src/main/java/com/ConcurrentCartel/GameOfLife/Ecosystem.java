package com.ConcurrentCartel.GameOfLife;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.logging.ConsoleHandler;

import com.ConcurrentCartel.GameOfLife.cells.AsexualCell;
import com.ConcurrentCartel.GameOfLife.cells.Cell;
import com.ConcurrentCartel.GameOfLife.cells.SexualCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ecosystem {
    private static final Logger logger = LoggerFactory.getLogger(Ecosystem.class);

    private boolean started;
    private GameRules rules;
    private ArrayList<Cell> cells;
    private int foodUnits;

    // This CountDownLatch is used to enable all Cell objects to star their execution
    // at the same time.
    private CountDownLatch startLatch;

    public Ecosystem(GameRules gameRules){
        rules = gameRules;
        cells = new ArrayList<Cell>();

        // startLatch is initialized with a counter of the total number of original cells
        // plus 1 for the main thread
        startLatch =
                new CountDownLatch(gameRules.getAsexualCellsStartNumber()
                        + gameRules.getSexualCellsStartNumber() + 1);

        logger.info("Generating original cells...");

        for (int i = 0; i < gameRules.getAsexualCellsStartNumber(); i++){
            cells.add(new AsexualCell(this, startLatch));
        }

        for (int i = 0; i < gameRules.getSexualCellsStartNumber(); i++){
            cells.add(new SexualCell(this, startLatch));
        }

        foodUnits = gameRules.getStartFoodUnits();

        logger.info("{} asexual cells and {} sexual cells were generated" +
                        " inside an ecosystem with {} units of food.",
                rules.getAsexualCellsStartNumber(), rules.getSexualCellsStartNumber(),
                rules.getStartFoodUnits());
    }

    public void start(){
        // The ecosystem should only be started once.
        if (started){
            throw new RuntimeException(
                    new OperationNotSupportedException("The ecosystem has already started."));
        }

        for (Cell cell: cells) {
            cell.start();
        }

        startLatch.countDown();
        try {
            startLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("The simulation has started.");

        //TODO: remove - needed for testing
        try {
            Thread.sleep(2000);
            for (Cell cell :
                    cells) {
                System.out.println(cell.getName() + " " + cell.getState());
            }
        }
        catch (InterruptedException ex){

        }
    }

    Object cellListLock = new Object();

    public void addCell(Cell newCell){
        synchronized (cellListLock){
            cells.add(newCell);
        }
//        throw new RuntimeException(new UnsupportedOperationException("Method not implemented."));
    }

    public Collection<Cell> getCells(){
        synchronized (cellListLock) {
            return Collections.unmodifiableCollection(cells);
        }
    }

    public Collection<SexualCell> getSexualCells() {
        synchronized (cellListLock) {
            Collection<SexualCell> sexualCells = new ArrayList<SexualCell>();
            for (Cell c : cells) {
                if (c instanceof SexualCell) {
                    sexualCells.add((SexualCell) c);
                }
            }
            return Collections.unmodifiableCollection(sexualCells);
        }
    }

    public void removeCell(Cell cell){
        synchronized (cellListLock){
            cells.remove(cell);
        }
        //throw new RuntimeException(new UnsupportedOperationException("Method not implemented."));
    }

    public void addFoodUnit(int amount){
        throw new RuntimeException(new UnsupportedOperationException("Method not implemented."));
    }

    public boolean removeFoodUnit(){
        throw new RuntimeException(new UnsupportedOperationException("Method not implemented."));
    }

    public int getStarveTime(){
        return rules.getStarveTime();
    }

    public int getFullTime(){
        return rules.getStarveTime();
    }
}
