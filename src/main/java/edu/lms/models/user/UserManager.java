package edu.lms.models.user;

import edu.lms.services.database.ClientDataService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserManager {
    private static final ObservableList<Client> clients = ClientDataService.loadClientsData();


    public static ObservableList<Client> getClients() {
        return clients;
    }

    public static Client getClient(int clientId) {
        if (clients == null) {
            throw new IllegalStateException("UserManager has not been initialized. Call initialize() first.");
        }
        return clients.get(clientId - 1); // convert the index from a 1-based to a 0-based.
    }
}
