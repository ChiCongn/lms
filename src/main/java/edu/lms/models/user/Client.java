package edu.lms.models.user;


import java.util.List;

public class Client extends User {
    List<Integer> borrowedBooks;
    List<Integer> overdueBooks;

    public Client(int id, String username, String password, String avatar) {
        super(id, username, password, avatar);
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
