package entities;

import interfaces.admin.deleteAuthor;
import interfaces.admin.deleteBook;
import interfaces.admin.insertAuthor;
import interfaces.admin.insertBook;
import interfaces.ordmember.borrowBook;
import interfaces.ordmember.returnBook;
import interfaces.watchLoans;

public class User implements deleteAuthor, deleteBook, insertAuthor, insertBook, borrowBook, returnBook, watchLoans{
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private boolean isAdmin;

    public User() {}

    public User(String firstName, String lastName, String email, String passwordHash, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    public int getUserUd() {
        return userId;
    }

    public void setUserId(int userUd) {
        this.userId = userUd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userUd=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                (isAdmin ? ", admin " : " has no admin credentials ") +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
