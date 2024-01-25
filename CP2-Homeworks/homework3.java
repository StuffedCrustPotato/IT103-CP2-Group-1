
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homework3 extends JFrame {
    private JTextField milestone1Field;
    private JTextField milestone2Field;
    private JTextField terminalField;

    public homework3() {
        // Set up the frame
        setTitle("Milestone Calculator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel milestone1Label = new JLabel("Milestone 1 (<=25%): ");
        JLabel milestone2Label = new JLabel("Milestone 2 (<=40%): ");
        JLabel terminalLabel = new JLabel("Terminal Assessment (<=35%): ");

        milestone1Field = new JTextField(10);
        milestone2Field = new JTextField(10);
        terminalField = new JTextField(10);

        JButton calculateButton = new JButton("Calculate Grade");

        // Set layout
        setLayout(new GridLayout(4, 2));

        // Add components to the frame
        add(milestone1Label);
        add(milestone1Field);
        add(milestone2Label);
        add(milestone2Field);
        add(terminalLabel);
        add(terminalField);
        add(new JLabel()); // Placeholder
        add(calculateButton);

        // Add action listener to the button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGrade();
            }
        });
    }

    private void calculateGrade() {
        try {
            double milestone1 = validateAndParse(milestone1Field.getText(), 25);
            double milestone2 = validateAndParse(milestone2Field.getText(), 40);
            double terminal = validateAndParse(terminalField.getText(), 35);

            double totalGrade = milestone1 + milestone2 + terminal;

            JOptionPane.showMessageDialog(this, "Total Grade: " + totalGrade, "Result", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //if inputs are out of the ranges per line item
    private double validateAndParse(String input, int maxPoints) {
        double value = Double.parseDouble(input);

        if (value < 0 || value > maxPoints) {
            throw new IllegalArgumentException("Value must be between 0 and " + maxPoints + ".");
        }

        return value;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new homework3().setVisible(true);
            }
        });
    }
}
