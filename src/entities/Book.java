package entities;

public class Book {
    private int bookId;
    private String title;
    private int authorId;
    private int publishedYear;
    private boolean isAvailable;

    public Book(){}

    public Book(String title, int authorId, int publishedYear, boolean isAvailable) {
        this.title = title;
        this.authorId = authorId;
        this.publishedYear = publishedYear;
        this.isAvailable = isAvailable;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", publishedYear=" + publishedYear +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
