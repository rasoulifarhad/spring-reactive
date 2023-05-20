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

@Component
public class ChartController implements Consumer<StockPrice> {
    
    @FXML
    public LineChart<String,Double> chart;
    private final WebClientStockClient webClientStockClient ;
    private ObservableList<Data<String,Double>>  seriesData =  observableArrayList();

    public ChartController(WebClientStockClient webClientStockClient) {
        this.webClientStockClient = webClientStockClient;
    }

    @FXML
    public void initialize() {
        
        String symbol = "DEMO";
        ObservableList<Series<String,Double>> data = observableArrayList();
        data.add(new Series<>(symbol, seriesData));
        chart.setData(data);

        webClientStockClient.pricesFor(symbol).subscribe(this);

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
