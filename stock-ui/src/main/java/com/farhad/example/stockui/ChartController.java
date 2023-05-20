package com.farhad.example.stockui;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.farhad.example.stockclient.StockPrice;
import com.farhad.example.stockclient.WebClientStockClient;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

@Component
public class ChartController implements Consumer<StockPrice> {
    
    @FXML
    public LineChart<String,Double> chart;
    private final WebClientStockClient webClientStockClient ;
    private ObservableList<Data<String,Double>>  seriesData =  FXCollections.observableArrayList();

    public ChartController(WebClientStockClient webClientStockClient) {
        this.webClientStockClient = webClientStockClient;
    }

    @FXML
    public void initialize() {
        ObservableList<XYChart.Series<String,Double>> data = FXCollections.observableArrayList();
        data.add(new XYChart.Series<>(seriesData));
        chart.setData(data);

        webClientStockClient.pricesFor("DEMO").subscribe(this);

    }

    @Override
    public void accept(StockPrice stockPrice) {
        Platform.runLater(() -> 
                    seriesData.add(
                        new XYChart.Data<String,Double>(
                                            String.valueOf(stockPrice.getTime().getSecond()), 
                                            stockPrice.getPrice())));
    }

    
}
