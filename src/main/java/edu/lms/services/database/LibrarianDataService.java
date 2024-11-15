package edu.lms.services.database;

import edu.lms.models.book.Book;
import javafx.collections.ObservableList;

public class LibrarianDataService {
    private static final String LOAD_LIBRARIANS_QUERY = "SELECT * FROM librarians";
    private static final String LOAD_A_SPECIFIC_LIBRARIAN_QUERY = "SELECT * FROM librarians WHERE librarian_id = ?";
    private static final DatabaseService dbService = DatabaseService.getInstance();

    public LibrarianDataService() {
    }


}
