package interfaces.admin;

import java.sql.SQLException;

public interface insertBook {
    
    public void insertBook();

    public void insertBook(String title, int authorId, int publishYear) throws SQLException;
// It inserts a book into the database with the provided title, author ID, and publish year. This method may throw a SQLException
}
