package edu.lms.models.user;

import edu.lms.models.book.Book;
import edu.lms.models.book.BorrowedBook;
import edu.lms.services.database.BookDataService;
import edu.lms.services.database.BorrowedBookDataService;
import edu.lms.services.database.ClientDataService;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Client extends User {
    ObservableList<BorrowedBook> borrowedBooks;
    private BigDecimal outstandingFines = BigDecimal.ZERO;
    private int borrowedBooksCount;
    private static Client currentClient;

    public Client(int id, String username, String password, String email, String avatarPath, Gender gender,
                  ObservableList<BorrowedBook> borrowedBooks, BigDecimal outstandingFines) {
        super(id, username, password, email, avatarPath, gender);
        this.borrowedBooks = borrowedBooks;
        this.outstandingFines = outstandingFines;
        borrowedBooksCount = borrowedBooks.size();
    }

    public Client(String username, String password, String email, Gender gender) {
        super(username, password, email, gender);
    }

    public Client(int clientId, String email, String username, String password, String avatarPath, Gender gender) {
        super(clientId, username, password, email, avatarPath, gender);
    }

    public void borrowBook(Book book) {
        // khong biet co can khong nua hay dung cai khac de check :(((( bo vay
        /*for (BorrowedBook borrowedBook : borrowedBooks) {
            if (borrowedBook.getBook().equals(book)) {
                System.out.println("You have already borrowed this book!");
                return;
            }
        }*/
        LocalDate borrowDate = LocalDate.now();
        BorrowedBook newBorrowedBook = new BorrowedBook(book, this, borrowDate, borrowDate.plusMonths(2), "borrowed");
        BorrowedBookDataService.addNewBorrowedBook(newBorrowedBook);
        BookDataService.updateAvailableCopiesOfThisBook(book.getBookId(), -1);
    }

    public void returnBook(int bookID) {
        borrowedBooks.remove(bookID);
    }

    public ObservableList<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ObservableList<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public BigDecimal getOutstandingFines() {
        return outstandingFines;
    }

    public void setOutstandingFines(BigDecimal outstandingFines) {
        this.outstandingFines = outstandingFines;
    }

    public int getBorrowedBooksCount() {
        return borrowedBooksCount;
    }

    public void setBorrowedBooksCount(int borrowedBooksCount) {
        this.borrowedBooksCount = borrowedBooksCount;
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void setCurrentClient(Client currentClient) {
        Client.currentClient = currentClient;
    }
}
