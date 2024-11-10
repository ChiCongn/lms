package edu.lms.models.book;

import edu.lms.services.database.BookDataService;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookManager {
    public static ObservableList<Book> books;

    // load all books in database :)
    public void initialize() {
        books = BookDataService.loadBooksData();
    }

    // Thanh Duy :)
    /*public static Book findBookInDatabase(int bookID) {
        return books.get(bookID); // id is mark from 0 similar index ???
    }*/

    public static ObservableList<Book> getBooks() {
        return books;
    }

    public static Book getBook(int bookId) {
        return books.get(bookId - 1); // convert the index from a 1-based to a 0-based.
    }

}
