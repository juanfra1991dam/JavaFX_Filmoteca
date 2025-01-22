package org.example.javafx_filmoteca;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class AddEditController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField yearField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Slider ratingSlider;
    @FXML
    private Label ratingLabel;
    @FXML
    private TextField genreField;
    @FXML
    private TextField posterField;
    @FXML
    private TextField directorField;

    private Stage dialogStage;
    private Pelicula pelicula;

    @FXML
    private void initialize() {
        // Agregar el ChangeListener al slider
        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Redondear el valor a 1 decimal
            double value = Math.round(newValue.doubleValue() * 10.0) / 10.0;
            // Mostrar el valor con 1 decimal
            ratingLabel.setText(String.format("%.1f", value));
        });

        // Solo permitir números en el campo YEAR
        yearField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearField.setText(oldValue);  // Restaurar el valor anterior si no es un número
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        if (pelicula != null) {
            titleField.setText(pelicula.getTitle());
            yearField.setText(Integer.toString(pelicula.getYear()));
            descriptionArea.setText(pelicula.getDescription());
            // Redondear el valor de rating antes de asignarlo al slider
            ratingSlider.setValue(Math.round(pelicula.getRating() * 10.0) / 10.0);
            // Mostrar el valor redondeado en el label
            ratingLabel.setText(String.format("%.1f", pelicula.getRating()));
            genreField.setText(String.join(", ", pelicula.getGenre()));
            posterField.setText(pelicula.getPoster());
            directorField.setText(pelicula.getDirector());
        }
    }

    @FXML
    private void handleSave() {
        if (pelicula == null) {
            pelicula = new Pelicula();
        }
        pelicula.setTitle(titleField.getText());
        pelicula.setYear(Integer.parseInt(yearField.getText()));
        pelicula.setDescription(descriptionArea.getText());
        // Redondear el rating a un decimal antes de guardarlo
        pelicula.setRating(Math.round(ratingSlider.getValue() * 10.0) / 10.0);
        pelicula.setGenre(List.of(genreField.getText().split(",")));
        pelicula.setPoster(posterField.getText());
        pelicula.setDirector(directorField.getText());

        if (!DatosFilmoteca.getInstance().getPeliculas().contains(pelicula)) {
            DatosFilmoteca.getInstance().getPeliculas().add(pelicula);
        }

        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
