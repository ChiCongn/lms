package edu.lms.models.user;

import edu.lms.services.database.ClientDataService;

import java.util.List;

public class Client extends User {
    List<Integer> borrowedBooks;
    List<Integer> overdueBooks;

    public Client(int id, String username, String password, String avatar) {
        super(id, username, password, avatar);
    }

    public void borrowBook(int bookID) {
        if (borrowedBooks.contains(bookID)) {
            System.out.println("You had borrowed this book!");
            return;
        }
        borrowedBooks.add(bookID);
        ClientDataService.borrowBook(this.ID, bookID);
    }

    public void returnBook(int bookID) {
        borrowedBooks.remove(bookID);
        ClientDataService.returnBook(this.ID, bookID);
    }

    public List<Integer> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Integer> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<Integer> getOverdueBooks() {
        return overdueBooks;
    }

    public void setOverdueBooks(List<Integer> overdueBooks) {
        this.overdueBooks = overdueBooks;
    }
}
