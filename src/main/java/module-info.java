module org.example.javafx_filmoteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    opens org.example.javafx_filmoteca to com.fasterxml.jackson.databind, javafx.fxml;

    exports org.example.javafx_filmoteca;
}
