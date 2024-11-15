package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.user.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClientDetailsController implements Initializable {
    @FXML
    private ImageView avatar;
    @FXML
    private Label email;
    @FXML
    private Label name;

    @FXML
    private TableView<BorrowedBook> borrowView;

    @FXML
    private TableColumn<BorrowedBook, String> bookColumn;

    @FXML
    private TableColumn<BorrowedBook, Integer> borrowIdColumn;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> borrowDateColumn;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> dueDateColumn;

    @FXML
    private TableColumn<BorrowedBook, String > statusColumn;

    private Client client;

    public void setClientDetails(Client client) {
        this.client = client;
        name.setText(client.getUsername());
        email.setText(client.getEmail());
        borrowView.setItems(client.getBorrowedBooks());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowIdColumn.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        bookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getTitle()));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        String imagePath = getClass().getResource("/edu/lms/images/quynhchi_avatar_path.png").toString();
        avatar.setImage(new Image(imagePath));
    }

    public void backToDashboard() {
        LibrarianDashboardController librarianDashboardController = SceneManager.switchScene(Constants.LIBRARIAN_DASHBOARD_VIEW);
    }

    public static void main(String[] args) {
        String imagePath = ClientDetailsController.class.getResource("/edu/lms/images/quynhchi_avatar_path.png").toString();
        System.out.println(imagePath);
    }
}
