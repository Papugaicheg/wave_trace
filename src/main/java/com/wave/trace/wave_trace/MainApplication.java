package com.wave.trace.wave_trace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage globalStage = null;

    @Override
    public void start(Stage stage) throws IOException {
        globalStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("insert-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Введите размер поля");
        stage.setScene(scene);
        stage.show();

    }
    public static void setScene(Scene scene, String title){
        globalStage.setTitle(title);
        globalStage.setScene(scene);
    }



    public static void main(String[] args) {
        launch();
    }
}