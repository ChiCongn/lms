package edu.lms.models.book;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BorrowedBook {
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean isReturned;
    private BigDecimal fine;

    public BorrowedBook(Book book, LocalDate borrowDate) {
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusMonths(2);
        this.isReturned = false;
        this.fine = BigDecimal.ZERO;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }
}
