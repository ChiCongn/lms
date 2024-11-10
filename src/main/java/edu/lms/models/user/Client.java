package edu.lms.models.user;

import edu.lms.models.book.Book;
import edu.lms.models.book.BorrowedBook;
import edu.lms.services.database.ClientDataService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Client extends User {
    List<BorrowedBook> borrowedBooks;
    List<BorrowedBook> overdueBooks;
    private BigDecimal outstandingFines;
    private int borrowedBooksCount;
    private int overdueBooksCount;
    private static Client currentClient;

    public Client(int id, String username, String password, String email, String avatarPath, Gender gender) {
        super(id, username, password, email, avatarPath, gender);
    }

    public Client(String username, String password, String email, Gender gender) {
        super(username, password, email, gender);
    }

    public void borrowBook(Book book) {
        for (BorrowedBook borrowedBook : borrowedBooks) {
            if (borrowedBook.getBook().equals(book)) {
                System.out.println("You have already borrowed this book!");
                return;
            }
        }
        BorrowedBook newBorrowedBook = new BorrowedBook(book, LocalDate.now());
        borrowedBooks.add(newBorrowedBook);
        ClientDataService.borrowBook(this.id, book.getBookId());
    }

    public void returnBook(int bookID) {
        borrowedBooks.remove(bookID);
        ClientDataService.returnBook(this.id, bookID);
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<BorrowedBook> getOverdueBooks() {
        return overdueBooks;
    }

    public void setOverdueBooks(List<BorrowedBook> overdueBooks) {
        this.overdueBooks = overdueBooks;
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

    public int getOverdueBooksCount() {
        return overdueBooksCount;
    }

    public void setOverdueBooksCount(int overdueBooksCount) {
        this.overdueBooksCount = overdueBooksCount;
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void setCurrentClient(Client currentClient) {
        Client.currentClient = currentClient;
    }
}
