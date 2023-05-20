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
import lombok.var;

@Component
@RequiredArgsConstructor
public class ChartController  {
    
    @FXML
    public LineChart<String,Double> chart;
    private final WebClientStockClient webClientStockClient ;
    @FXML
    public void initialize() {
        var priceSubscriber1 = new PriceSubscriber("SYMBOL1", webClientStockClient);
        var priceSubscriber2 = new PriceSubscriber("SYMBOL2", webClientStockClient);
        
        ObservableList<Series<String,Double>> data = observableArrayList();
        data.add(priceSubscriber1.getSeries());
        data.add(priceSubscriber2.getSeries());
        chart.setData(data);
        
    }

    private static class PriceSubscriber implements Consumer<StockPrice> {
        @Getter
        private Series<String,Double> series;
        private ObservableList<Data<String,Double>>  seriesData =  observableArrayList();

        public PriceSubscriber(String symbol, WebClientStockClient stockClient) {
            series = new Series<>(symbol, seriesData);
            stockClient.pricesFor(symbol)
                       .subscribe(this);
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
