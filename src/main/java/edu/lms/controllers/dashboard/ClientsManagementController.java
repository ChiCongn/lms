package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.user.Client;
import edu.lms.models.user.UserManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientsManagementController implements Initializable {
    @FXML
    private TableView<Client> clientsTableView;

    @FXML
    private TableColumn<Client, Integer> idColumn;

    @FXML
    private TableColumn<Client, HBox> usernameColumn;

    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableColumn<Client, Integer> borrowedBooksColumn;

    @FXML
    private TableColumn<Client, BigDecimal> outstandingFinesColumn;

    @FXML
    private TableColumn<Client, String> statusColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("load data for clients table view in client management scene");
        ObservableList<Client> clients = UserManager.getClients();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setGraphic(null);
                } else {
                    Client client = getTableRow().getItem();

                    if (client != null) {
                        ImageView avatar = new ImageView(new Image(client.getAvatarPath()));
                        avatar.setFitWidth(20);
                        avatar.setFitHeight(20);
                        Label username = new Label(client.getUsername());

                        // Style HBox
                        HBox hbox = new HBox(10, avatar, username);
                        hbox.setAlignment(Pos.CENTER_LEFT);

                        setGraphic(hbox);
                    }
                }
            }
        });
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        borrowedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedBooksCount"));
        outstandingFinesColumn.setCellValueFactory(new PropertyValueFactory<>("outstandingFines"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        clientsTableView.setItems(clients);

        clientsTableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                Client selectedUser = clientsTableView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    switchToClientDetails(selectedUser);
                }
            }
        });
    }

    private void switchToClientDetails(Client client) {
        ClientDetailsController clientDetailsController = SceneManager.switchScene(Constants.CLIENTS_DETAILS_VIEW);
        assert clientDetailsController != null;
        clientDetailsController.setClientData(client);
    }

}
