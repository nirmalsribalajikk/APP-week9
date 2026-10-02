public class Student {
    private String name;
    private final double[] marks = new double[3];
    private double total;
    private double average;
    private char grade;

    public void calculateResult(String name, double mark1, double mark2, double mark3) {
        validateMark(mark1);
        validateMark(mark2);
        validateMark(mark3);

        this.name = name.trim();
        marks[0] = mark1;
        marks[1] = mark2;
        marks[2] = mark3;
        total = mark1 + mark2 + mark3;
        average = total / marks.length;
        grade = calculateGrade(average);
    }

    private void validateMark(double mark) {
        if (Double.isNaN(mark) || Double.isInfinite(mark) || mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }
    }

    private char calculateGrade(double average) {
        if (average >= 90) {
            return 'A';
        } else if (average >= 75) {
            return 'B';
        } else if (average >= 60) {
            return 'C';
        } else if (average >= 50) {
            return 'D';
        }
        return 'F';
    }

    public String getName() {
        return name;
    }

    public double getTotal() {
        return total;
    }

    public double getAverage() {
        return average;
    }

    public char getGrade() {
        return grade;
    }
}
