package edu.lms.models.user;

import edu.lms.models.book.Book;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.issue.Issue;
import edu.lms.services.database.BorrowedBookDao;
import edu.lms.services.database.ClientDao;
import edu.lms.services.database.IssuesDao;
import javafx.collections.ObservableList;

public class ClientDataManager {
    private static final int LIMIT_RECENT_BOOKS = 10;
    private static ObservableList<Book> favouriteBooks;
    private static ObservableList<Book> recentBooks;
    private static ObservableList<Issue> issues;
    private static ObservableList<BorrowedBook> borrowedBooks;

    public static void initialize(int clientId) {
        favouriteBooks = ClientDao.loadFavouriteBooks(clientId);
        recentBooks = ClientDao.loadRecentBooks(clientId, LIMIT_RECENT_BOOKS);
        borrowedBooks = BorrowedBookDao.loadBorrowedBooks(clientId);
        issues = IssuesDao.loadIssues(clientId);
    }

    public static ObservableList<Book> getFavouriteBooks() {
        return favouriteBooks;
    }

    public static void setFavouriteBooks(ObservableList<Book> favouriteBooks) {
        ClientDataManager.favouriteBooks = favouriteBooks;
    }

    public static ObservableList<Book> getRecentBooks() {
        return recentBooks;
    }

    public static void setRecentBooks(ObservableList<Book> recentBooks) {
        ClientDataManager.recentBooks = recentBooks;
    }

    public static ObservableList<Issue> getIssues() {
        return issues;
    }

    public static void setIssues(ObservableList<Issue> issues) {
        ClientDataManager.issues = issues;
    }

    public static ObservableList<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public static void setBorrowedBooks(ObservableList<BorrowedBook> borrowedBooks) {
        ClientDataManager.borrowedBooks = borrowedBooks;
    }

    public static boolean isFavouriteBook(int bookId) {
        for (Book book : favouriteBooks) {
            if (book.getBookId() == bookId) {
                return true;
            }
        }
        return false;
    }

    public static boolean addFavouriteBook(Book book) {
        if (favouriteBooks.contains(book)) return false;
        favouriteBooks.add(book);
        return true;
    }

    public static void unfavouriteBook(Book book) {
        favouriteBooks.remove(book);
    }

    public static boolean addRecentBook(Book book) {
        if (recentBooks.contains(book)) return false;
        recentBooks.add(book);
        return true;
    }

    public static boolean isBorrowed(int bookId) {
        for (BorrowedBook borrowedBook : borrowedBooks) {
            if (borrowedBook.getBook().getBookId() == bookId) {
                return true;
            }
        }
        return false;
    }

    public static boolean addBorrowedBook(BorrowedBook borrowedBook) {
        if (borrowedBooks.contains(borrowedBook)) return false;
        borrowedBooks.add(borrowedBook);
        return true;
    }

    public static void returnBook(BorrowedBook book) {
        borrowedBooks.remove(book);
    }
}