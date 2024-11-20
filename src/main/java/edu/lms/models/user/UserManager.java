package edu.lms.models.user;

import edu.lms.services.database.UsersDataService;
import javafx.collections.ObservableList;

public class UserManager {
    private static final ObservableList<Client> clients = UsersDataService.loadClientsData();

    private UserManager() {}

    public static ObservableList<Client> getClients() {
        return clients;
    }

    public static int getNumberOfClients() {
        return clients.size();
    }

    public static Client getClient(int clientId) {
        return clients.get(clientId - 1); // convert the index from a 1-based to a 0-based.
    }
}
