package edu.lms.models.user;

import edu.lms.services.database.UsersDataService;
import javafx.collections.ObservableList;

public class UserManager {
    private static ObservableList<Client> clients;

    private UserManager() {}

    public static void initialize() {
        clients = UsersDataService.loadClientsData();
    }

    public static ObservableList<Client> getClients() {
        return clients;
    }

    public static int getNumberOfClients() {
        return clients.size();
    }

    public static Client getClient(int clientId) {
        int lo = 0, hi = clients.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int currentId = clients.get(mid).id;
            if (currentId == clientId) return clients.get(mid);
            else if (currentId < clientId) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return null;
    }
}
