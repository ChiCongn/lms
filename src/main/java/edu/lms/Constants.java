package edu.lms;

import edu.lms.models.book.Book;
import edu.lms.models.user.Client;
import edu.lms.models.user.User;
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
    public static final String SIGN_IN_VIEW = "/edu/lms/fxml/sign-in-view.fxml";
    public static final String SIGNUP_VIEW = "/edu/lms/fxml/sign-up-view.fxml";

    public static final String ADMIN_DASHBOARD_VIEW = "/edu/lms/fxml/dashboard-view.fxml";
    public static final String LIBRARIAN_DASHBOARD_VIEW = "/edu/lms/fxml/librarian-dashboard-view.fxml";
    public static final String CLIENT_DASHBOARD_VIEW = "/edu/lms/fxml/client-dashboard-view.fxml";
    public static final String TRENDING_DASHBOARD_VIEW = "/edu/lms/fxml/trending-dashboard.fxml";
    public static final String DASHBOARD_VIEW = "/edu/lms/fxml/TestingDashBoard.fxml";
    public static final String CATEGORY_DASHBOARD_VIEW = "/edu/lms/fxml/category-dashboard.fxml";
    public static final String READING_DASHBOARD_VIEW = "/edu/lms/fxml/TestingDashBoard.fxml";
    public static final String FAVOURITE_DASHBOARD_VIEW = "/edu/lms/fxml/TestingDashBoard.fxml";
    public static final String HISTORY_DASHBOARD_VIEW = "/edu/lms/fxml/history-dashboard.fxml";
    public static final String GAME_DASHBOARD_VIEW = "/edu/lms/fxml/quiz-game.fxml";

    public static final String CLIENT_MANAGEMENT_VIEW = "/edu/lms/fxml/clients-management-view.fxml";
    public static final String BOOKS_MANAGEMENT_VIEW = "/edu/lms/fxml/books-management-view.fxml";
    public static final String CLIENTS_DETAILS_VIEW = "/edu/lms/fxml/user-details-view.fxml";
    public static final String BOOK_DETAILS_VIEW = "/edu/lms/fxml/book-details-view.fxml";




    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchScene(String fxmlPath, MouseEvent event, Stage stage, Scene scene, Parent root ) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Book> recommendedBooks = FXCollections.observableArrayList();
    public static ObservableList<Book> recentlyAddedBooks = FXCollections.observableArrayList();
    public static ObservableList<Book> booksByCategory = FXCollections.observableArrayList();

    public static List<Book> recommended = new ArrayList<>();
    public static List<Book> recentlyAdded = new ArrayList<>();
    public static List<Book> books = new ArrayList<>();
    public static List<Client> users = new ArrayList<>();

    public List<Client> getUsers() {
        List<Client> users = new ArrayList<>();
        Client user = new Client("kirito");
        users.add(user);

        Client user1 = new Client("Congdeptrai01");
        users.add(user1);

        Client user2 = new Client("Congdeptrai02");
        users.add(user2);

        Client user3 = new Client("Duysech69");
        users.add(user3);

        Client user4 = new Client("Datmaniac");
        users.add(user4);

        return users;
    }

    public List<edu.lms.models.book.Book> recentlyAdded() {
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("RICH DAD\nPOOR DAD");
        book.setCoverImage("/edu/lms/images/Books/rich_dad_poor_dad.jpg");
        book.setAuthors("Robert Kiyosaki");
        book.setDescription("abcs");

        book.setPublishedYear("111");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("MAN'S SEARCHING\n FOR MEANING");
        book1.setCoverImage("/edu/lms/images/Books/man's searching for meaning.jpg");
        book1.setAuthors("Viktor Frankl");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("THE STOICISM'S\n MIND");
        book2.setCoverImage("/edu/lms/images/Books/stoicism.jpg");
        book2.setAuthors("Nancy Sherman");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("THE STORYTELLER'S\n SECRET");
        book3.setCoverImage("/edu/lms/images/Books/storyteller's secrete.jpg");
        book3.setAuthors("Carmine Gallo");
        ls.add(book3);

        return ls;
    }

    public List<edu.lms.models.book.Book> books(){
        List<edu.lms.models.book.Book> ls = new ArrayList<>();
        edu.lms.models.book.Book book = new edu.lms.models.book.Book();
        book.setTitle("HARRY POTTER");
        book.setCoverImage("/edu/lms/images/Books/HarryPotter.jpg");
        book.setAuthors("J.K.Rowling");
        ls.add(book);

        edu.lms.models.book.Book book1 = new edu.lms.models.book.Book();
        book1.setTitle("HOME");
        book1.setCoverImage("/edu/lms/images/Books/Home.jpg");
        book1.setAuthors("Lisa Allen");
        ls.add(book1);

        edu.lms.models.book.Book book2 = new edu.lms.models.book.Book();
        book2.setTitle("The Dark Side\n of The Mirror");
        book2.setCoverImage("/edu/lms/images/Books/TheDarkSideOfMirror.jpg");
        book2.setAuthors("Christopher Murphy");
        ls.add(book2);

        edu.lms.models.book.Book book3 = new edu.lms.models.book.Book();
        book3.setTitle("The UnderStory");
        book3.setCoverImage("/edu/lms/images/Books/TheUnderStory.jpg");
        book3.setAuthors("Saner Sangsuk");
        ls.add(book3);

        edu.lms.models.book.Book book4 = new edu.lms.models.book.Book();
        book4.setTitle("Educated");
        book4.setCoverImage("/edu/lms/images/Books/Educated.jpg");
        book4.setAuthors("Tara Westover");
        ls.add(book4);

        edu.lms.models.book.Book book5 = new edu.lms.models.book.Book();
        book5.setTitle("A million to one");
        book5.setCoverImage("/edu/lms/images/Books/a million to one.jpg");
        book5.setAuthors("Tony Faggioli");
        ls.add(book5);

        edu.lms.models.book.Book book6 = new edu.lms.models.book.Book();
        book6.setTitle("To Kill a Mockingbird");
        book6.setCoverImage("/edu/lms/images/Books/toKillaMockBird.jpg");
        book6.setAuthors("Harper Lee");
        ls.add(book6);

        edu.lms.models.book.Book book7 = new edu.lms.models.book.Book();
        book7.setTitle("Frankenstein");
        book7.setCoverImage("/edu/lms/images/Books/Frankenstein.jpg");
        book7.setAuthors("Marry Shelley");
        ls.add(book7);

        edu.lms.models.book.Book book8 = new edu.lms.models.book.Book();
        book8.setTitle("Beloved");
        book8.setCoverImage("/edu/lms/images/Books/Beloved.jpg");
        book8.setAuthors("Toni Morrison");
        ls.add(book8);


        return ls;
    }

}
