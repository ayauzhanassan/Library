import entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
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
        ArrayList<Admin> admins = new ArrayList<>();
        ArrayList<OrdMember> ordMembers = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        ArrayList<Loan> loans = new ArrayList<>();
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

                    if (!isAdmin) {
                        OrdMember ordMember = new OrdMember(firstName, lastName, email, passwordHash, isAdmin);
                        ordMember.setUserId(userId);
                        ordMembers.add(ordMember);
                    } else {
                        Admin admin = new Admin(firstName, lastName, email, passwordHash, isAdmin);
                        admin.setUserId(userId);
                        admins.add(admin);
                    }
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

            if (ordMembers.isEmpty() && admins.isEmpty()) {
                System.out.println("incorrect email or password");
            } else if (!ordMembers.isEmpty() && admins.isEmpty()) {
                System.out.println("you have logged in successfully !!!");
                for (OrdMember ordMember : ordMembers) {
                    System.out.println(ordMember);
                }

                System.out.println("Choose an action:");
                System.out.println("1. Borrow a book");
                System.out.println("2. Return a book");
                System.out.println("3. Watch your loans");
                System.out.println("4. Quit program");
                int action = scanner.nextInt();
                switch (action) {
                    case 1:

                        showBooks(books, conn);

                        scanner.nextLine();

                        System.out.println("Enter an ID of a book you want to borrow:");
                        int bookIdToBorrow = scanner.nextInt();

                        ordMembers.get(0).borrowBook(bookIdToBorrow);

                        break;

                    case 2:

                        showBooks(books, conn);

                        scanner.nextLine();

                        System.out.println("Enter an ID of a book you want to return:");
                        int bookIdToReturn = scanner.nextInt();

                        ordMembers.get(0).returnBook(bookIdToReturn);

                        break;

                    case 3:

                        System.out.println("Here are the loans you have taken:");

                        ordMembers.get(0).getLoans(loans, ordMembers.get(0).getUserId());

                        break;

                }
            } else if (ordMembers.isEmpty() && !admins.isEmpty()) {
                System.out.println("you have logged in successfully !!!");
                for (Admin admin : admins) {
                    System.out.println(admin);
                }

                System.out.println("Choose an action");
                System.out.println("1. Insert a book");
                System.out.println("2. Delete a book");
                System.out.println("3. Insert an author");
                System.out.println("4. Delete an author");
                System.out.println("5. Watch loans");
                System.out.println("6. Quit program");
                int action = scanner.nextInt();
                switch (action) {
                    case 1:

                        showBooks(books, conn);
                        showAuthors(authors, conn);

                        scanner.nextLine();

                        System.out.println("Enter an the title of book you want to insert:");
                        String titleToInsert = scanner.nextLine();
                        System.out.println(("Enter an ID of book's author:"));
                        int authorIdToInsert = scanner.nextInt();
                        System.out.println("Enter a published year");
                        int publishedYearToInsert = scanner.nextInt();

                        admins.get(0).insertBook(titleToInsert, authorIdToInsert, publishedYearToInsert);

                        break;

                    case 2:

                        showBooks(books, conn);

                        scanner.nextLine();

                        System.out.println("Enter an Id of a book you want to delete:");
                        int bookIdToDelete = scanner.nextInt();

                        admins.get(0).deleteBook(bookIdToDelete);

                        break;

                    case 3:

                        showAuthors(authors, conn);

                        scanner.nextLine();

                        System.out.println("Enter a first name of an author you want to insert:");
                        String firstNameToInsert = scanner.nextLine();
                        System.out.println("Enter a last name of an author you want to insert:");
                        String lastNameToInsert = scanner.nextLine();

                        admins.get(0).insertAuthor(firstNameToInsert, lastNameToInsert);

                        break;

                    case 4:

                        showAuthors(authors, conn);

                        scanner.nextLine();

                        System.out.println("Enter an ID of an author you want to delete:");
                        int authorIdToDelete = scanner.nextInt();

                        admins.get(0).deleteAuthor(authorIdToDelete);

                        break;

                    case 5:

                        System.out.println("Here are all of the existing loans:");

                        admins.get(0).getLoans(loans);

                        break;


                    case 6:

                        System.exit(0);

                        break;

                }

            }
        } else if (logOrSignUp.equals("2")) {
            System.out.println("Sigh Up:");
            String firstNameInput;

            do {
                System.out.println("Enter your first name:");
                firstNameInput = scanner.nextLine();
            } while (firstNameInput.isEmpty());

            String lastNameInput;
            do {
                System.out.println("Enter your last name:");
                lastNameInput = scanner.nextLine();
            } while (lastNameInput.isEmpty());

            String emailInput;
            do {
                System.out.println("Enter your email:");
                emailInput = scanner.nextLine();
            } while (emailInput.isEmpty());

            String passwordInput;
            do {
                System.out.println("Enter your password:");
                passwordInput = scanner.nextLine();
            } while (passwordInput.isEmpty());

            String passwordInputRep;
            do {
                System.out.println("Repeat password:");
                passwordInputRep = scanner.nextLine();
            } while (passwordInputRep.isEmpty() && !Objects.equals(passwordInputRep, passwordInput));

            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(connString, "postgres", "123");

                String sql = "INSERT INTO members (firstname, lastname, email, passwordhash, isadmin) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmn = conn.prepareStatement(sql);

                stmn.setString(1, firstNameInput);
                stmn.setString(2, lastNameInput);
                stmn.setString(3, emailInput);
                stmn.setString(4, passwordInput);
                stmn.setBoolean(5, false);

                int rowsAffected = stmn.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("You have signed up succesfully");
                } else {
                    System.out.println("Sign Up failed...");
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
                        System.out.println("could not close the exception: " + e.getMessage());
                    }
                }
            }
        }

    }

    public static void showBooks(ArrayList<Book> books, Connection conn) {

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(connString, "postgres", "123");

            String sqlSelectBooks = "SELECT * FROM books";
            PreparedStatement selectBooksStmt = conn.prepareStatement(sqlSelectBooks);

            ResultSet rs = selectBooksStmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("bookid");
                String title = rs.getString("title");
                int authorId = rs.getInt("authorid");
                int publishedYear = rs.getInt("publishedyear");
                boolean isAvailable = rs.getBoolean("isavailable");

                Book book = new Book(title, authorId, publishedYear, isAvailable);
                book.setBookId(bookId);
                books.add(book);
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

        if (books.isEmpty()) {
            System.out.println("no books");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }

        books.clear();

    }

    public static void showAuthors(ArrayList<Author> authors, Connection conn) {

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(connString, "postgres", "123");

            String sqlSelectAuthors = "SELECT * FROM authors";
            PreparedStatement selectAuthorsStmt = conn.prepareStatement(sqlSelectAuthors);

            ResultSet rs = selectAuthorsStmt.executeQuery();

            while (rs.next()) {
                int authorId = rs.getInt("authorid");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");

                Author author = new Author(firstName, lastName);
                author.setAuthorId(authorId);
                authors.add(author);
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

        if (authors.isEmpty()) {
            System.out.println("no authors");
        } else {
            for (Author author : authors) {
                System.out.println(author);
            }
        }

        authors.clear();

    }
}