import java.sql.Connection;
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
        //logOrSignUp variable declaration
        String logOrSignUp;
        //Asking for Log In or Sign Up
        do {
            System.out.println("Type 1 for Login ot type 2 for Sign Up: ");
            logOrSignUp = scanner.nextLine();
        } while (logOrSignUp.isEmpty() && !(logOrSignUp.equals("2") || logOrSignUp.equals("1")));

    }
}