package org.example.javafx_filmoteca;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        // Restringe el campo YEAR a solo números, no más de 4 dígitos
        yearField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearField.setText(oldValue);
            } else {
                // Limitar a 4 dígitos
                if (newValue.length() > 4) {
                    yearField.setText(oldValue);
                }
            }
        });

        // Sincroniza slider con label (1 decimal)
        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = Math.round(newValue.doubleValue() * 10.0) / 10.0;
            ratingLabel.setText(String.format("%.1f", value));
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPelicula(Pelicula pelicula, boolean isAdd) {

        this.pelicula = (pelicula != null) ? pelicula : new Pelicula();

        titleField.setText(this.pelicula.getTitle());

        // Establece el año actual por defecto en el campo yearField
        int currentYear = LocalDate.now().getYear();

        yearField.setText(isAdd ? Integer.toString(currentYear) : Integer.toString(this.pelicula.getYear()));
        descriptionArea.setText(this.pelicula.getDescription());

        // Sincroniza slider con label
        double rating = Math.round(this.pelicula.getRating() * 10.0) / 10.0;
        ratingSlider.setValue(rating);
        ratingLabel.setText(String.format("%.1f", rating));

        genreField.setText(String.join(", ", this.pelicula.getGenre()));
        posterField.setText(this.pelicula.getPoster());
        directorField.setText(this.pelicula.getDirector());
    }

    @FXML
    private void handleSave() {
        // Validar campos obligatorios
        if (isAnyRequiredFieldEmpty()) {
            showAlert("Los campos 'Título', 'Año', 'Director' y 'Género' son obligatorios.");
            return;
        }

        try {
            pelicula.setYear(Integer.parseInt(yearField.getText()));
        } catch (NumberFormatException e) {
            showAlert("El año debe ser un número válido.");
            return;
        }

        pelicula.setTitle(titleField.getText().trim());
        pelicula.setDescription(descriptionArea.getText() != null ? descriptionArea.getText().trim() : "");

        double rating = Math.round(ratingSlider.getValue() * 10.0) / 10.0;
        pelicula.setRating(rating);

        List<String> genres = Arrays.stream(genreField.getText().split(","))
                .map(String::trim)
                .filter(g -> !g.isEmpty())
                .collect(Collectors.toList());

        pelicula.setGenre(genres);
        pelicula.setPoster(posterField.getText() != null ? posterField.getText().trim() : "");
        pelicula.setDirector(directorField.getText().trim());

        // Solo asignar ID si es una nueva película
        if (pelicula.getId() == 0) {
            int maxId = DatosFilmoteca.getInstance().getPeliculas().stream()
                    .mapToInt(Pelicula::getId)
                    .max()
                    .orElse(0);
            pelicula.setId(maxId + 1);
        }

        List<Pelicula> peliculas = DatosFilmoteca.getInstance().getPeliculas();

        // Si ya existe, la reemplaza
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getId() == pelicula.getId()) {
                peliculas.set(i, pelicula);
                DatosFilmoteca.getInstance().saveToJson();
                dialogStage.close();
                return;
            }
        }

        // Si es nueva, la agrega
        peliculas.add(pelicula);
        DatosFilmoteca.getInstance().saveToJson();
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metodo para validar campos obligatorios
    private boolean isAnyRequiredFieldEmpty() {
        return isNullOrEmpty(titleField.getText()) ||
                isNullOrEmpty(yearField.getText()) ||
                isNullOrEmpty(directorField.getText()) ||
                isNullOrEmpty(genreField.getText());
    }

    // Metodo para verificar si el texto es null o vacío
    private boolean isNullOrEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
