# Stock Market Simulator

A Java-based stock market simulation application that allows users to track and trade stocks using real-time data from the Alpha Vantage API.

## Features

- Real-time stock quotes using Alpha Vantage API
- Portfolio management with buy/sell functionality
- Auto-updating stock prices
- User-friendly graphical interface
- Cash balance tracking
- Portfolio value calculation

## Prerequisites

- Java 11 or higher
- Maven
- Alpha Vantage API key (get one for free at https://www.alphavantage.co/)

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd StockMarketSimulator
```

2. Get an API key from Alpha Vantage:
   - Go to https://www.alphavantage.co/
   - Sign up for a free account
   - Get your API key

3. Update the API key in the code:
   - Open `src/main/java/com/stockmarket/gui/MainFrame.java`
   - Replace `"YOUR_API_KEY"` with your actual Alpha Vantage API key

4. Build the project:
```bash
mvn clean package
```

## Running the Application

Run the application using Maven:
```bash
mvn exec:java -Dexec.mainClass="com.stockmarket.StockMarketSimulator"
```

Or run the generated JAR file:
```bash
java -jar target/stock-market-simulator-1.0-SNAPSHOT.jar
```

## Usage

1. **Search for Stocks**
   - Enter a stock symbol (e.g., AAPL, GOOGL, MSFT) in the search field
   - Click "Search" to get current stock information

2. **Buy Stocks**
   - After searching for a stock, enter the number of shares you want to buy
   - Click "Buy" to purchase the shares
   - The transaction will be processed if you have sufficient funds

3. **Sell Stocks**
   - Select a stock from your portfolio in the right panel
   - Enter the number of shares to sell
   - Click "Sell" to sell the shares

4. **Monitor Your Portfolio**
   - View your cash balance and total portfolio value
   - Track individual stock holdings and their current values
   - See real-time price changes and percentage gains/losses

## Notes

- The application starts with a virtual cash balance of $100,000
- Stock prices are updated every minute
- All transactions are simulated and do not involve real money
- The Alpha Vantage API has rate limits for free accounts

## License

This project is licensed under the MIT License - see the LICENSE file for details. 