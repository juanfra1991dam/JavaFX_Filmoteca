package org.example.javafx_filmoteca;

import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class DatosFilmoteca {
    private static DatosFilmoteca instance;
    private ObservableList<Pelicula> peliculas;

    private DatosFilmoteca() {
        peliculas = FXCollections.observableArrayList();
    }

    public static DatosFilmoteca getInstance() {
        if (instance == null) {
            instance = new DatosFilmoteca();
        }
        return instance;
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void cargarDatos() {
        try (FileReader reader = new FileReader("datos/peliculas.json")) {
            // Crear el objeto Gson con los adaptadores registrados
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(IntegerProperty.class, new TypeAdapters.IntegerPropertyAdapter())
                    .registerTypeAdapter(StringProperty.class, new TypeAdapters.StringPropertyAdapter())
                    .registerTypeAdapter(DoubleProperty.class, new TypeAdapters.DoublePropertyAdapter())
                    .registerTypeAdapter(ListProperty.class, new TypeAdapters.ListPropertyAdapter())
                    .create();

            // Deserializar los datos del archivo JSON
            Type listType = new TypeToken<List<Pelicula>>() {}.getType();
            List<Pelicula> lista = gson.fromJson(reader, listType);

            // Agregar las películas deserializadas a la lista observable
            peliculas.addAll(lista);
        } catch (IOException e) {
            mostrarAlerta("Error al cargar datos", e.getMessage());
        }
    }


    public void guardarDatos() {
        try (FileWriter writer = new FileWriter("datos/peliculas.json")) {
            // Crear el objeto Gson con los adaptadores registrados
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(IntegerProperty.class, new TypeAdapters.IntegerPropertyAdapter())
                    .registerTypeAdapter(StringProperty.class, new TypeAdapters.StringPropertyAdapter())
                    .registerTypeAdapter(DoubleProperty.class, new TypeAdapters.DoublePropertyAdapter())
                    .registerTypeAdapter(ListProperty.class, new TypeAdapters.ListPropertyAdapter())
                    .create();

            // Serializar las películas a formato JSON y escribirlas en el archivo
            gson.toJson(peliculas, writer);
        } catch (IOException e) {
            mostrarAlerta("Error al guardar datos", e.getMessage());
        }
    }


    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
