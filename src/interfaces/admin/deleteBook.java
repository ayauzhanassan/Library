package interfaces.admin;

import java.sql.SQLException;

public interface deleteBook {
    
    public void deleteBook();

    public void deleteBook(int bookId) throws SQLException;
    //This method deletes a book based on the provided book ID and may throw a SQLException
}
