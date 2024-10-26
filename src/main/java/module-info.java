module edu.lms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens edu.lms to javafx.fxml;
    exports edu.lms;
}