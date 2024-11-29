package edu.lms.controllers.librarian;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.common.SoundManager;
import edu.lms.controllers.login.SignInController;
import edu.lms.models.issue.IssuesManager;
import edu.lms.models.user.Librarian;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class DashboardController {

    protected static Librarian librarian;

    @FXML
    protected Label usernameLabel;

    @FXML
    protected ImageView avatarImage;

    public static Librarian getLibrarian() {
        return librarian;
    }

    public static void setData(Librarian librarian) {
        DashboardController.librarian = librarian;
    }

    public void backToHomeView() {
        SoundManager.playSound("mouse-click.wav");
        LibrarianDashboardController librarianDashboardController = SceneManager.switchScene(Constants.LIBRARIAN_DASHBOARD_VIEW, true);
    }

    public void switchToClientManagementView() {
        SoundManager.playSound("mouse-click.wav");
        ClientsManagementController clientsManagementController = SceneManager.switchScene(Constants.CLIENT_MANAGEMENT_VIEW, true);
    }

    public void switchToBooksManagementView() {
        SoundManager.playSound("mouse-click.wav");
        BooksManagementController booksManagementController = SceneManager.switchScene(Constants.BOOKS_MANAGEMENT_VIEW, true);
    }

    public void logout() {
        SoundManager.playSound("mouse-click.wav");
        SignInController signInController = SceneManager.switchScene(Constants.SIGN_IN_VIEW, false);
    }

    public void switchToIssuesManagementView() {
        SoundManager.playSound("mouse-click.wav");
        IssuesManagementController issuesManagementController = SceneManager.switchScene(Constants.ISSUES_MANAGEMENT_VIEW, true);
    }
}
