package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.user.Client;
import edu.lms.models.user.ClientDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DashboardController {

    protected static Client client;

    public static void setClientData(Client client) {
        DashboardController.client = client;
    }

    @FXML
    protected void switchToCategories() {
        //CategoriesController categoriesController = SceneManager.switchScene(Constants.CATEGORIES_VIEW, true);
    }
}
