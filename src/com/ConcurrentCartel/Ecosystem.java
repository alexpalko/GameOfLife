package com.ConcurrentCartel;

import com.ConcurrentCartel.cells.Cell;
import jdk.jshell.spi.ExecutionControl;
import java.util.ArrayList;
import java.util.List;

public class Ecosystem {
    private static Ecosystem instance;
    private List<Cell> cells;
    private int foodUnits;

    public Ecosystem(){
        cells = new ArrayList<Cell>();
        instance = this;
    }

    public static Ecosystem getInstance(){
        return instance == null ? new Ecosystem() : instance;
    }

    public void addCell(Cell cell){
        throw new RuntimeException(new ExecutionControl.NotImplementedException(""));
    }

    public void removeCell(Cell cell){
        throw new RuntimeException(new ExecutionControl.NotImplementedException(""));
    }

    public void addFoodUnit(int amount){
        throw new RuntimeException(new ExecutionControl.NotImplementedException(""));
    }

    public boolean removeFoodUnit(){
        throw new RuntimeException(new ExecutionControl.NotImplementedException(""));
    }

    public boolean findMate() {
        throw new RuntimeException(new ExecutionControl.NotImplementedException(""));
    }
}
