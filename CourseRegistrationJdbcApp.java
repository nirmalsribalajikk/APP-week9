import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseRegistrationJdbcApp {
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
            createTableIfNeeded(connection);

            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine().trim();
            if (courseCode.isEmpty()) {
                System.out.println("Course code cannot be empty.");
                return;
            }

            displayRegistrations(connection, courseCode);
        } catch (SQLException exception) {
            System.err.println("Database error: " + exception.getMessage());
        }
    }

    private static void createTableIfNeeded(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS CourseRegistration ("
                + "StudentID INT NOT NULL, "
                + "StudentName VARCHAR(255) NOT NULL, "
                + "CourseCode VARCHAR(50) NOT NULL, "
                + "CourseName VARCHAR(255) NOT NULL, "
                + "Semester VARCHAR(50) NOT NULL)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    private static void displayRegistrations(Connection connection, String courseCode)
            throws SQLException {
        String sql = "SELECT StudentID, StudentName, CourseCode, CourseName, Semester "
                + "FROM CourseRegistration WHERE CourseCode = ? ORDER BY StudentID";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseCode);
            try (ResultSet result = statement.executeQuery()) {
                boolean found = false;
                while (result.next()) {
                    System.out.printf(
                            "Student ID: %d | Student Name: %s | Course Code: %s | "
                                    + "Course Name: %s | Semester: %s%n",
                            result.getInt("StudentID"),
                            result.getString("StudentName"),
                            result.getString("CourseCode"),
                            result.getString("CourseName"),
                            result.getString("Semester"));
                    found = true;
                }

                if (!found) {
                    System.out.println(
                            "No students are registered for course code: " + courseCode);
                }
            }
        }
    }
}
