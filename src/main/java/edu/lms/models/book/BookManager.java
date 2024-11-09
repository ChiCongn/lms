package edu.lms.models.book;

import edu.lms.services.database.BookDataService;
import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.util.List;

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
}
