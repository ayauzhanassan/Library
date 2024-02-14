import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private BookDAO bookDAO;

    public LibraryService() {
        this.bookDAO = new BookDAO();
    }

    public void addBook(Book book) {
        bookDAO.addLibraryItem(book);
    }

    public Book getBookById(int bookId) {
        LibraryItem item = bookDAO.getLibraryItemById(bookId);
        if (item instanceof Book) {
            return (Book) item;
        }
        return null;
    }

    public List<Book> getAllBooks() {
        List<LibraryItem> items = bookDAO.getAllLibraryItems();
        List<Book> books = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item instanceof Book) {
                books.add((Book) item);
            }
        }
        return books;
    }

    public boolean updateBook(Book book) {
        return bookDAO.updateLibraryItem(book);
    }

    public boolean deleteBook(int bookId) {
        return bookDAO.deleteLibraryItem(bookId);
    }

}
