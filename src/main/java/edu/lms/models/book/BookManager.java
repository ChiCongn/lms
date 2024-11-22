package edu.lms.models.book;

import edu.lms.services.database.BookDataService;
import edu.lms.services.database.BorrowedBookDataService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BookManager {
    private static ObservableList<Book> books;
    private static Map<String, Integer> borrowedBooksDataByMonth;
    private static ObservableList<PieChart.Data> categoriesDistributionData;
    private static List<Book> topChoiceBooks;

    private BookManager() {}

    public static void initialize() {
         books = BookDataService.loadBooksData();
         borrowedBooksDataByMonth = BorrowedBookDataService.loadBorrowedBooksByMonth();
         calculateCategoriesDistribution();
        topChoiceBooks = BookDataService.loadTopChoicesBook();
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

    private static void calculateCategoriesDistribution() {
        Map<String, Integer> categoryData = BookDataService.loadCategoryDistributionData();
        categoriesDistributionData = FXCollections.observableArrayList();

        // Calculate total for percentage
        int total = categoryData.values().stream().mapToInt(Integer::intValue).sum();

        // Populate the list with data and calculate percentage
        categoryData.forEach((category, count) -> {
            double percentage = (count / (double) total) * 100;
            categoriesDistributionData.add(new PieChart.Data(category + " (" + String.format("%.1f%%", percentage) + ")", count));
        });
    }

    public static Map<String, Integer> getBorrowedBooksDataByMonth() {
        return borrowedBooksDataByMonth;
    }

    public static List<Book> getTopChoiceBooks() {
        return topChoiceBooks;
    }
}
