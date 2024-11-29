package edu.lms.controllers.client;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.models.search.BookSearch;
import edu.lms.models.search.Trie;
import edu.lms.services.GoogleBooksAPI;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ClientSearchBookController extends DashboardController implements Initializable {
    @FXML
    private ListView<Book> availableInLibrary;

    @FXML
    private ListView<Book> moreOnGoogleBooks;

    @FXML
    private TextField search;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(client.getUsername());
        avatarImage.setImage(new Image(client.getAvatarPath()));
        configureListView(moreOnGoogleBooks);
        configureListView(availableInLibrary);
        search.setOnKeyReleased(this::searchOnLibrary);
        search.setOnKeyPressed(this::searchBookOnGgBook);
    }

    public void searchBookOnGgBook(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("search :(");

            String query = search.getText();
            if (!query.isEmpty()) {
                ObservableList<Book> filteredBooks = GoogleBooksAPI.searchBooks(query);
                moreOnGoogleBooks.setItems(filteredBooks);
                moreOnGoogleBooks.setVisible(!filteredBooks.isEmpty());
            }
        }
    }

    public void searchOnLibrary(KeyEvent keyEvent) {
        if (keyEvent.getEventType() != KeyEvent.KEY_RELEASED) {
            return;
        }
        String filter = search.getText().toLowerCase();
        if (!filter.isEmpty()) {
            // Filter the books based on the search query
            // wow surprised! O(k logN) k is constant
            String[] keywords = filter.split("\\s+");
            List<String> guessedKeywords = new ArrayList<>();
            for (String keyword : keywords) {
                Trie.autocomplete(guessedKeywords, keyword);
            }
            Set<Integer> filteredBookId = BookSearch.searchBooksByKeywords(guessedKeywords, false);
            ObservableList<Book> filteredBooks = BookManager.getFilteredBooks(filteredBookId);

            // O(n*m)
            // n is the number of books.
            // m is the average length of the book titles.
            /*ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(filter)) {
                    filteredBooks.add(book);
                }
            }*/
            availableInLibrary.setItems(filteredBooks);
        }
    }

    private void configureListView(ListView<Book> listView) {
        System.out.println("set config list view");
        listView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label titleText = new Label();
            private final HBox content = new HBox(imageView, titleText);

            {
                imageView.setFitHeight(60);
                imageView.setFitWidth(40);
                content.setSpacing(5);
            }

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setGraphic(null);
                } else {
                    titleText.setText(book.getTitle());
                    imageView.setImage(new Image(book.getCoverImage(), true));
                    setGraphic(content);
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                switchToClientBookDetail(newSelection, "search books");
            }
        });
    }
}
