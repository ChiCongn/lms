module edu.lms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires com.google.api.client;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;

    requires java.dotenv;

    requires java.sql;

    requires java.mail;
    requires java.smartcardio;
    requires java.desktop;

//    opens edu.lms to javafx.fxml;
//    exports edu.lms;

    opens edu.lms to javafx.fxml;
    opens edu.lms.controllers to javafx.fxml; // Mở gói edu.lms.controllers cho javafx.fxml
    opens edu.lms.controllers.Client to javafx.fxml;
    exports edu.lms;


}