package com.stockmarket.model; 
public class Stock { 
    private String symbol; 
    private double price; 
    private int shares; 
    private double change; 
    private double changePercent; 
    private int volume; 
    private String lastUpdated; 
    public Stock(String symbol, double price, int shares) { 
        this.symbol = symbol; 
        this.price = price; 
        this.shares = shares; 
        this.change = 0.0; 
        this.changePercent = 0.0; 
        this.volume = 0; 
        this.lastUpdated = ""; 
    } 
    public String getSymbol() { return symbol; } 
    public double getPrice() { return price; } 
    public int getShares() { return shares; } 
    public double getChange() { return change; } 
    public double getChangePercent() { return changePercent; } 
    public int getVolume() { return volume; } 
    public String getLastUpdated() { return lastUpdated; } 
    public void setPrice(double price) { this.price = price; } 
    public void setShares(int shares) { this.shares = shares; } 
    public void setChange(double change) { this.change = change; } 
    public void setChangePercent(double changePercent) { this.changePercent = changePercent; } 
    public void setVolume(int volume) { this.volume = volume; } 
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; } 
    public double getValue() { return price * shares; } 
} 
