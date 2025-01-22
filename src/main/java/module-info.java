module org.example.javafx_filmoteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    // Abre los paquetes de JavaFX y Gson
    opens org.example.javafx_filmoteca to javafx.fxml, com.google.gson;

    exports org.example.javafx_filmoteca;
}
