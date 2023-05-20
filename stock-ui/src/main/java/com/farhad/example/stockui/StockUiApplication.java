package com.farhad.example.stockui;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

@SpringBootApplication
public class StockUiApplication {

	public static void main(String[] args) {
		Application.launch(ChartApplication.class, args);
	}

}
