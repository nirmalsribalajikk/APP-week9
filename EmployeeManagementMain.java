import javax.swing.SwingUtilities;

public class EmployeeManagementMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementModel model = new EmployeeManagementModel();
            EmployeeLoginView loginView = new EmployeeLoginView();
            EmployeePortalView portalView = new EmployeePortalView();
            new EmployeeManagementController(model, loginView, portalView);
            loginView.setVisible(true);
        });
    }
}
