package com.stockmarket;

import com.stockmarket.gui.MainFrame;
import javax.swing.*;

public class StockMarketSimulator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
} 
