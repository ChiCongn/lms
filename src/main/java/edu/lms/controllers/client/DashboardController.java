package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.controllers.common.SoundManager;
import edu.lms.controllers.login.SignInController;
import edu.lms.models.book.Book;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.user.Client;
import edu.lms.models.user.ClientDataManager;
import edu.lms.services.GoogleBooksAPI;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DashboardController {

    @FXML
    protected Label usernameLabel;

    @FXML
    protected ImageView avatarImage;


    protected static Client client;

    public static void setClientData(Client client) {
        DashboardController.client = client;
        ClientDataManager.initialize(client.getId());
    }

    @FXML
    protected void switchToCategories() {
        SoundManager.playSound("mouse-click.wav");
        CategoriesController categoriesController = SceneManager.switchScene(Constants.CATEGORIES_VIEW, true);
    }

    @FXML
    protected void backToBrowse() {
        SoundManager.playSound("mouse-click.wav");
        ClientDashboardController clientDashboardController = SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW, true);
    }

    @FXML
    protected void switchToFavouriteBookView() {
        SoundManager.playSound("mouse-click.wav");
        FavouriteBooksController favouriteBooksController = SceneManager.switchScene(Constants.FAVOURITE_DASHBOARD_VIEW, true);
    }

    @FXML
    protected void switchToBorrowedBooks() {
        SoundManager.playSound("mouse-click.wav");
        BorrowedBooksController borrowedBooksController = SceneManager.switchScene(Constants.CLIENT_BORROWED_BOOKS_VIEW, true);
    }

    @FXML
    protected void switchToGame() {
        SoundManager.playSound("mouse-click.wav");
        QuizGameController controller = SceneManager.switchScene(Constants.GAME_DASHBOARD_VIEW, true);
    }

    @FXML
    protected void switchToClientBookDetail(Book book, String previousScene) {
        SoundManager.playSound("mouse-click.wav");
        ClientBookDetailsController controller = SceneManager.switchScene(Constants.CLIENT_BOOK_DETAILS_VIEW, true);
        assert controller != null;
        controller.initialize(book, client.getId(), previousScene);
    }

    @FXML
    protected void switchToSearchBook() {
        SoundManager.playSound("mouse-click.wav");
        ClientSearchBookController controller = SceneManager.switchScene(Constants.CLIENT_SEARCH_BOOK_VIEW, true);
    }

    @FXML
    protected void logout() {
        SoundManager.playSound("mouse-click.wav");
        SignInController signInController = SceneManager.switchScene(Constants.SIGN_IN_VIEW, false);
    }
}
