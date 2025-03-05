package com.example.haarmonika;

import com.example.haarmonika.Database.Repository;
import com.example.haarmonika.Usecase.ScheduledTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class BookingApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Repository repository = new Repository();
        ScheduledTask scheduledTask = new ScheduledTask(repository);
        scheduledTask.start();
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}