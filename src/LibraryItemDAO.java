import java.util.List;

public interface LibraryItemDAO {
    void addLibraryItem(LibraryItem item);
    LibraryItem getLibraryItemById(int itemId);
    List<LibraryItem> getAllLibraryItems();
    boolean updateLibraryItem(LibraryItem item);
    boolean deleteLibraryItem(int itemId);
}
