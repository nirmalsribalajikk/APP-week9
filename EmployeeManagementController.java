public class EmployeeManagementController {
    private final EmployeeManagementModel model;
    private final EmployeeLoginView loginView;
    private final EmployeePortalView portalView;

    public EmployeeManagementController(
            EmployeeManagementModel model,
            EmployeeLoginView loginView,
            EmployeePortalView portalView) {
        this.model = model;
        this.loginView = loginView;
        this.portalView = portalView;

        loginView.getLoginButton().addActionListener(event -> login());
        portalView.getAddEmployeeItem().addActionListener(event -> addEmployee());
        portalView.getViewEmployeeItem().addActionListener(
                event -> portalView.showEmployees(model.getEmployees()));
        portalView.getChangePasswordItem().addActionListener(event -> changePassword());
        portalView.getLogoutItem().addActionListener(event -> logout());
        portalView.getExitApplicationItem().addActionListener(event -> exitApplication());
    }

    private void login() {
        if (model.validateLogin(loginView.getUsername(), loginView.getPassword())) {
            loginView.setVisible(false);
            portalView.setVisible(true);
            loginView.clearPassword();
            portalView.showMessage("Login successful.", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            loginView.showMessage(
                    "Invalid username or password.",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            loginView.clearPassword();
        }
    }

    private void addEmployee() {
        Employee employee = portalView.getEmployeeInput();
        if (employee != null) {
            model.addEmployee(employee.getId(), employee.getName(), employee.getDepartment());
            portalView.showMessage("Employee added successfully.", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void changePassword() {
        String[] passwords = portalView.getPasswordChangeInput();
        if (passwords == null) {
            return;
        }
        if (passwords[1].isEmpty()) {
            portalView.showMessage("New password cannot be empty.", javax.swing.JOptionPane.ERROR_MESSAGE);
        } else if (!passwords[1].equals(passwords[2])) {
            portalView.showMessage("New password and confirm password do not match.",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } else if (!model.changePassword(passwords[0], passwords[1])) {
            portalView.showMessage("Old password is incorrect.", javax.swing.JOptionPane.ERROR_MESSAGE);
        } else {
            portalView.showMessage("Password changed successfully.",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void logout() {
        portalView.setVisible(false);
        loginView.setVisible(true);
        loginView.clearPassword();
    }

    private void exitApplication() {
        loginView.dispose();
        portalView.dispose();
        System.exit(0);
    }
}
