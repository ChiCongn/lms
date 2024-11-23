package edu.lms.services.database;

import edu.lms.models.issue.Issue;
import edu.lms.models.book.Book;
import edu.lms.models.book.BookManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class IssuesDataService {
    private static final String LOAD_ALL_ISSUES_QUERY = "SELECT * FROM issues";
    private static final String LOAD_ISSUES_QUERY = "SELECT * FORM issues WHERE user_id = ?";
    private static final String ADD_ISSUE_QUERY = "INSERT INTO issues (user_id, book_id, description, reported_date) VALUE (?, ?, ?, ?)";
    private static final String DELETE_ISSUE_QUERY = "DELETE FROM issues WHERE issue_id = ?";


    public static ObservableList<Issue> loadAllIssues() {
        ObservableList<Issue> issues = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_ALL_ISSUES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int issueId = resultSet.getInt("issue_id");
                int clientId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                LocalDate reportedDate = resultSet.getDate("reported_date").toLocalDate();
                LocalDate fixedDate = null;
                Date dbFixedDate = resultSet.getDate("fixed_date");
                if (dbFixedDate != null) {
                    fixedDate = dbFixedDate.toLocalDate();
                }
                String remark = resultSet.getString("remark");

                Book book = BookManager.getBook(bookId);
                //int issueId, Client reporter, Book issueBook, String detail, String status, LocalDate reportedDate, LocalDate fixedDate, String remark
                Issue issue = new Issue(issueId, clientId, book, description, status, reportedDate, fixedDate, remark);
                issues.add(issue);
            }
            System.out.println("load all issues");
        } catch (SQLException e) {
            System.err.println("Error loading all issues: " + e.getMessage());
        }
        return issues;
    }

    public static ObservableList<Issue> loadIssues(int userId) {
        ObservableList<Issue> issues = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_ALL_ISSUES_QUERY)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int issueId = resultSet.getInt("issue_id");
                int bookId = resultSet.getInt("book_id");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                LocalDate reportedDate = resultSet.getDate("reported_date").toLocalDate();
                LocalDate fixedDate = null;
                Date dbFixedDate = resultSet.getDate("fixed_date");
                if (dbFixedDate != null) {
                    fixedDate = dbFixedDate.toLocalDate();
                }
                String remark = resultSet.getString("remark");

                Book book = BookManager.getBook(bookId);
                //int issueId, Client reporter, Book issueBook, String detail, String status, LocalDate reportedDate, LocalDate fixedDate, String remark
                Issue issue = new Issue(issueId, userId, book, description, status, reportedDate, fixedDate, remark);
                issues.add(issue);
            }
            System.out.println("load issues of specific client");
        } catch (SQLException e) {
            System.err.println("Error loading issues of specific client: " + e.getMessage());
        }
        return issues;
    }

    public static boolean addIssue(Issue issue) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ISSUE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            //client_id, book_id, description, reported_date
            statement.setInt(1, issue.getReporterId());
            statement.setInt(2, issue.getIssueBook().getBookId());
            statement.setString(3, issue.getDescription());
            statement.setDate(4, Date.valueOf(issue.getReportedDate()));

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            System.out.println("add an issue into database");
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                issue.setIssueId(generatedId);
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding issue: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteIssue(int issueId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ISSUE_QUERY)) {

            
        } catch (SQLException e) {
            System.err.println("Error deleting issue: " + e.getMessage());
        }
        return false;
    }
}
