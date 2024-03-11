import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class EmployeeDetailsFrame extends JFrame {
    private JComboBox<String> monthComboBox;
    private JButton computeButton;
    private JTextArea detailsTextArea;

    @SuppressWarnings("unused")
	private String[] employeeDetails;

    public EmployeeDetailsFrame(String[] employeeDetails) {
        this.employeeDetails = employeeDetails;

        setTitle("Employee Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Employee Number:"));
        panel.add(new JLabel(employeeDetails[0]));

        panel.add(new JLabel("Last Name:"));
        panel.add(new JLabel(employeeDetails[1]));

        panel.add(new JLabel("First Name:"));
        panel.add(new JLabel(employeeDetails[2]));

        panel.add(new JLabel("Select Month:"));
        monthComboBox = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"});
        panel.add(monthComboBox);

        detailsTextArea = new JTextArea();
        panel.add(detailsTextArea);

        computeButton = new JButton("Compute");
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeSalary();
            }
        });
        panel.add(computeButton);

        add(panel);
    }

    private void computeSalary() {
        // Implement your salary computation logic here based on the selected month
        // You can use the employeeDetails array for employee-specific data

        String selectedMonth = (String) monthComboBox.getSelectedItem();
        detailsTextArea.setText("Salary details for " + selectedMonth + ":\n");
        // Add additional details or computation logic as needed
    }
}
