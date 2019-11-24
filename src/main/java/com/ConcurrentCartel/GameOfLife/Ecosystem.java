package com.ConcurrentCartel.GameOfLife;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
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
    private volatile int foodUnits;

    // This CountDownLatch is used to enable all Cell objects to star their execution
    // at the same time.
    private CountDownLatch startLatch;

    //Object foodLock = new Object();
    ReentrantLock foodLock = new ReentrantLock();

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
    }

    Object cellListLock = new Object();

    public void addCell(Cell newCell){
        synchronized (cellListLock){
            cells.add(newCell);
            newCell.start();
        }
        logger.info("{} was born, it is a" +
                (newCell instanceof AsexualCell ?
                        "n asexual cell" : " sexual cell"),
                newCell.getName());
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
    }

    // The food lock should be called whenever more food is added
    public void addFoodUnit(int amount){
        foodLock.lock();
            foodUnits += amount;
        foodLock.unlock();
    }

    // Divide the time until a cell starves into 1 second intervals.
    // If 1 second passes and the foodlock is not acquired,
    // the locking process times out and starts again.
    public boolean removeFoodUnit(){
        int timeout = getStarveTime();
        for(int i = 0; i < timeout; i++) {
            try {
                boolean isLockAcquired = foodLock.tryLock(1, TimeUnit.SECONDS);
                if (isLockAcquired) {
                    if (foodUnits > 0) {
                        foodUnits--;
                        return true;
                    }
                }
            } catch (InterruptedException ex) {
                //TODO: handle properly
                ex.printStackTrace();
            } finally {
                foodLock.unlock();
            }
        }
        return false;
    }

    public int getStarveTime(){
        return rules.getStarveTime();
    }

    public int getFullTime(){
        return rules.getStarveTime();
    }
}
