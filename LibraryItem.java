public abstract class LibraryItem {
    protected int itemId;
    protected String title;
    protected boolean isAvailable;


    public LibraryItem(int itemId, String title, boolean isAvailable) {
        this.itemId = itemId;
        this.title = title;
        this.isAvailable = isAvailable;
    }

    public int getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
