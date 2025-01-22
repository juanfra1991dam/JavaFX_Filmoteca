package org.example.javafx_filmoteca;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private TableView<Pelicula> peliculasTable;
    @FXML
    private TableColumn<Pelicula, Integer> idColumn;
    @FXML
    private TableColumn<Pelicula, String> titleColumn;
    @FXML
    private TableColumn<Pelicula, Integer> yearColumn;
    @FXML
    private TableColumn<Pelicula, String> directorColumn;
    @FXML
    private TableColumn<Pelicula, Double> ratingColumn;

    @FXML
    private Label titleLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label directorLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private ImageView posterImageView;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        directorColumn.setCellValueFactory(cellData -> cellData.getValue().directorProperty());

        // Para el ratingColumn, se usa el valor Double directamente
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());

        peliculasTable.setItems(DatosFilmoteca.getInstance().getPeliculas());

        peliculasTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> mostrarDetallesPelicula(newValue));
    }

    @FXML
    private void handleAdd() {
        mostrarVentanaEdicion(null);
    }

    @FXML
    private void handleEdit() {
        Pelicula selectedPelicula = peliculasTable.getSelectionModel().getSelectedItem();
        if (selectedPelicula != null) {
            mostrarVentanaEdicion(selectedPelicula);
        } else {
            mostrarAlerta("No se ha seleccionado ninguna película", "Por favor, selecciona una película de la tabla.");
        }
    }

    @FXML
    private void handleDelete() {
        Pelicula selectedPelicula = peliculasTable.getSelectionModel().getSelectedItem();
        if (selectedPelicula != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de borrado");
            alert.setHeaderText("Borrar película");
            alert.setContentText("¿Estás seguro de que deseas borrar la película seleccionada?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    DatosFilmoteca.getInstance().getPeliculas().remove(selectedPelicula);
                }
            });
        } else {
            mostrarAlerta("No se ha seleccionado ninguna película", "Por favor, selecciona una película de la tabla.");
        }
    }

    @FXML
    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de cierre");
        alert.setHeaderText("Cerrar aplicación");
        alert.setContentText("¿Estás seguro de que deseas cerrar la aplicación?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) peliculasTable.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void mostrarVentanaEdicion(Pelicula pelicula) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEditPeliculaView.fxml"));
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(pelicula == null ? "Añadir Película" : "Editar Película");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(peliculasTable.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPelicula(pelicula);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetallesPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            titleLabel.setText(pelicula.getTitle());
            yearLabel.setText(Integer.toString(pelicula.getYear()));
            descriptionLabel.setText(pelicula.getDescription());
            directorLabel.setText(pelicula.getDirector());
            // Formatear el rating a 1 decimal
            ratingLabel.setText(String.format("%.1f", pelicula.getRating()));
            genreLabel.setText(String.join(", ", pelicula.getGenre()));
            posterImageView.setImage(new Image(pelicula.getPoster()));
        } else {
            titleLabel.setText("");
            yearLabel.setText("");
            descriptionLabel.setText("");
            directorLabel.setText("");
            ratingLabel.setText("");
            genreLabel.setText("");
            posterImageView.setImage(null);
        }
    }
}
