package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.user.Client;
import edu.lms.models.user.UserManager;
import edu.lms.services.AlertDialog;
import edu.lms.services.database.UsersDataService;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

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

    @FXML
    private Label selectionClientWarningLabel;

    @FXML
    private Button activateButton;

    @FXML
    private Button suspendButton;

    @FXML
    private Button deleteButton;


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
                        hbox.setOnMouseClicked(mouseEvent -> {
                            if (mouseEvent.getClickCount() == 1) {
                                Client selectedUser = clientsTableView.getSelectionModel().getSelectedItem();
                                if (selectedUser != null) {
                                    switchToClientDetails(selectedUser);
                                }
                            }
                        });
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

        initializeButtonActions();
    }

    private void initializeButtonActions() {
        handleDeleteAction();
        handleSuspendAction();
        handleActivateAction();
    }

    private void handleSuspendAction() {
        suspendButton.setOnAction(event -> {
            Client selectedClient = clientsTableView.getSelectionModel().getSelectedItem();
            if (isSelectClient(selectedClient)) {
                if (selectedClient.getStatus().equals("suspended")) return;
                Alert confirmation = AlertDialog.makeConfirmationAlert("confirmation",
                        "Warning: Suspending this client will revoke their access. Proceed with caution");
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        if (!UsersDataService.suspendUser(selectedClient.getId())) {
                            System.err.println("something is wrong. Can not suspend user!");
                        }
                        System.out.println("suspend");
                    } else {
                        System.out.println("cancel");
                    }
                });
            }
        });
    }

    private void handleActivateAction() {
        activateButton.setOnAction(event -> {
            Client selectedClient = clientsTableView.getSelectionModel().getSelectedItem();
            if (isSelectClient(selectedClient)) {
                if (selectedClient.getStatus().equals("active")) return;
                Alert confirmation = AlertDialog.makeConfirmationAlert("confirmation",
                        "Warning: Activating this client will restore their account. Proceed carefully");
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        if (!UsersDataService.reactivateUser(selectedClient.getId())) {
                            System.err.println("Something is wrong. Can not reactivate user!");
                        }
                        System.out.println("active");
                    } else {
                        System.out.println("cancel");
                    }
                });
            }
        });
    }

    private void handleDeleteAction() {
        deleteButton.setOnAction(event -> {
            Client selectedClient = clientsTableView.getSelectionModel().getSelectedItem();
            if (isSelectClient(selectedClient)) {
                Alert confirmation = AlertDialog.makeConfirmationAlert("confirmation",
                        "Warning: Deleting this client will remove all associated data. Are you sure?");
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        clientsTableView.getItems().remove(selectedClient);
                        if (!UsersDataService.deleteUser(selectedClient.getId())) {
                            System.err.println("Something is wrong. Can not delete user!");
                        }
                    } else {
                        System.out.println("cancel");
                    }
                });
            }
        });
    }

    private boolean isSelectClient(Client selectedClient) {
        if (selectedClient != null) {
            selectionClientWarningLabel.setVisible(false);
            return true;
        } else {
            selectionClientWarningLabel.setVisible(true);

            //hide the label after 2 seconds
            PauseTransition hideLabel = new PauseTransition(Duration.seconds(2));
            hideLabel.setOnFinished(e -> selectionClientWarningLabel.setVisible(false));
            hideLabel.play();
            return false;
        }
    }

    private void switchToClientDetails(Client client) {
        ClientDetailsController clientDetailsController = SceneManager.switchScene(Constants.CLIENTS_DETAILS_VIEW);
        assert clientDetailsController != null;
        clientDetailsController.setClientData(client);
    }

}
