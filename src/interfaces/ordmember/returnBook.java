package interfaces.ordmember;

import java.sql.SQLException;

public interface returnBook {
    public void returnBook();

    public void returnBook(int bookId) throws SQLException;
}
