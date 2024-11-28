package edu.lms.controllers.client;

import edu.lms.Constants;
import edu.lms.controllers.SceneManager;
import edu.lms.models.book.Book;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.user.Client;
import edu.lms.models.user.ClientDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
        CategoriesController categoriesController = SceneManager.switchScene(Constants.CATEGORIES_VIEW, true);
    }

    @FXML
    protected void backToBrowse() {
        ClientDashboardController clientDashboardController = SceneManager.switchScene(Constants.CLIENT_DASHBOARD_VIEW, true);
    }

    @FXML
    protected void switchToFavouriteBookView() {
        FavouriteBooksController favouriteBooksController = SceneManager.switchScene(Constants.FAVOURITE_DASHBOARD_VIEW, true);
    }

    @FXML
    protected void switchToBorrowedBooks() {
        BorrowedBooksController borrowedBooksController = SceneManager.switchScene(Constants.CLIENT_BORROWED_BOOKS_VIEW, true);
    }

    @FXML
    protected void switchToGame() {

    }

    @FXML
    protected void switchToClientBookDetail(Book book) {
        ClientBookDetailsController controller = SceneManager.switchScene(Constants.CLIENT_BOOK_DETAILS_VIEW, true);
        assert controller != null;
        controller.initialize(book, client.getId());
    }
}
