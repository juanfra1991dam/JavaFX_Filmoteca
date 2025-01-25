package org.example.javafx_filmoteca;

import javafx.collections.FXCollections;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private TextField searchField;

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
        mostrarVentanaEdicion(null, true);
    }

    @FXML
    private void handleEdit() {
        Pelicula selectedPelicula = peliculasTable.getSelectionModel().getSelectedItem();
        if (selectedPelicula != null) {
            mostrarVentanaEdicion(selectedPelicula, false);
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
                    // Eliminamos la película de la lista
                    DatosFilmoteca.getInstance().getPeliculas().remove(selectedPelicula);

                    // Guardamos el cambio en el archivo JSON
                    DatosFilmoteca.getInstance().saveToJson();

                    // También se puede hacer alguna acción extra si es necesario, como actualizar la vista
                    peliculasTable.refresh();
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

    private void mostrarVentanaEdicion(Pelicula pelicula, boolean isAdd) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEditPeliculaView.fxml"));
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(isAdd ? "Añadir Película" : "Editar Película");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(peliculasTable.getScene().getWindow());

            Scene scene = new Scene(page);
            // Cargar la hoja de estilos
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/styles.css")).toExternalForm());

            dialogStage.setScene(scene);

            AddEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPelicula(pelicula, isAdd);

            dialogStage.showAndWait();

            // Si se editó, actualizar los detalles
            peliculasTable.refresh();
            mostrarDetallesPelicula(pelicula);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de edición.");
        }
    }


    void mostrarDetallesPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            titleLabel.setText(pelicula.getTitle());
            yearLabel.setText(Integer.toString(pelicula.getYear()));
            descriptionLabel.setText(pelicula.getDescription());
            directorLabel.setText(pelicula.getDirector());
            ratingLabel.setText(String.format("%.1f", pelicula.getRating()));
            genreLabel.setText(String.join(", ", pelicula.getGenre()));

            // Manejo seguro de la imagen
            try {
                if (pelicula.getPoster() != null && !pelicula.getPoster().isBlank()) {
                    posterImageView.setImage(new Image(pelicula.getPoster(), true));
                } else {
                    posterImageView.setImage(null);
                }
            } catch (Exception e) {
                posterImageView.setImage(null);
            }
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

    // Metodo de búsqueda
    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase().trim();

        if (searchText.isEmpty()) {
            // Si no hay texto en la búsqueda, mostrar todas las películas
            peliculasTable.setItems(DatosFilmoteca.getInstance().getPeliculas());
        } else {
            // Filtrar las películas que contienen el texto de búsqueda en su título
            List<Pelicula> filteredPeliculas = DatosFilmoteca.getInstance().getPeliculas().stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            peliculasTable.setItems(FXCollections.observableList(filteredPeliculas));
        }
    }

}
