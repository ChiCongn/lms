package edu.lms.services.database;

import edu.lms.models.book.Book;
import javafx.collections.ObservableList;

public class LibrarianDataService {
    private static final String LOAD_LIBRARIANS_QUERY = "SELECT * FROM librarians";
    private static final String LOAD_SINGLE_LIBRARIAN_QUERY = "SELECT * FROM clients WHERE librarian_id = ?";
    private static final DatabaseService dbService = DatabaseService.getInstance();

    public LibrarianDataService() {
    }


}
