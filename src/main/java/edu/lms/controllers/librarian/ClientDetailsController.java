package edu.lms.controllers.librarian;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.user.Client;
import edu.lms.models.user.Gender;
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
    private ImageView avatarView;

    @FXML
    private Label emailLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label usernameLabel;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowIdColumn.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        bookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getTitle()));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    }

    // transfer data of specific client from another scene to this scene.
    public void setClientData(Client client) {
        this.client = client;
        idLabel.setText(Integer.toString(client.getId()));
        usernameLabel.setText(client.getUsername());
        emailLabel.setText(client.getEmail());
        genderLabel.setText(Gender.takeGender(client.getGender()));
        avatarView.setImage(new Image(client.getAvatarPath()));
        borrowView.setItems(client.getBorrowedBooks());
    }

    public void backToClientManagement() {
        ClientsManagementController clientsManagementController = SceneManager.switchScene(Constants.CLIENT_MANAGEMENT_VIEW, true);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /*public static void main(String[] args) {
        String imagePath = ClientDetailsController.class.getResource("/edu/lms/images/default_avatar.png").toString();
        System.out.println(imagePath);
    }*/
}
