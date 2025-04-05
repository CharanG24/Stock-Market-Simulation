package com.stockmarket.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.stockmarket.model.Stock;

public class StockService {
    private static final String API_KEY = "YOUR API KEY";
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private final Gson gson = new Gson();

    public Stock getStockQuote(String symbol) throws Exception {
        String urlStr = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
            BASE_URL, symbol, API_KEY);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JsonObject json = gson.fromJson(response.toString(), JsonObject.class);
        JsonObject quote = json.getAsJsonObject("Global Quote");

        Stock stock = new Stock(
            symbol,
            Double.parseDouble(quote.get("05. price").getAsString()),
            0
        );

        stock.setChange(Double.parseDouble(quote.get("09. change").getAsString()));
        stock.setChangePercent(Double.parseDouble(quote.get("10. change percent").getAsString().replace("%", "")));
        stock.setVolume(Integer.parseInt(quote.get("06. volume").getAsString()));
        stock.setLastUpdated(quote.get("07. latest trading day").getAsString());

        return stock;
    }

    public double getStockPrice(String symbol) throws Exception {
        return getStockQuote(symbol).getPrice();
    }
} 