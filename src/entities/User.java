package entities;
import interfaces.ordmember.*;
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
