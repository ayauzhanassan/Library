import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //Create a scanner
    private static final Scanner scanner = new Scanner(System.in);
    //Connection string
    public static String connString = "jdbc:postgresql://localhost:5432/library11";
    public static void main(String[] args) {
        //Greeting a User
        System.out.println("Hello!");
        //Declare a connection variable
        Connection conn = null;
        //Create ArrayLists
        ArrayList<User> users = new ArrayList<>();
        //logOrSignUp variable declaration
        String logOrSignUp;
        //Asking for Log In or Sign Up
        do {
            System.out.println("Type 1 for Login ot type 2 for Sign Up: ");
            logOrSignUp = scanner.nextLine();
        } while (logOrSignUp.isEmpty() && !(logOrSignUp.equals("2") || logOrSignUp.equals("1")));

        //If the user chose Log In
        if (logOrSignUp.equals("1")) {
            System.out.println("Login");
            System.out.println("Email:");
            String emailInput = scanner.nextLine();
            System.out.println("Password:");
            String passwordInput = scanner.nextLine();
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(connString, "postgres", "123");

                String sql = "SELECT * FROM members WHERE email = ? AND passwordhash = ?";
                PreparedStatement stmn = conn.prepareStatement(sql);

                stmn.setString(1, emailInput);
                stmn.setString(2, passwordInput);

                ResultSet rs = stmn.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("memberid");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String passwordHash = rs.getString("passwordhash");
                    boolean isAdmin = rs.getBoolean("isAdmin");

                    User user = new User(firstName, lastName, email, passwordHash, isAdmin);
                    user.setUserUd(userId);
                    users.add(user);
                } else {
                    System.out.println("incorrect email or password");
                }
            } catch (SQLException e) {
                System.out.println("connection error: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("driver error: " + e.getMessage());
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

    }
}