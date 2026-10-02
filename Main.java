import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Student model = new Student();
            GradeCalculatorView view = new GradeCalculatorView();
            new GradeCalculatorController(model, view);
            view.setVisible(true);
        });
    }
}
