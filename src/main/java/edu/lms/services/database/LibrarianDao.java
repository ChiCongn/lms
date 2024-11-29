package edu.lms.services.database;

public class LibrarianDao {
    private static final String LOAD_LIBRARIANS_QUERY = "SELECT * FROM librarians";
    private static final String LOAD_A_SPECIFIC_LIBRARIAN_QUERY = "SELECT * FROM librarians WHERE librarian_id = ?";

    public LibrarianDao() {
    }


}
