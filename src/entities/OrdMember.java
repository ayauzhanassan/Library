package entities;

public class OrdMember extends User{
    public OrdMember() {
    }

    public OrdMember(String firstName, String lastName, String email, String passwordHash, boolean isAdmin) {
        super(firstName, lastName, email, passwordHash, isAdmin);
    }
}
