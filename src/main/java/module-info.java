module edu.lms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    
    requires com.google.api.client;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;

    opens edu.lms to javafx.fxml;
    exports edu.lms;
}