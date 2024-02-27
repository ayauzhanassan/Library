package entities;

import java.sql.*;
import java.util.ArrayList;

public class Admin extends User{
    public Admin() {
    }

    public Admin(String firstName, String lastName, String email, String passwordHash, boolean isAdmin) {
        super(firstName, lastName, email, passwordHash, isAdmin);
    }
// Override method to insert a new book into the database
    @Override
    public void insertBook(String title, int authorId, int publishedYear) throws SQLException {
        // Establishing a database connection
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library11", "postgres", "123");

        try (conn) {
            String sql = "INSERT INTO books (title, authorid, publishedyear, isavailable) VALUES (?, ?, ?, true)";
            PreparedStatement stmn = conn.prepareStatement(sql);

            stmn.setString(1, title);
            stmn.setInt(2, authorId);
            stmn.setInt(3, publishedYear);

            int rowsAffected = stmn.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The book has been successfully inserted.");
            } else {
                System.out.println("Failed to insert the book.");
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
        }
    }
    // Override method to delete a book from the database
    @Override
    public void deleteBook(int bookId) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library11", "postgres", "123");

        try (conn) {
            String sql = "DELETE FROM books WHERE bookid = ?";
            PreparedStatement stmn = conn.prepareStatement(sql);

            stmn.setInt(1, bookId);

            int rowsAffected = stmn.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The book has been successfully deleted.");
            } else {
                System.out.println("Failed to delete book.");
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
        }
    }
    // Override method to insert a new author into the database
    @Override
    public void insertAuthor(String firstName, String lastName) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library11", "postgres", "123");

        try (conn) {
            String sql = "INSERT INTO authors (firstname, lastname) VALUES (?, ?)";
            PreparedStatement stmn = conn.prepareStatement(sql);

            stmn.setString(1, firstName);
            stmn.setString(2, lastName);

            int rowsAffected = stmn.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The author has been successfully inserted.");
            } else {
                System.out.println("Failed to insert an author");
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
        }
    }
    // Override method to delete an author from the database
    @Override
    public void deleteAuthor(int authorId) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library11", "postgres", "123");

        try (conn) {
            String sql = "DELETE FROM authors WHERE authorid = ?";
            PreparedStatement stmn = conn.prepareStatement(sql);

            stmn.setInt(1, authorId);

            int rowsAffected = stmn.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The author has been successfully deleted.");
            } else {
                System.out.println("Failed to delete an author");
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
        }
    }
// Override method to get all loans from the database
    @Override
    public void getLoans(ArrayList<Loan> loans) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library11", "postgres", "123");

        try (conn) {
            Class.forName("org.postgresql.Driver");

            String sqlSelectLoans = "SELECT * FROM loans WHERE";
            PreparedStatement selectLoanssStmt = conn.prepareStatement(sqlSelectLoans);

            ResultSet rs = selectLoanssStmt.executeQuery();

            while (rs.next()) {
                int loanId = rs.getInt("loanid");
                int memberId = rs.getInt("memberid");
                int bookId = rs.getInt("bookid");
                java.util.Date loanDate = rs.getDate("loandate");
                java.util.Date returnDate = rs.getDate("returndate");

                Loan loan = new Loan(memberId, bookId, (Date) loanDate, (Date) returnDate);
                loan.setLoanId(loanId);
                loans.add(loan);
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("could not close the connection: " + e.getMessage());
                }
            }
        }

        if (loans.isEmpty()) {
            System.out.println("no loans");
        } else {
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        }

        loans.clear();

    }

    @Override
    public void getLoans(ArrayList<Loan> loans, int userId) throws SQLException {

    }

    @Override
    public void borrowBook(int bookId) throws SQLException {

    }

    @Override
    public void returnBook(int bookId) throws SQLException {

    }
}
