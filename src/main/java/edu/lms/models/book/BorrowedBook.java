package edu.lms.models.book;

import edu.lms.models.user.Client;

import java.time.LocalDate;

public class BorrowedBook {
    private int borrow_id;
    private Book book; // save pointer which taken from books in BookManager to save memory
    private Client client; // save pointer which taken from clients in UserManager to save memory
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;

    public BorrowedBook(Book book, Client client, LocalDate borrowDate, LocalDate dueDate, String status) {
        this.book = book;
        this.client = client;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public BorrowedBook(int borrow_id, Book book, Client client, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, String status) {
        this.borrow_id = borrow_id;
        this.book = book;
        this.client = client;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getBorrow_id() {
        return borrow_id;
    }

    public void setBorrow_id(int borrow_id) {
        this.borrow_id = borrow_id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
