package entities;

import java.sql.Date;

public class Loan {
    // Fields to represent loan details
    private int loanId;
    private int memberId;
    private int bookId;
    private Date loanDate;
    private Date returnDate;

    public Loan(){ // Default constructor

    }
    // Parameterized constructor
    public Loan(int memberId, int bookId, Date loanDate, Date returnDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
    // Getters and setters for loan fields
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    // Override toString() method to provide a meaningful string representation of the Loan object
    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", memberId=" + memberId +
                ", bookId=" + bookId +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
