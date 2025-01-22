package org.example.javafx_filmoteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class FilmotecaApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatosFilmoteca.getInstance().cargarDatos();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainView.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
        primaryStage.setTitle("Filmoteca");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
