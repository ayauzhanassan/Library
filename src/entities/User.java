package entities;
import interfaces.ordinary_user.*;
import interfaces.admin.*;
import interfaces.watchLoans;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class User implements borrowBook, returnBook, insertBook, deleteBook, insertAuthor, deleteAuthor, watchLoans{
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                (isAdmin ? " admin " : " has no admin rights ") +
                '}';
    }

    @Override
    public void getLoans() throws SQLException {

    }

    @Override
    public void borrowBook() {

    }

    @Override
    public void returnBook() {

    }

    @Override
    public void insertBook() {

    }

    @Override
    public void deleteBook() {

    }

    @Override
    public void insertAuthor() {

    }

    @Override
    public void deleteAuthor() {

    }

    public abstract void getLoans(ArrayList<Loan> loans) throws SQLException;

    public abstract void getLoans(ArrayList<Loan> loans, int userId) throws SQLException;
}
