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
    public void getLoans(ArrayList<Loan> loans, int userId) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library11", "postgres", "123");

        try (conn) {
            Class.forName("org.postgresql.Driver");

            String sqlSelectLoans = "SELECT * FROM loans WHERE  memberid = ?";
            PreparedStatement selectLoanssStmt = conn.prepareStatement(sqlSelectLoans);

            selectLoanssStmt.setInt(1,userId);

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
    public void borrowBook(int bookId) throws SQLException {

        Date currentDate = new Date(System.currentTimeMillis());

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library11", "postgres", "123");

        try(conn) {
            String sql = "UPDATE books SET isavailable = false WHERE bookid = ? AND isavailable = true";
            PreparedStatement stmn = conn.prepareStatement(sql);

            stmn.setInt(1, bookId);

            int rowsAffected = stmn.executeUpdate();

            if (rowsAffected > 0) {
                String insertSql = "INSERT INTO loans (memberid, bookid, loandate) VALUES (?, ?, ?)";
                stmn = conn.prepareStatement(insertSql);

                stmn.setInt(1, this.getUserId());
                stmn.setInt(2, bookId);
                stmn.setDate(3, currentDate);

                stmn.executeUpdate();

                System.out.println("You have successfully borrowed the book!");
            } else {
                System.out.println("This book is not available");
            }
        } catch (SQLException e)  {
            System.out.println("connection error: " + e.getMessage());
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

    @Override
    public void returnBook(int bookId) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library11", "postgres", "123");

        try (conn) {
            String sql = "UPDATE loans SET returndate = ? WHERE memberid = ? AND bookid = ? AND returndate IS NULL";
            PreparedStatement stmn = conn.prepareStatement(sql);

            stmn.setDate(1, new Date(System.currentTimeMillis()));
            stmn.setInt(2, this.getUserId());
            stmn.setInt(3, bookId);

            int rowsAffected = stmn.executeUpdate();

            if (rowsAffected > 0) {
                sql = "UPDATE books SET isavailable = true WHERE bookid = ?";
                stmn = conn.prepareStatement(sql);

                stmn.setInt(1, bookId);

                stmn.executeUpdate();

                System.out.println("You have successfully returned the book");
            } else {
                System.out.println("You have not borrowed this book.");
            }
        } catch (SQLException e)  {
            System.out.println("connection error: " + e.getMessage());
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
