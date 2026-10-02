import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class EmployeePortalView extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JMenuItem addEmployeeItem = new JMenuItem("Add Employee");
    private final JMenuItem viewEmployeeItem = new JMenuItem("View Employee");
    private final JMenuItem changePasswordItem = new JMenuItem("Change Password");
    private final JMenuItem logoutItem = new JMenuItem("Logout");
    private final JMenuItem exitApplicationItem = new JMenuItem("Exit Application");

    public EmployeePortalView() {
        setTitle("Employee Management Portal");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setJMenuBar(createMenuBar());
        add(new JLabel("Welcome to the Employee Management Portal", JLabel.CENTER));
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu employeeMenu = new JMenu("Employee");
        employeeMenu.add(addEmployeeItem);
        employeeMenu.add(viewEmployeeItem);

        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.add(changePasswordItem);

        JMenu exitMenu = new JMenu("Exit");
        exitMenu.add(logoutItem);
        exitMenu.add(exitApplicationItem);

        menuBar.add(employeeMenu);
        menuBar.add(toolsMenu);
        menuBar.add(exitMenu);
        return menuBar;
    }

    public JMenuItem getAddEmployeeItem() {
        return addEmployeeItem;
    }

    public JMenuItem getViewEmployeeItem() {
        return viewEmployeeItem;
    }

    public JMenuItem getChangePasswordItem() {
        return changePasswordItem;
    }

    public JMenuItem getLogoutItem() {
        return logoutItem;
    }

    public JMenuItem getExitApplicationItem() {
        return exitApplicationItem;
    }

    public Employee getEmployeeInput() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Employee Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);

        int option = JOptionPane.showConfirmDialog(
                this, panel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) {
            return null;
        }

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        if (id.isEmpty() || name.isEmpty() || department.isEmpty()) {
            showMessage("All employee fields are required.", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new Employee(id, name, department);
    }

    public String[] getPasswordChangeInput() {
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Old Password:"));
        panel.add(oldPasswordField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);

        int option = JOptionPane.showConfirmDialog(
                this, panel, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) {
            return null;
        }
        return new String[] {
                new String(oldPasswordField.getPassword()),
                new String(newPasswordField.getPassword()),
                new String(confirmPasswordField.getPassword())
        };
    }

    public void showEmployees(java.util.List<Employee> employees) {
        if (employees.isEmpty()) {
            showMessage("No employees have been added.", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder employeeText = new StringBuilder();
        for (Employee employee : employees) {
            employeeText.append(employee).append('\n');
        }
        showMessage(employeeText.toString(), JOptionPane.INFORMATION_MESSAGE);
    }

    public void showMessage(String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, "Employee Portal", messageType);
    }
}
