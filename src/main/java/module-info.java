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

    exports edu.lms.controllers.common;
    opens edu.lms.controllers.common to javafx.fxml;

    exports edu.lms.controllers.librarian;
    opens edu.lms.controllers.librarian to javafx.fxml;

    exports edu.lms.controllers.client;
    opens edu.lms.controllers.client to javafx.fxml;
    exports edu.lms.models.review;

}