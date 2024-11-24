package edu.lms.models.issue;

import edu.lms.models.book.BorrowedBook;
import edu.lms.services.database.BorrowedBookDao;
import edu.lms.services.database.FinesDao;
import edu.lms.services.database.IssuesDao;
import javafx.collections.ObservableList;

import java.math.BigDecimal;

public class IssuesManager {
    private static boolean isInitialize;
    private static ObservableList<Issue> issues;
    private static ObservableList<BorrowedBook> borrowedBooks;
    private static BigDecimal totalFines;
    private static int totalBorrowedBook;

    public static void initialize() {
        if (isInitialize) return;
        issues = IssuesDao.loadAllIssues();
        borrowedBooks = BorrowedBookDao.loadAllBorrowedBooks();
        totalFines = FinesDao.calculateTotalFines();
        totalBorrowedBook = BorrowedBookDao.getNumberOfBorrowedBook();
        isInitialize = true;
    }

    public static ObservableList<Issue> getIssues() {
        return issues;
    }

    public static int getNumberOfIssues() {
        return issues.size();
    }

    public static Issue getIssue(int issueId) {
        int lo = 0, hi = issues.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int id = issues.get(mid).getIssueId();
            if (id == issueId) return issues.get(mid);
            else if (id < issueId) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return null;
    }

    public static ObservableList<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public static int getNumberOfBorrowed() {
        return borrowedBooks.size();
    }

    public static BorrowedBook getBorrowedBook(int borrowedId) {
        int lo = 0, hi = borrowedBooks.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int id = borrowedBooks.get(mid).getBorrowId();
            if (id == borrowedId) return borrowedBooks.get(mid);
            else if (id < borrowedId) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return null;
    }

    public static BigDecimal getTotalFines() {
        return totalFines;
    }

    public static void setTotalFines(BigDecimal totalFines) {
        IssuesManager.totalFines = totalFines;
    }

    public static int getTotalBorrowedBook() {
        return totalBorrowedBook;
    }

    public static void setTotalBorrowedBook(int totalBorrowedBook) {
        IssuesManager.totalBorrowedBook = totalBorrowedBook;
    }
}
