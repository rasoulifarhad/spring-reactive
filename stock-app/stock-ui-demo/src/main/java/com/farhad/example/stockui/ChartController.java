package com.farhad.example.stockui;

import static javafx.collections.FXCollections.observableArrayList;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.farhad.example.stockclient.StockPrice;
import com.farhad.example.stockclient.WebClientStockClient;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChartController  {
    
    @FXML
    public LineChart<String,Double> chart;
    private final WebClientStockClient webClientStockClient ;
    @FXML
    public void initialize() {
        String symbol1 = "SYMBOL1";
        PriceSubscriber priceSubscriber1 = new PriceSubscriber(symbol1);
        String symbol2 = "SYMBOL2";
        PriceSubscriber priceSubscriber2 = new PriceSubscriber(symbol2);
        
        ObservableList<Series<String,Double>> data = observableArrayList();
        data.add(priceSubscriber1.getSeries());
        data.add(priceSubscriber2.getSeries());
        chart.setData(data);
        
        webClientStockClient
                .pricesFor(symbol1)
                .subscribe(priceSubscriber1);
        webClientStockClient
                .pricesFor(symbol2)
                .subscribe(priceSubscriber2);
    }

    private class PriceSubscriber implements Consumer<StockPrice> {
        @Getter
        private Series<String,Double> series;
        private ObservableList<Data<String,Double>>  seriesData =  observableArrayList();

        public PriceSubscriber(String symbol) {
            series = new Series<>(symbol, seriesData);
        }
        @Override
        public void accept(StockPrice stockPrice) {
            Platform.runLater(() -> 
                        seriesData.add(
                            new Data<String,Double>(
                                                String.valueOf(stockPrice.getTime().getSecond()), 
                                                stockPrice.getPrice())));
        }
    }
}
