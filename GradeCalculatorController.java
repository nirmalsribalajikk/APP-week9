public class GradeCalculatorController {
    private final Student model;
    private final GradeCalculatorView view;

    public GradeCalculatorController(Student model, GradeCalculatorView view) {
        this.model = model;
        this.view = view;
        view.getCalculateButton().addActionListener(event -> calculateResult());
    }

    private void calculateResult() {
        String name = view.getStudentName().trim();
        if (name.isEmpty()) {
            view.showError("Student name cannot be empty.");
            return;
        }

        try {
            double subject1 = parseMarks(view.getSubject1Marks(), "Subject 1");
            double subject2 = parseMarks(view.getSubject2Marks(), "Subject 2");
            double subject3 = parseMarks(view.getSubject3Marks(), "Subject 3");
            model.calculateResult(name, subject1, subject2, subject3);
            view.displayResult(model);
        } catch (NumberFormatException exception) {
            view.showError("Enter valid numeric marks for all three subjects.");
        } catch (IllegalArgumentException exception) {
            view.showError(exception.getMessage());
        }
    }

    private double parseMarks(String value, String subjectName) {
        if (value.trim().isEmpty()) {
            throw new NumberFormatException(subjectName + " marks are missing.");
        }
        return Double.parseDouble(value.trim());
    }
}
