import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeManagementModel {
    private String password = "admin123";
    private final List<Employee> employees = new ArrayList<>();

    public boolean validateLogin(String username, String enteredPassword) {
        return "admin".equals(username) && password.equals(enteredPassword);
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword)) {
            return false;
        }
        password = newPassword;
        return true;
    }

    public void addEmployee(String id, String name, String department) {
        employees.add(new Employee(id, name, department));
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }
}
