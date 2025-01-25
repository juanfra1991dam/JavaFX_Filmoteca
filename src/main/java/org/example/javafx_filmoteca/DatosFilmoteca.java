package org.example.javafx_filmoteca;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DatosFilmoteca {
    private static final String ARCHIVO_JSON = "datos/peliculas.json";
    private static final DatosFilmoteca instance = new DatosFilmoteca();
    private final ObservableList<Pelicula> peliculas;
    private final ObjectMapper objectMapper;

    private DatosFilmoteca() {
        peliculas = FXCollections.observableArrayList();
        objectMapper = new ObjectMapper();
    }

    public static DatosFilmoteca getInstance() {
        return instance;
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void cargarDatos() {
        try {
            File archivo = new File(ARCHIVO_JSON);
            if (!archivo.exists()) {
                return;
            }

            List<Pelicula> lista = objectMapper.readValue(archivo, new TypeReference<>() {
            });
            peliculas.setAll(lista);
        } catch (IOException e) {
            mostrarAlerta("Error al cargar datos", "No se pudo leer el archivo de datos.\n" + e.getMessage());
        }
    }

    public void saveToJson() {
        // Formateamos el json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            // Guardamos el cambio en el archivo JSON
            objectMapper.writeValue(new File(ARCHIVO_JSON), peliculas);
        } catch (IOException e) {
            mostrarAlerta("Error al guardar datos", "No se pudo escribir en el archivo de datos.\n" + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
