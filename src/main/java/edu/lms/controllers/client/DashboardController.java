package edu.lms.controllers.client;

import edu.lms.models.user.Client;
import edu.lms.models.user.ClientDataManager;

public class DashboardController {
    protected static Client client;

    public static void setClientData(Client client) {
        DashboardController.client = client;
        ClientDataManager.initialize(client.getId());
    }
}
