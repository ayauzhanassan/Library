public interface Loanable {
    void checkOut(int memberId) throws ItemNotAvailableException;
    void checkIn();
}
