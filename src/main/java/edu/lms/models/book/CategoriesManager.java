package edu.lms.models.book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;
import java.util.stream.Collectors;

public class CategoriesManager {
    static Map<String, ObservableList<Book>> categories;

    public static void initialize() {
        categories = BookManager.getBooks().stream().collect(Collectors.groupingBy(
                Book::getCategories, Collectors.collectingAndThen(
                        Collectors.toList(), FXCollections::observableArrayList)));
    }

    public static Map<String, ObservableList<Book>> getCategories() {
        return categories;
    }
}

