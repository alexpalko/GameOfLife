package com.ConcurrentCartel.GameOfLife.Gui;

import com.ConcurrentCartel.GameOfLife.Ecosystem;
import com.ConcurrentCartel.GameOfLife.GameRules;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {

    private JTextField foodUnitsField;
    private JTextField fullTimeField;
    private JTextField starveTimeField;
    private JTextField asexualCellsNoField;
    private JTextField sexualCellsNoField;
    private JPanel mainPanel;
    private JButton startSimulationButton;

    private Ecosystem ecosystem;

    public MainForm() {
        startSimulationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int startFood = Integer.parseInt(foodUnitsField.getText());
                    int fullTime = Integer.parseInt(fullTimeField.getText());
                    int starveTime = Integer.parseInt(starveTimeField.getText());
                    int aCellsNo = Integer.parseInt(asexualCellsNoField.getText());
                    int sCellsNo = Integer.parseInt(sexualCellsNoField.getText());
                    GameRules rules = new GameRules(aCellsNo, sCellsNo, startFood, starveTime, fullTime);
                    ecosystem = new Ecosystem(rules);
                    ecosystem.start();
                    startSimulationButton.setEnabled(false);
                }
                catch (Exception ex){
                    // ignore
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game of Life");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
