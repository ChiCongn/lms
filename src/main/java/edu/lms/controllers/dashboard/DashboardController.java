package edu.lms.controllers.dashboard;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.login.SignInController;
import edu.lms.models.user.Librarian;
import javafx.fxml.FXML;


public class DashboardController {

    protected static Librarian librarian;

    public static Librarian getLibrarian() {
        return librarian;
    }

    public static void setLibrarian(Librarian librarian) {
        DashboardController.librarian = librarian;
    }

    public void backToHomeView() {
        LibrarianDashboardController librarianDashboardController = SceneManager.switchScene(Constants.LIBRARIAN_DASHBOARD_VIEW);
    }

    public void switchToClientManagementView() {
        ClientsManagementController clientsManagementController = SceneManager.switchScene(Constants.CLIENT_MANAGEMENT_VIEW);
    }

    public void switchToBooksManagementView() {
        BooksManagementController booksManagementController = SceneManager.switchScene(Constants.BOOKS_MANAGEMENT_VIEW);
    }

    public void logout() {
        SignInController signInController = SceneManager.switchScene(Constants.SIGN_IN_VIEW);
    }
}
