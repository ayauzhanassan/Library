package interfaces.ordmember;

import java.sql.SQLException;

public interface borrowBook {
    public void borrowBook();

    public void borrowBook(int bookId) throws SQLException;
}
