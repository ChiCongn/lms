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

    // Thanh Duy :)
    /*public static Book findBookInDatabase(int bookID) {
        return books.get(bookID); // id is mark from 0 similar index ???
    }*/

//    public static void initialize() {
//        books = BookDataService.loadBooksData();
//    }

    private BookManager() {}

    private static void initialize() {
         books = BookDataService.loadBooksData();
    }
    public static ObservableList<Book> getBooks() {
        if (books == null) initialize();
        return books;
    }

    public static Book getBook(int bookId) {
        if (books == null) initialize();
        return books.get(bookId - 1); // convert the index from a 1-based to a 0-based.
    }

    public static void insertBook(Book book) {
        if (books == null) initialize();
        books.add(book);
    }

    public static void deleteBook(Book book) {
        if (books == null) initialize();
        books.remove(book);
    }

    public static int getNumberOfBooks() {
        if (books == null) initialize();
        return books.size();
    }

}
