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
