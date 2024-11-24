package edu.lms.models.search;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.services.database.BookDao;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BookSearch {

    private static Map<String, Set<Integer>> invertedIndex;

    private BookSearch() {}

    public static Map<String, Set<Integer>> getInvertedIndex() {
        if (invertedIndex == null) initialize();
        return invertedIndex;
    }

    public static void addBook(int bookId, String title) {
        // split the title into words and add them to the inverted index.
        String[] words = title.split("\\s+");
        for (String word : words) {
            word = word.toLowerCase();  // change word to lowercase to easy search
            invertedIndex.putIfAbsent(word, new HashSet<>());
            invertedIndex.get(word).add(bookId);
            System.out.println("try to add keyword in map");
            InvertedIndexDao.addKeywordAndIndexBook(bookId, word);
        }
    }

    // search for books whose titles contain all or any of the given keywords
    public static Set<Integer> searchBooksByKeywords(String[] keywords, boolean andSearch) {
        Set<Integer> result = null;

        for (String keyword : keywords) {
            keyword = keyword.toLowerCase();
            Set<Integer> booksWithKeyword = invertedIndex.getOrDefault(keyword, new HashSet<>());

            if (andSearch) {
                // intersect the result with the current keyword's posting list
                if (result == null) {
                    result = new HashSet<>(booksWithKeyword);
                } else {
                    result.retainAll(booksWithKeyword);
                }
            } else {
                // take the union of the result and the current keyword's posting list
                if (result == null) {
                    result = new HashSet<>(booksWithKeyword);
                } else {
                    result.addAll(booksWithKeyword);
                }
            }
        }
        return result != null ? result : new HashSet<>();
    }

    public static void initialize() {
        invertedIndex = InvertedIndexDao.loadInvertedIndex();
    }

    /*public static void main(String[] args) {
        //invertedIndex = new HashMap<>();
        ObservableList<Book> books = BookDao.loadBooksData();
        for (Book book : books) {
            addBook(book.getBookId(), book.getTitle());
        }
    }*/
}

