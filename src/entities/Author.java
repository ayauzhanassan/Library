package entities;

public class Author {
    private int authorId;
    private String firstName;
    private String lastname;

    public Author() {};

    public Author(String firstName, String lastname) {
        this.firstName = firstName;
        this.lastname = lastname;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
