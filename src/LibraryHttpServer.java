import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryHttpServer {

    private static LibraryService libraryService = new LibraryService();

    private static Map<String, String> parseFormData(String formData) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                try {
                    value = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                params.put(key, value);
            }
        }
        return params;
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // list all books
        server.createContext("/books", (HttpExchange exchange) -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<Book> books = libraryService.getAllBooks();
                String response = books.toString();

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                // method not allowed
                exchange.sendResponseHeaders(405, -1);
            }
        });

        server.createContext("/addBook", (exchange -> {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();

                Map<String, String> params = parseFormData(formData);
                boolean isAvailable = "1".equals(params.get("isAvailable")) || "true".equalsIgnoreCase(params.get("isAvailable"));

                Book book = new Book(
                        Integer.parseInt(params.get("itemId")),
                        params.get("title"),
                        params.get("author"),
                        Integer.parseInt(params.get("yearPublished")),
                        params.get("genre"),
                        isAvailable
                );
                libraryService.addBook(book);

                String response = "Book added successfully";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }));

        server.createContext("/getBookById", (HttpExchange exchange) -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] queryParams = query.split("=");
                int bookId = Integer.parseInt(queryParams[1]);

                Book book = libraryService.getBookById(bookId);

                if (book != null) {
                    String response = "Book ID: " + book.getItemId() + "\n"
                            + "Title: " + book.getTitle() + "\n"
                            + "Author: " + book.getAuthor() + "\n"
                            + "Year Published: " + book.getYearPublished() + "\n"
                            + "Genre: " + book.getGenre() + "\n"
                            + "Available: " + book.isAvailable();

                    // send the response back
                    exchange.getResponseHeaders().set("Content-Type", "text/plain");
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    // book not found
                    String response = "Book not found";
                    exchange.sendResponseHeaders(404, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        server.createContext("/deleteBook", (HttpExchange exchange) -> {
            if ("DELETE".equalsIgnoreCase(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] queryParams = query.split("=");
                int bookId = Integer.parseInt(queryParams[1]);

                boolean deleted = libraryService.deleteBook(bookId);

                if (deleted) {
                    String response = "Book deleted successfully";
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    // book not found or error
                    String response = "Failed to delete book";
                    exchange.sendResponseHeaders(404, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        server.createContext("/updateBook", (HttpExchange exchange) -> {
            if ("PUT".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine(); // read POST data

                Map<String, String> params = parseFormData(formData);

                // extract book details
                int bookId = Integer.parseInt(params.get("itemId"));
                String title = params.get("title");
                String author = params.get("author");
                int yearPublished = Integer.parseInt(params.get("yearPublished"));
                String genre = params.get("genre");
                boolean isAvailable = "1".equals(params.get("isAvailable")) || "true".equalsIgnoreCase(params.get("isAvailable"));

                // create a book object with the updated details
                Book updatedBook = new Book(bookId, title, author, yearPublished, genre, isAvailable);

                // update a book in db
                boolean updated = libraryService.updateBook(updatedBook);

                if (updated) {
                    String response = "Book updated successfully";
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    // book not found or error
                    String response = "Failed to update book";
                    exchange.sendResponseHeaders(404, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        // check out a book
        server.createContext("/checkoutBook", (HttpExchange exchange) -> {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine(); // Read POST data
                Map<String, String> params = parseFormData(formData);

                try {
                    int bookId = Integer.parseInt(params.get("itemId"));
                    int memberId = Integer.parseInt(params.get("memberId"));
                    Book book = libraryService.getBookById(bookId);

                    if (book == null) {
                        String response = "Book not found";
                        exchange.sendResponseHeaders(404, response.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        return;
                    }

                    // attempt to check out the book
                    try {
                        book.checkOut(memberId);
                        libraryService.updateBook(book); // update book's availability

                        String response = "Book checked out successfully";
                        exchange.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (ItemNotAvailableException e) {
                        // if not available for checkout
                        String response = e.getMessage();
                        exchange.sendResponseHeaders(409, response.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }

                } catch (NumberFormatException e) {
                    // handle case where bookId or memberId isnt valid integers
                    String response = "Invalid book ID or member ID";
                    exchange.sendResponseHeaders(400, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                // handle case where HTTP method is not POST
                exchange.sendResponseHeaders(405, -1);
            }
        });

        server.createContext("/returnBook", (HttpExchange exchange) -> {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map<String, String> params = parseFormData(formData);

                try {
                    int bookId = Integer.parseInt(params.get("itemId"));
                    Book book = libraryService.getBookById(bookId);

                    if (book != null) {
                        book.checkIn();
                        libraryService.updateBook(book);

                        String response = "Book returned successfully";
                        exchange.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } else {
                        String response = "Book not found";
                        exchange.sendResponseHeaders(404, response.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                } catch (NumberFormatException e) {
                    String response = "Invalid book ID";
                    exchange.sendResponseHeaders(400, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });


        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8000");
    }
}
