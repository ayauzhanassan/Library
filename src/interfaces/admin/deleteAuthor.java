package interfaces.admin;

import java.sql.SQLException;

public interface deleteAuthor {
    
    public void deleteAuthor();
    
    public void deleteAuthor(int authorId) throws SQLException;
//This method is used for deleting an author based on their ID and may throw a SQLException
}
