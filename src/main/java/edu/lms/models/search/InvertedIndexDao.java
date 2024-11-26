package edu.lms.models.search;

import edu.lms.Constants;
import edu.lms.services.database.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InvertedIndexDao {
    private static final String LOAD_INVERTED_INDEX = "SELECT book_id, keyword FROM book_keyword_index bki JOIN book_keywords bk ON bki.keyword_id = bk.keyword_id";

    private static final String CHECK_KEYWORD_EXISTED = "SELECT keyword_id FROM book_keywords WHERE keyword = ?";
    private static final String ADD_KEYWORD_INDEX = "INSERT INTO book_keywords (keyword) VALUES (?)";
    private static final String UPDATE_FREQUENCY_QUERY = "UPDATE book_keyword_index SET frequency = frequency + 1 WHERE book_id = ? AND keyword_id = ?";

    public static Map<String, Set<Integer>> loadInvertedIndex() {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_INVERTED_INDEX)) {

            ResultSet rs = statement.executeQuery();
            System.out.println();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String keyword = rs.getString("keyword");
                Trie.insert(keyword);
                invertedIndex.computeIfAbsent(keyword, k -> new HashSet<>()).add(bookId);
            }
        } catch (SQLException e) {
            System.err.println("Error loading inverted index: " + e.getMessage());
        }
        return invertedIndex;
    }

    public static void addKeywordAndIndexBook(int bookId, String keyword) {
        int keywordId = checkIfKeywordExisted(keyword);
        if (keywordId == -1) {
            // If keyword does not exist, add it to the book_keywords table
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement psAddKeyword = connection.prepareStatement(ADD_KEYWORD_INDEX, Statement.RETURN_GENERATED_KEYS)) {

                psAddKeyword.setString(1, keyword);
                psAddKeyword.executeUpdate();

                System.out.println("insert keyword into database");
                ResultSet generatedKeys = psAddKeyword.getGeneratedKeys();
                if (generatedKeys.next()) {
                    keywordId = generatedKeys.getInt(1);
                }

            } catch (SQLException e) {
                System.err.println("Error add keyword: " + e.getMessage());
            }
        }

        updateFrequency(bookId, keywordId);
    }

    public static int checkIfKeywordExisted(String keyword) {
        int keywordId = -1;
        // Check if the keyword already exists
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement psCheckKeyword = connection.prepareStatement(CHECK_KEYWORD_EXISTED)) {

            System.out.println("checking if keyword existed");
            psCheckKeyword.setString(1, keyword);
            ResultSet rs = psCheckKeyword.executeQuery();
            if (rs.next()) {
                keywordId = rs.getInt("keyword_id");
            }

        } catch (SQLException e) {
            System.err.println("Error checking keyword existed: " + e.getMessage());
        }
        return keywordId;
    }

    private static void updateFrequency(int bookId, int keywordId) {
        // Update the frequency in book_keyword_index table if the pair exists, or insert it if it does not exist
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement psUpdateFrequency = connection.prepareStatement(UPDATE_FREQUENCY_QUERY)) {
            psUpdateFrequency.setInt(1, bookId);
            psUpdateFrequency.setInt(2, keywordId);
            int rowsUpdated = psUpdateFrequency.executeUpdate();
            System.out.println("insert keyword into database");
            // If no rows were updated (i.e., the pair doesn't exist), insert it into book_keyword_index
            if (rowsUpdated == 0) {
                String insertQuery = "INSERT INTO book_keyword_index (book_id, keyword_id, frequency) VALUES (?, ?, 1)";
                try (PreparedStatement psInsertIndex = connection.prepareStatement(insertQuery)) {
                    psInsertIndex.setInt(1, bookId);
                    psInsertIndex.setInt(2, keywordId);
                    psInsertIndex.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating keyword frequency: " + e.getMessage());
        }
    }

}
