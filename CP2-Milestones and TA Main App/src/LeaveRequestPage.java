import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaveRequestPage extends JFrame {
    private JTable employeeTable;
    private JTextField nameTextField, positionTextField;
    private JButton deleteButton, addButton; // Added addButton

    public LeaveRequestPage() {
        setTitle("Employee App - Leave Request");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Employee data for the sample
        Object[][] data = {
                {"John Doe", "Developer"},
                {"Jane Smith", "Manager"},
                {"Bob Johnson", "Designer"}
        };

        String[] columnNames = {"Name", "Position"};

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        employeeTable = new JTable(tableModel);

        employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateEmployeeDetails();
            }
        });

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel(new GridLayout(3, 2));

        detailsPanel.add(new JLabel("Name:"));
        nameTextField = new JTextField();
        detailsPanel.add(nameTextField);

        detailsPanel.add(new JLabel("Position:"));
        positionTextField = new JTextField();
        detailsPanel.add(positionTextField);

        addButton = new JButton("Add Employee"); // Added addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        deleteButton = new JButton("Delete Employee");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedEmployee();
            }
        });

        detailsPanel.add(addButton); // Added addButton
        detailsPanel.add(deleteButton);

        panel.add(detailsPanel, BorderLayout.CENTER);

        add(panel);
    }

    private void updateEmployeeDetails() {
        int selectedRow = employeeTable.getSelectedRow();

        if (selectedRow >= 0) {
            String name = (String) employeeTable.getValueAt(selectedRow, 0);
            String position = (String) employeeTable.getValueAt(selectedRow, 1);

            nameTextField.setText(name);
            positionTextField.setText(position);

            deleteButton.setEnabled(true);
        } else {
            nameTextField.setText("");
            positionTextField.setText("");
            deleteButton.setEnabled(false);
        }
    }

    private void deleteSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();

        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
            model.removeRow(selectedRow);

            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
        }

        // Reset the text fields and disable the delete button
        updateEmployeeDetails();
    }

    private void addEmployee() {
        String name = nameTextField.getText();
        String position = positionTextField.getText();

        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.addRow(new Object[]{name, position});

        JOptionPane.showMessageDialog(this, "Employee added successfully!");

        // Reset the text fields and disable the delete button
        updateEmployeeDetails();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LeaveRequestPage leaveRequestPage = new LeaveRequestPage();
            leaveRequestPage.setVisible(true);
        });
    }
}
