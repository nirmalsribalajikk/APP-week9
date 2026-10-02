import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookJdbcApp {
    private static final String DB_URL =
            System.getenv().getOrDefault("LIBRARY_DB_URL", "jdbc:mysql://localhost:3306/library");
    private static final String DB_USER =
            System.getenv().getOrDefault("LIBRARY_DB_USER", "root");
    private static final String DB_PASSWORD =
            System.getenv().getOrDefault("LIBRARY_DB_PASSWORD", "root");

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to the library database.");
            runMenu(connection, scanner);
        } catch (SQLException exception) {
            System.err.println("Could not connect to the database: " + exception.getMessage());
        }
    }

    private static void runMenu(Connection connection, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Library Book Management ---");
            System.out.println("1. Insert new book");
            System.out.println("2. Search book by ID");
            System.out.println("3. Display available books");
            System.out.println("4. Issue book");
            System.out.println("5. Exit");

            int choice = readInt(scanner, "Enter your choice: ");
            try {
                switch (choice) {
                    case 1:
                        insertBook(connection, scanner);
                        break;
                    case 2:
                        searchBook(connection, scanner);
                        break;
                    case 3:
                        displayAvailableBooks(connection);
                        break;
                    case 4:
                        issueBook(connection, scanner);
                        break;
                    case 5:
                        System.out.println("Goodbye.");
                        return;
                    default:
                        System.out.println("Please select a valid option.");
                }
            } catch (SQLException exception) {
                System.out.println("Database operation failed: " + exception.getMessage());
            }
        }
    }

    private static void insertBook(Connection connection, Scanner scanner) throws SQLException {
        int bookId = readInt(scanner, "Book ID: ");
        String title = readRequiredText(scanner, "Title: ");
        String author = readRequiredText(scanner, "Author: ");
        BigDecimal price = readPrice(scanner);

        String sql = "INSERT INTO Book (BookID, Title, Author, Price, Availability) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setBigDecimal(4, price);
            statement.setBoolean(5, true);
            statement.executeUpdate();
            System.out.println("Book inserted successfully.");
        }
    }

    private static void searchBook(Connection connection, Scanner scanner) throws SQLException {
        int bookId = readInt(scanner, "Book ID to search: ");
        String sql = "SELECT BookID, Title, Author, Price, Availability "
                + "FROM Book WHERE BookID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    printBook(result);
                } else {
                    System.out.println("No book found with ID " + bookId + ".");
                }
            }
        }
    }

    private static void displayAvailableBooks(Connection connection) throws SQLException {
        String sql = "SELECT BookID, Title, Author, Price, Availability "
                + "FROM Book WHERE Availability = ? ORDER BY BookID";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, true);
            try (ResultSet result = statement.executeQuery()) {
                boolean found = false;
                while (result.next()) {
                    printBook(result);
                    found = true;
                }
                if (!found) {
                    System.out.println("No available books found.");
                }
            }
        }
    }

    private static void issueBook(Connection connection, Scanner scanner) throws SQLException {
        int bookId = readInt(scanner, "Book ID to issue: ");
        String sql = "UPDATE Book SET Availability = ? "
                + "WHERE BookID = ? AND Availability = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, false);
            statement.setInt(2, bookId);
            statement.setBoolean(3, true);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 1) {
                System.out.println("Book issued successfully.");
            } else {
                System.out.println("Book was not issued. It may not exist or is already issued.");
            }
        }
    }

    private static void printBook(ResultSet result) throws SQLException {
        System.out.printf(
                "ID: %d | Title: %s | Author: %s | Price: %s | Availability: %s%n",
                result.getInt("BookID"),
                result.getString("Title"),
                result.getString("Author"),
                result.getBigDecimal("Price"),
                result.getBoolean("Availability") ? "Available" : "Issued");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static BigDecimal readPrice(Scanner scanner) {
        while (true) {
            System.out.print("Price: ");
            String input = scanner.nextLine().trim();
            try {
                BigDecimal price = new BigDecimal(input);
                if (price.signum() < 0) {
                    throw new NumberFormatException();
                }
                return price;
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid non-negative price.");
            }
        }
    }

    private static String readRequiredText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be empty.");
        }
    }
}
