package edu.lms.models.book;

import java.time.LocalDate;

public class BorrowedBook {
    private int borrowId;
    private Book book; // save pointer which taken from books in BookManager to save memory
    private int clientId; // save pointer which taken from clients in UserManager to save memory
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;

    public BorrowedBook(Book book, int clientId, LocalDate borrowDate, LocalDate dueDate, String status) {
        this.book = book;
        this.clientId = clientId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public BorrowedBook(int borrowId, Book book, int clientId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, String status) {
        this.borrowId = borrowId;
        this.book = book;
        this.clientId = clientId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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
