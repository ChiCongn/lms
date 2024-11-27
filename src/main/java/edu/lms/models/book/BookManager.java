package edu.lms.models.book;

import edu.lms.services.database.BookDao;
import edu.lms.services.database.BorrowedBookDao;
import edu.lms.services.database.IssuesDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookManager {
    private static ObservableList<Book> books;
    private static Map<String, Integer> borrowedBooksDataByMonth;
    private static Map<String, Integer> issuesDataByMonth;
    private static ObservableList<PieChart.Data> categoriesDistributionData;
    private static List<Book> topChoiceBooks;

    private BookManager() {}

    public static void initialize() {
         books = BookDao.loadBooksData();
         borrowedBooksDataByMonth = BorrowedBookDao.loadBorrowedBooksByMonth();
         issuesDataByMonth = IssuesDao.loadIssueByMonth();
         calculateCategoriesDistribution();
         topChoiceBooks = BookDao.loadTopChoicesBook();
    }
    public static ObservableList<Book> getBooks() {
        if (books == null) initialize();
        return books;
    }

    public static Book getBook(int bookId) {
        int lo = 0, hi = books.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int id = books.get(mid).getBookId();
            if (id == bookId) return books.get(mid);
            else if (id < bookId) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return null;
    }

    public static void insertBook(Book book) {
        if (books == null) initialize();
        books.add(book);
    }

    public static void deleteBook(Book book) {
        if (books == null) initialize();
        books.remove(book);
    }

    public static int getNumberOfBooks() {
        if (books == null) initialize();
        return books.size();
    }

    public static ObservableList<PieChart.Data> getCategoriesDistributionData() {
        return categoriesDistributionData;
    }

    public static Map<String, Integer> getBorrowedBooksDataByMonth() {
        return borrowedBooksDataByMonth;
    }

    public static Map<String, Integer> getIssuesDataByMonth() {
        return issuesDataByMonth;
    }

    public static List<Book> getTopChoiceBooks() {
        return topChoiceBooks;
    }

    public static ObservableList<Book> getFilteredBooks(Set<Integer> searchBooksByKeywords) {
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        for (int id : searchBooksByKeywords) {
            filteredBooks.add(BookManager.getBook(id));
        }
        return filteredBooks;
    }

    private static void calculateCategoriesDistribution() {
        Map<String, Integer> categoryData = BookDao.loadCategoryDistributionData();
        categoriesDistributionData = FXCollections.observableArrayList();

        // Calculate total for percentage
        int total = categoryData.values().stream().mapToInt(Integer::intValue).sum();

        // Populate the list with data and calculate percentage
        categoryData.forEach((category, count) -> {
            double percentage = (count / (double) total) * 100;
            categoriesDistributionData.add(new PieChart.Data(category + " (" + String.format("%.1f%%", percentage) + ")", count));
        });
    }
}
