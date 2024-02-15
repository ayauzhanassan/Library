public class Book extends LibraryItem implements Loanable {
    private String author;
    private int yearPublished;
    private String genre;

    public Book(int itemId, String title, String author, int yearPublished, String genre, boolean isAvailable) {
        super(itemId, title, isAvailable);
        this.author = author;
        this.yearPublished = yearPublished;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // implementing methods from Loanable
    @Override
    public void checkOut(int memberId) throws ItemNotAvailableException {
        if (!isAvailable()) {
            throw new ItemNotAvailableException("Item is not available for checkout.");
        }
        setAvailable(false);
        System.out.println("Item checked out to member ID: " + memberId);
    }

    @Override
    public void checkIn() {
        setAvailable(true);
        System.out.println("Book returned to the library");
    }

    @Override
    public String toString() {
        return "Book{" +
                "itemId=" + getItemId() +
                ", title='" + getTitle() + '\'' +
                ", author='" + author + '\'' +
                ", yearPublished=" + yearPublished +
                ", genre='" + genre + '\'' +
                ", isAvailable=" + isAvailable() +
                '}';
    }
}
