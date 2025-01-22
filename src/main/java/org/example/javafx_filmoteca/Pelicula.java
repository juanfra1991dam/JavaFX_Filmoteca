package org.example.javafx_filmoteca;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;

public class Pelicula {
    private final IntegerProperty id;
    private final StringProperty title;
    private final IntegerProperty year;
    private final ListProperty<String> genre;
    private final StringProperty description;
    private final StringProperty director;
    private final DoubleProperty rating;
    private final StringProperty poster;

    public Pelicula(int id, String title, int year, List<String> genre, String description, String director, double rating, String poster) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.year = new SimpleIntegerProperty(year);
        this.genre = new SimpleListProperty<>(FXCollections.observableArrayList(genre));
        this.description = new SimpleStringProperty(description);
        this.director = new SimpleStringProperty(director);
        this.rating = new SimpleDoubleProperty(rating);
        this.poster = new SimpleStringProperty(poster);
    }

    public Pelicula() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
        this.genre = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.description = new SimpleStringProperty();
        this.director = new SimpleStringProperty();
        this.rating = new SimpleDoubleProperty();
        this.poster = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public List<String> getGenre() {
        return genre.get();
    }

    public ListProperty<String> genreProperty() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre.set(FXCollections.observableArrayList(genre));
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getDirector() {
        return director.get();
    }

    public StringProperty directorProperty() {
        return director;
    }

    public void setDirector(String director) {
        this.director.set(director);
    }

    public double getRating() {
        return rating.get();
    }

    public DoubleProperty ratingProperty() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating.set(rating);
    }

    public String getPoster() {
        return poster.get();
    }

    public StringProperty posterProperty() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster.set(poster);
    }
}
