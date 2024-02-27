package interfaces.admin;

import java.sql.SQLException;

public interface insertAuthor {
    
    public void insertAuthor();

    public void insertAuthor(String firstName, String lastName) throws SQLException;
    //It inserts an author into the database with the provided first name and last name. This method may throw a SQLException
}
