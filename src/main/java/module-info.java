module edu.lms {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.dotenv;

    requires org.controlsfx.controls;

    requires okhttp3;
    requires com.google.gson;

    requires java.sql;
    requires mysql.connector.j;

    requires java.mail;
    requires java.base;

    opens edu.lms to javafx.fxml;
    exports edu.lms;
    exports edu.lms.controllers;
    opens edu.lms.controllers to javafx.fxml;
}