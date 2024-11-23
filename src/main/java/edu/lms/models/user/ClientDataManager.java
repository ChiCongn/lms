package edu.lms.models.user;

import edu.lms.models.book.Book;
import edu.lms.models.book.BorrowedBook;
import edu.lms.models.issue.Issue;
import edu.lms.services.database.BorrowedBookDataService;
import edu.lms.services.database.ClientDao;
import edu.lms.services.database.IssuesDataService;
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
        borrowedBooks = BorrowedBookDataService.loadBorrowedBooks(clientId);
        issues = IssuesDataService.loadIssues(clientId);
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
}
