package com.farhad.example.stockui;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ChartApplication extends Application {

    private ConfigurableApplicationContext applicationContext;


    @Override
    public void start(Stage stage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(stage));    
    }

    @Override
    public void init() throws Exception {
        applicationContext = new SpringApplicationBuilder(StockUiApplication.class).run();
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
        Platform.exit();
    }
    // Now we have our SpringBoot application class which launches our JavaFX Application class, ChartApplication:

    static class StageReadyEvent extends ApplicationEvent  {

        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return ((Stage)getSource());
        }
    }
    
    
}
