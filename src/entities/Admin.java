package entities;

public class Admin extends User{
    public Admin() {
    }

    public Admin(String firstName, String lastName, String email, String passwordHash, boolean isAdmin) {
        super(firstName, lastName, email, passwordHash, isAdmin);
    }


}
