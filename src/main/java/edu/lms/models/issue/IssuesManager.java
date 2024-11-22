package edu.lms.models.issue;

import edu.lms.models.book.BorrowedBook;
import edu.lms.services.database.BorrowedBookDataService;
import edu.lms.services.database.IssuesDataService;
import javafx.collections.ObservableList;

public class IssuesManager {
    private static ObservableList<Issue> issues;
    private static ObservableList<BorrowedBook> borrowedBooks;

    public static void initialize() {
        issues = IssuesDataService.loadAllIssues();
        borrowedBooks = BorrowedBookDataService.loadAllBorrowedBooks();
    }

    public static ObservableList<Issue> getIssues() {
        return issues;
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
}
