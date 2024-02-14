import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements LibraryItemDAO {
    private Connection connection;

    public BookDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public List<LibraryItem> getAllLibraryItems() {
        List<LibraryItem> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("item_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year_published"),
                        resultSet.getString("genre"),
                        resultSet.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public LibraryItem getLibraryItemById(int itemId) {
        String sql = "SELECT * FROM books WHERE item_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Book(
                        resultSet.getInt("item_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year_published"),
                        resultSet.getString("genre"),
                        resultSet.getBoolean("is_available")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addLibraryItem(LibraryItem item) {
        if (!(item instanceof Book)) {
            throw new IllegalArgumentException("Item must be a Book instance");
        }
        Book book = (Book) item;
        String sql = "INSERT INTO books (title, author, year_published, genre, is_available) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getYearPublished());
            statement.setString(4, book.getGenre());
            statement.setBoolean(5, book.isAvailable());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateLibraryItem(LibraryItem item) {
        if (!(item instanceof Book)) {
            throw new IllegalArgumentException("Item must be a Book instance");
        }
        Book book = (Book) item;
        String sql = "UPDATE books SET title = ?, author = ?, year_published = ?, genre = ?, is_available = ? WHERE item_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getYearPublished());
            statement.setString(4, book.getGenre());
            statement.setBoolean(5, book.isAvailable());
            statement.setInt(6, book.getItemId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteLibraryItem(int itemId) {
        String sql = "DELETE FROM books WHERE item_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
