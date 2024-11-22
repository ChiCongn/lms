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

    exports edu.lms;
    exports edu.lms.models.book;
    exports edu.lms.models.user;
    exports edu.lms.models.issue;
    exports edu.lms.controllers;
    opens edu.lms.controllers to javafx.fxml;

    exports edu.lms.controllers.login;
    opens edu.lms.controllers.login to javafx.fxml;

    exports edu.lms.controllers.dashboard;
    opens edu.lms.controllers.dashboard to javafx.fxml;

    exports edu.lms.controllers.Client;
    opens edu.lms.controllers.Client to javafx.fxml;

}