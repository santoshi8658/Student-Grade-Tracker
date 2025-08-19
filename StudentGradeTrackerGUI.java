import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea outputArea;
    private ArrayList<Student> students = new ArrayList<>();

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker (GUI)");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Student Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Grade:"));
        gradeField = new JTextField(5);
        add(gradeField);

        JButton addButton = new JButton("Add Student");
        JButton reportButton = new JButton("Generate Report");

        add(addButton);
        add(reportButton);

        outputArea = new JTextArea(15, 30);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        addButton.addActionListener(e -> addStudent());
        reportButton.addActionListener(e -> generateReport());
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String gradeStr = gradeField.getText().trim();

        if (name.isEmpty() || gradeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeStr);
            students.add(new Student(name, grade));
            outputArea.append("Added: " + name + " - " + grade + "\n");
            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade input.");
        }
    }

    private void generateReport() {
        if (students.isEmpty()) {
            outputArea.setText("No students to report.\n");
            return;
        }

        double total = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        StringBuilder report = new StringBuilder("=== Summary Report ===\n");
        for (Student s : students) {
            report.append(s.name).append(" - ").append(s.grade).append("\n");
            total += s.grade;

            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = total / students.size();

        report.append("\nAverage Grade: ").append(String.format("%.2f", average));
        report.append("\nHighest Grade: ").append(highest).append(" (").append(topStudent).append(")");
        report.append("\nLowest Grade: ").append(lowest).append(" (").append(lowStudent).append(")\n");

        outputArea.setText(report.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}