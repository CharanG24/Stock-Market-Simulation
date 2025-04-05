package com.stockmarket.gui;

import com.stockmarket.model.Portfolio;
import com.stockmarket.model.Stock;
import com.stockmarket.service.StockService;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainFrame extends JFrame {
    private final Portfolio portfolio;
    private final StockService stockService;
    private final JTextField symbolField;
    private final JTextField sharesField;
    private final JEditorPane portfolioArea;
    private final JLabel cashLabel;
    private final JEditorPane stockInfoArea;
    
    // Color scheme
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final Color PANEL_COLOR = new Color(255, 255, 255);
    private static final Color ACCENT_COLOR = new Color(41, 128, 185);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color POSITIVE_COLOR = new Color(46, 204, 113);
    private static final Color NEGATIVE_COLOR = new Color(231, 76, 60);

    public MainFrame() {
        portfolio = new Portfolio(100000.0);
        stockService = new StockService();
        setTitle("Stock Market Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));

        // Input Panel (North)
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(PANEL_COLOR);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Style the input fields
        symbolField = createStyledTextField(10);
        sharesField = createStyledTextField(5);
        
        // Create styled buttons
        JButton searchButton = createStyledButton("Search", ACCENT_COLOR);
        JButton buyButton = createStyledButton("Buy", POSITIVE_COLOR);
        JButton sellButton = createStyledButton("Sell", NEGATIVE_COLOR);

        // Add components with labels
        inputPanel.add(createStyledLabel("Symbol:"));
        inputPanel.add(symbolField);
        inputPanel.add(createStyledLabel("Shares:"));
        inputPanel.add(sharesField);
        inputPanel.add(searchButton);
        inputPanel.add(buyButton);
        inputPanel.add(sellButton);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBackground(BACKGROUND_COLOR);
        
        // Stock Info Area (Left)
        stockInfoArea = createStyledTextArea();
        JScrollPane stockScrollPane = createStyledScrollPane(stockInfoArea, "Stock Information");
        
        // Portfolio Area (Right)
        portfolioArea = createStyledTextArea();
        JScrollPane portfolioScrollPane = createStyledScrollPane(portfolioArea, "Portfolio");

        centerPanel.add(stockScrollPane);
        centerPanel.add(portfolioScrollPane);

        // Cash Label (South)
        cashLabel = createStyledLabel("Cash Balance: $100,000.00");
        cashLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(PANEL_COLOR);
        southPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        southPanel.add(cashLabel, BorderLayout.CENTER);

        // Add all panels to frame with padding
        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Add action listeners
        searchButton.addActionListener(e -> searchStock());
        buyButton.addActionListener(e -> buyStock());
        sellButton.addActionListener(e -> sellStock());

        updatePortfolioDisplay();
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JEditorPane createStyledTextArea() {
        JEditorPane area = new JEditorPane();
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setEditable(false);
        area.setBackground(PANEL_COLOR);
        area.setForeground(TEXT_COLOR);
        return area;
    }

    private JScrollPane createStyledScrollPane(JEditorPane area, String title) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                title,
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                ACCENT_COLOR
            ),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        scrollPane.setBackground(PANEL_COLOR);
        return scrollPane;
    }

    private void searchStock() {
        try {
            String symbol = symbolField.getText().toUpperCase();
            Stock stock = stockService.getStockQuote(symbol);
            updateStockInfoDisplay(stock);
        } catch (Exception ex) {
            showErrorDialog("Error fetching stock price: " + ex.getMessage());
        }
    }

    private void updateStockInfoDisplay(Stock stock) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Symbol: %s\n\n", stock.getSymbol()));
        sb.append(String.format("Price: $%.2f\n", stock.getPrice()));
        
        // Color the change based on positive/negative
        String changeColor = stock.getChange() >= 0 ? 
            String.format("<font color='#%02X%02X%02X'>", POSITIVE_COLOR.getRed(), POSITIVE_COLOR.getGreen(), POSITIVE_COLOR.getBlue()) :
            String.format("<font color='#%02X%02X%02X'>", NEGATIVE_COLOR.getRed(), NEGATIVE_COLOR.getGreen(), NEGATIVE_COLOR.getBlue());
        
        sb.append(String.format("Change: %s$%.2f (%.2f%%)</font>\n", 
            changeColor, stock.getChange(), stock.getChangePercent()));
        
        sb.append(String.format("Volume: %,d\n", stock.getVolume()));
        sb.append(String.format("Last Updated: %s", stock.getLastUpdated()));
        
        stockInfoArea.setContentType("text/html");
        stockInfoArea.setText("<html>" + sb.toString().replace("\n", "<br>") + "</html>");
    }

    private void buyStock() {
        try {
            String symbol = symbolField.getText().toUpperCase();
            int shares = Integer.parseInt(sharesField.getText());
            Stock quote = stockService.getStockQuote(symbol);
            double totalCost = quote.getPrice() * shares;

            if (totalCost > portfolio.getCashBalance()) {
                showErrorDialog("Insufficient funds!");
                return;
            }

            Stock stock = portfolio.getStock(symbol);
            if (stock == null) {
                stock = new Stock(symbol, quote.getPrice(), shares);
                portfolio.addStock(stock);
            } else {
                stock.setShares(stock.getShares() + shares);
                stock.setPrice(quote.getPrice());
            }

            portfolio.setCashBalance(portfolio.getCashBalance() - totalCost);
            updatePortfolioDisplay();

            showSuccessDialog(String.format("Bought %d shares of %s at $%.2f each", 
                shares, symbol, quote.getPrice()));
        } catch (Exception ex) {
            showErrorDialog("Error buying stock: " + ex.getMessage());
        }
    }

    private void sellStock() {
        try {
            String symbol = symbolField.getText().toUpperCase();
            int shares = Integer.parseInt(sharesField.getText());
            Stock stock = portfolio.getStock(symbol);

            if (stock == null || stock.getShares() < shares) {
                showErrorDialog("Insufficient shares!");
                return;
            }

            Stock quote = stockService.getStockQuote(symbol);
            double totalValue = quote.getPrice() * shares;

            stock.setShares(stock.getShares() - shares);
            if (stock.getShares() == 0) {
                portfolio.getStocks().remove(symbol);
            } else {
                stock.setPrice(quote.getPrice());
            }

            portfolio.setCashBalance(portfolio.getCashBalance() + totalValue);
            updatePortfolioDisplay();

            showSuccessDialog(String.format("Sold %d shares of %s at $%.2f each", 
                shares, symbol, quote.getPrice()));
        } catch (Exception ex) {
            showErrorDialog("Error selling stock: " + ex.getMessage());
        }
    }

    private void updatePortfolioDisplay() {
        StringBuilder sb = new StringBuilder();
        double totalValue = portfolio.getCashBalance();

        for (Map.Entry<String, Stock> entry : portfolio.getStocks().entrySet()) {
            Stock stock = entry.getValue();
            try {
                Stock quote = stockService.getStockQuote(stock.getSymbol());
                stock.setPrice(quote.getPrice());
                stock.setChange(quote.getChange());
                stock.setChangePercent(quote.getChangePercent());
                double value = stock.getValue();
                totalValue += value;
                
                sb.append(String.format("<b>%s</b><br>", stock.getSymbol()));
                sb.append(String.format("Shares: %d<br>", stock.getShares()));
                sb.append(String.format("Price: $%.2f<br>", stock.getPrice()));
                sb.append(String.format("Value: $%.2f<br>", value));
                
                String changeColor = stock.getChange() >= 0 ? 
                    String.format("<font color='#%02X%02X%02X'>", POSITIVE_COLOR.getRed(), POSITIVE_COLOR.getGreen(), POSITIVE_COLOR.getBlue()) :
                    String.format("<font color='#%02X%02X%02X'>", NEGATIVE_COLOR.getRed(), NEGATIVE_COLOR.getGreen(), NEGATIVE_COLOR.getBlue());
                
                sb.append(String.format("Change: %s$%.2f (%.2f%%)</font><br><br>", 
                    changeColor, stock.getChange(), stock.getChangePercent()));
            } catch (Exception e) {
                sb.append(String.format("<b>%s</b><br>", stock.getSymbol()));
                sb.append(String.format("Shares: %d<br>", stock.getShares()));
                sb.append("<font color='red'>Error fetching price</font><br><br>");
            }
        }

        sb.append(String.format("<b>Total Portfolio Value: $%.2f</b>", totalValue));
        portfolioArea.setContentType("text/html");
        portfolioArea.setText("<html>" + sb.toString() + "</html>");
        cashLabel.setText(String.format("Cash Balance: $%.2f", portfolio.getCashBalance()));
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
} 