package com.ConcurrentCartel.GameOfLife.cells;

import com.ConcurrentCartel.GameOfLife.Ecosystem;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Cell extends Thread{
    protected static final Logger logger = LoggerFactory.getLogger(Cell.class);

    protected Ecosystem ecosystem;
    protected CellStatus status;
    private int eatCount;

    private CountDownLatch startLatch;

    public Cell (Ecosystem ecosystem){
        this.ecosystem = ecosystem;
        status = CellStatus.HUNGRY;
    }

    // This constructor will be used by Ecosystem to initialize the original specified cells.
    public Cell(Ecosystem ecosystem, CountDownLatch startLatch) {
        this(ecosystem);
        status = CellStatus.MATING; // FOR TESTING
        this.startLatch = startLatch;
    }

    public void run() {
        // Only the original threads that were created in Ecosystem will depend on startLatch.
        // Subsequent cells will not be assigned a startLatch;
        if (startLatch != null) {
            // The counter on startLatch is decremented to notify other threads that
            // the current thread has reached this state and is ready to continue.
            startLatch.countDown();
            // The current thread will wait for the counter on startLatch to reach 0.
            // When that happens, it means that all other cell threads reached this state.
            // Now, all cell threads can start fairly, at the 'same' time.
            try {
                startLatch.await();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return;
            }

        }

        while(true) {
            switch (status){
                case HUNGRY:
                    continue;
//                    if (!eat()){
//                        die();
//                    }
//                    else eatCount++;
//                    if (eatCount == 10){
//                        status = CellStatus.MATING;
//                    }
                case MATING:
                    reproduce();
                case CONTENT:
                    continue;
                case DEAD:
                    break;
                default:
                    logger.error("{} reached an invalid state");
            }
        }
    }

    private boolean eat() {
        logger.info("{} is looking for food", this.getName());
        return ecosystem.removeFoodUnit();
    }

    private void die() {
        status = CellStatus.DEAD;
        int foodUnits = new Random().nextInt(5);
        ecosystem.removeCell(this);
        ecosystem.addFoodUnit(foodUnits);
        logger.info("{} died and left behind {} food units.", this.getName(), foodUnits);
    }

    protected abstract void reproduce();
}
