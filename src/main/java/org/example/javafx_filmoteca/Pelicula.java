package org.example.javafx_filmoteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;

public class Pelicula {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final IntegerProperty year = new SimpleIntegerProperty();
    private final ListProperty<String> genre = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty director = new SimpleStringProperty();
    private final DoubleProperty rating = new SimpleDoubleProperty();
    private final StringProperty poster = new SimpleStringProperty();

    // Constructor
    public Pelicula() {
    }

    // ID
    @JsonProperty("id")
    public int getId() {
        return id.get();
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id.set(id);
    }

    @JsonIgnore
    public IntegerProperty idProperty() {
        return id;
    }

    // Title
    @JsonProperty("title")
    public String getTitle() {
        return title.get();
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title.set(title);
    }

    @JsonIgnore
    public StringProperty titleProperty() {
        return title;
    }

    // Year
    @JsonProperty("year")
    public int getYear() {
        return year.get();
    }

    @JsonProperty("year")
    public void setYear(int year) {
        this.year.set(year);
    }

    @JsonIgnore
    public IntegerProperty yearProperty() {
        return year;
    }

    // Genre
    @JsonProperty("genre")
    public List<String> getGenre() {
        return genre.get();
    }

    @JsonProperty("genre")
    public void setGenre(List<String> genre) {
        this.genre.set(FXCollections.observableArrayList(genre));
    }

    @JsonIgnore
    public ListProperty<String> genreProperty() {
        return genre;
    }

    // Description
    @JsonProperty("description")
    public String getDescription() {
        return description.get();
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description.set(description);
    }

    @JsonIgnore
    public StringProperty descriptionProperty() {
        return description;
    }

    // Director
    @JsonProperty("director")
    public String getDirector() {
        return director.get();
    }

    @JsonProperty("director")
    public void setDirector(String director) {
        this.director.set(director);
    }

    @JsonIgnore
    public StringProperty directorProperty() {
        return director;
    }

    // Rating
    @JsonProperty("rating")
    public double getRating() {
        return rating.get();
    }

    @JsonProperty("rating")
    public void setRating(double rating) {
        this.rating.set(rating);
    }

    @JsonIgnore
    public DoubleProperty ratingProperty() {
        return rating;
    }

    // Poster
    @JsonProperty("poster")
    public String getPoster() {
        return poster.get();
    }

    @JsonProperty("poster")
    public void setPoster(String poster) {
        this.poster.set(poster);
    }

    @JsonIgnore
    public StringProperty posterProperty() {
        return poster;
    }
}
