package entities;

import java.sql.*;
import java.util.ArrayList;

public class OrdMember extends User{
    public OrdMember() {
    }

    public OrdMember(String firstName, String lastName, String email, String passwordHash, boolean isAdmin) {
        super(firstName, lastName, email, passwordHash, isAdmin);
    }

    @Override
    public void getLoans(ArrayList<Loan> loans) throws SQLException {

    }

    @Override
    public void getLoans(ArrayList<Loan> loans, int userId) throws SQLException {}

    @Override
    public void borrowBook(int bookId) throws SQLException {}

    @Override
    public void returnBook(int bookId) throws SQLException {
    }


    @Override
    public void deleteAuthor(int authorId) throws SQLException {

    }

    @Override
    public void deleteBook(int bookId) throws SQLException {

    }

    @Override
    public void insertAuthor(String firstName, String lastName) throws SQLException {

    }

    @Override
    public void insertBook(String title, int authorId, int publishYear) throws SQLException {

    }
}
