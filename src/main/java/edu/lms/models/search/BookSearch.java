package edu.lms.models.search;

import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;
import edu.lms.services.database.BookDao;
import javafx.collections.ObservableList;

import java.util.*;

public class BookSearch {

    private static Map<String, Set<Integer>> invertedIndex;

    private static String previousFilter;

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

    public static Set<Integer> searchBooksByKeywords(List<String> keywords, boolean andSearch) {
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
        Trie.initialize();
        invertedIndex = InvertedIndexDao.loadInvertedIndex();
    }

    /*public static ObservableList<Book> handleFilterChange(String filter) {
        // Split the current filter into keywords
        String[] keywords = filter.trim().split("\\s+");
        List<String> newGuessedKeywords = new ArrayList<>();

        // Determine whether the filter was extended or shortened
        if (filter.startsWith(previousFilter) && !filter.isBlank()) {
            // Extend existing guesses for new keywords
            for (String keyword : keywords) {
                if (keyword.startsWith(previousFilter)) {
                    newGuessedKeywords.addAll(Objects.requireNonNull(Trie.autocomplete(keyword)));
                } else {
                    newGuessedKeywords.add(keyword);
                }
            }
        } else {
            // Recompute guesses for all keywords
            for (String keyword : keywords) {
                newGuessedKeywords.addAll(Objects.requireNonNull(Trie.autocomplete(keyword)));
            }
        }

        // Convert guessed keywords to a set to search for books
        Set<Integer> filteredBookIds = BookSearch.searchBooksByKeywords(newGuessedKeywords, false);

        // Get the filtered books from the BookManager
        ObservableList<Book> filteredBooks = BookManager.getFilteredBooks(filteredBookIds);

        // Cache the current filter and guessed keywords
        previousFilter = filter;

        return filteredBooks;
    }*/

    /*public static void main(String[] args) {
        //invertedIndex = new HashMap<>();
        ObservableList<Book> books = BookDao.loadBooksData();
        for (Book book : books) {
            addBook(book.getBookId(), book.getTitle());
        }
    }*/
}

