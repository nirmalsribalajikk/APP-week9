import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductJdbcApp {
    private static final String DB_URL =
            System.getenv().getOrDefault("LIBRARY_DB_URL", "jdbc:mysql://localhost:3306/library");
    private static final String DB_USER =
            System.getenv().getOrDefault("LIBRARY_DB_USER", "root");
    private static final String DB_PASSWORD =
            System.getenv().getOrDefault("LIBRARY_DB_PASSWORD", "root");

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to the database.");
            createProductTableIfNeeded(connection);
            runMenu(connection, scanner);
        } catch (SQLException exception) {
            System.err.println("Database error: " + exception.getMessage());
        }
    }

    private static void createProductTableIfNeeded(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Product ("
                + "ProductID INT PRIMARY KEY, "
                + "ProductName VARCHAR(255) NOT NULL, "
                + "Price DECIMAL(10, 2) NOT NULL, "
                + "Quantity INT NOT NULL)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    private static void runMenu(Connection connection, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Product Management ---");
            System.out.println("1. Insert new product");
            System.out.println("2. Retrieve product by ID");
            System.out.println("3. Update product quantity");
            System.out.println("4. Display products with quantity below 10");
            System.out.println("5. Exit");

            int choice = readInt(scanner, "Enter your choice: ");
            try {
                switch (choice) {
                    case 1:
                        insertProduct(connection, scanner);
                        break;
                    case 2:
                        retrieveProduct(connection, scanner);
                        break;
                    case 3:
                        updateQuantity(connection, scanner);
                        break;
                    case 4:
                        displayLowStockProducts(connection);
                        break;
                    case 5:
                        System.out.println("Goodbye.");
                        return;
                    default:
                        System.out.println("Please enter a number from 1 to 5.");
                }
            } catch (SQLException exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            }
        }
    }

    private static void insertProduct(Connection connection, Scanner scanner) throws SQLException {
        int productId = readInt(scanner, "Product ID: ");
        String productName = readRequiredText(scanner, "Product name: ");
        BigDecimal price = readPrice(scanner);
        int quantity = readNonNegativeInt(scanner, "Quantity: ");

        String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            statement.setString(2, productName);
            statement.setBigDecimal(3, price);
            statement.setInt(4, quantity);
            statement.executeUpdate();
            System.out.println("Product inserted successfully.");
        }
    }

    private static void retrieveProduct(Connection connection, Scanner scanner) throws SQLException {
        int productId = readInt(scanner, "Product ID to retrieve: ");
        String sql = "SELECT ProductID, ProductName, Price, Quantity "
                + "FROM Product WHERE ProductID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    printProduct(result);
                } else {
                    System.out.println("No product found with ID " + productId + ".");
                }
            }
        }
    }

    private static void updateQuantity(Connection connection, Scanner scanner) throws SQLException {
        int productId = readInt(scanner, "Product ID: ");
        int quantity = readNonNegativeInt(scanner, "New quantity: ");

        String sql = "UPDATE Product SET Quantity = ? WHERE ProductID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 1) {
                System.out.println("Product quantity updated successfully.");
            } else {
                System.out.println("No product found with ID " + productId + ".");
            }
        }
    }

    private static void displayLowStockProducts(Connection connection) throws SQLException {
        String sql = "SELECT ProductID, ProductName, Price, Quantity "
                + "FROM Product WHERE Quantity < ? ORDER BY Quantity, ProductID";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 10);
            try (ResultSet result = statement.executeQuery()) {
                boolean found = false;
                while (result.next()) {
                    printProduct(result);
                    found = true;
                }
                if (!found) {
                    System.out.println("No products have quantity below 10.");
                }
            }
        }
    }

    private static void printProduct(ResultSet result) throws SQLException {
        System.out.printf(
                "ID: %d | Name: %s | Price: %s | Quantity: %d%n",
                result.getInt("ProductID"),
                result.getString("ProductName"),
                result.getBigDecimal("Price"),
                result.getInt("Quantity"));
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

    private static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("Value cannot be negative.");
        }
    }

    private static BigDecimal readPrice(Scanner scanner) {
        while (true) {
            System.out.print("Price: ");
            try {
                BigDecimal price = new BigDecimal(scanner.nextLine().trim());
                if (price.signum() >= 0) {
                    return price;
                }
            } catch (NumberFormatException exception) {
                // The message below gives the user a clear retry instruction.
            }
            System.out.println("Enter a valid non-negative price.");
        }
    }

    private static String readRequiredText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }
}
