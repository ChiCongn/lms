package edu.lms;

import edu.lms.models.book.Book;
import edu.lms.models.user.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final int DEFAULT_BORROW_DURATION_DAYS = 60;
    public static final String SIGN_IN_VIEW = "/edu/lms/fxml/sign-in-view.fxml";
    public static final String SIGNUP_VIEW = "/edu/lms/fxml/sign-up-view.fxml";

    public static final String ADMIN_DASHBOARD_VIEW = "/edu/lms/fxml/dashboard-view.fxml";
    public static final String LIBRARIAN_DASHBOARD_VIEW = "/edu/lms/fxml/librarian-dashboard-view.fxml";
    public static final String CLIENT_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String CLIENT_BOOK_DETAILS_VIEW = "/edu/lms/fxml/client-book-details-view.fxml";

    public static final String FORGOT_PASSWORD_VIEW = "/edu/lms/fxml/forgot-password-view.fxml";
    public static final String TRENDING_DASHBOARD_VIEW = "/edu/lms/fxml/trending-dashboard.fxml";
    public static final String CATEGORIES_VIEW = "/edu/lms/fxml/category-dashboard.fxml";
    public static final String CLIENT_BORROWED_BOOKS_VIEW = "/edu/lms/fxml/client-borrowed-books-view.fxml";
    public static final String READING_DASHBOARD_VIEW = "/edu/lms/fxml/TestingDashBoard.fxml";
    public static final String FAVOURITE_DASHBOARD_VIEW = "/edu/lms/fxml/favourite-books-view.fxml";
    public static final String HISTORY_DASHBOARD_VIEW = "/edu/lms/fxml/history-dashboard.fxml";
    public static final String CLIENT_SEARCH_BOOK_VIEW = "/edu/lms/fxml/client-search-book-view.fxml";
    public static final String GAME_DASHBOARD_VIEW = "/edu/lms/fxml/quiz-game.fxml";

    public static final String CLIENT_MANAGEMENT_VIEW = "/edu/lms/fxml/clients-management-view.fxml";
    public static final String BOOKS_MANAGEMENT_VIEW = "/edu/lms/fxml/books-management-view.fxml";
    public static final String CLIENTS_DETAILS_VIEW = "/edu/lms/fxml/user-details-view.fxml";
    public static final String BOOK_DETAILS_VIEW = "/edu/lms/fxml/book-details-view.fxml";
    public static final String ADD_BOOK_VIEW = "/edu/lms/fxml/add-book-view.fxml";
    public static final String ISSUES_MANAGEMENT_VIEW = "/edu/lms/fxml/issues-management-view.fxml";

    public static final String ISSUE_DETAIL_VIEW = "/edu/lms/fxml/issue-details-view.fxml";

    public static final String VERTICAL_CARD_VIEW = "/edu/lms/fxml/vertical-card.fxml";
    public static final String HORIZONTAL_CARD_VIEW = "/edu/lms/fxml/horizontal-card.fxml";

}
