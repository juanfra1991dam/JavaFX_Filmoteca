module org.example.javafx_filmoteca {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javafx_filmoteca to javafx.fxml;
    exports org.example.javafx_filmoteca;
}