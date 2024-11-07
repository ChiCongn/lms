package edu.lms.models.book;

import edu.lms.services.database.BookDataService;

import java.util.List;

public class BookManager {
    public static List<Book> books;

    // load all books in database :)
    public void initialize() {
        books = BookDataService.loadBooksData();
    }

    // Thanh Duy :)
    /*public static Book findBookInDatabase(int bookID) {
        return books.get(bookID); // id is mark from 0 similar index ???
    }*/

    public static List<Book> getBooks() {
        return books;
    }
}
