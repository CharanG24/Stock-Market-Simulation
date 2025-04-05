package com.stockmarket.model; 
import java.util.HashMap; 
import java.util.Map; 
public class Portfolio { 
    private Map<String, Stock> stocks; 
    private double cashBalance; 
    public Portfolio(double initialCash) { 
        this.stocks = new HashMap<>(); 
        this.cashBalance = initialCash; 
    } 
    public void addStock(Stock stock) { 
        stocks.put(stock.getSymbol(), stock); 
    } 
    public Stock getStock(String symbol) { 
        return stocks.get(symbol); 
    } 
    public double getCashBalance() { return cashBalance; } 
    public void setCashBalance(double cashBalance) { this.cashBalance = cashBalance; } 
    public Map<String, Stock> getStocks() { return stocks; } 
} 
