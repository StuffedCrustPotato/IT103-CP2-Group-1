import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import pkg.CSVReaderWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewEmployeePage extends JFrame {
    private JTable employeeTable;
    private JTextField searchField;

    public ViewEmployeePage() {
        setTitle("Employee App - View Employees");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600); // Adjusted the size
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });
        searchPanel.add(searchButton);
        panel.add(searchPanel, BorderLayout.NORTH);

        List<String[]> employeeData = CSVReaderWriter.readEmployees();

        String[] columnNames = {"Employee #", "Last Name", "First Name", "Birthday", "Phone Number",
                "SSS#", "Philhealth #", "TIN #", "Pag-ibig #", "Address"};

        DefaultTableModel tableModel = new DefaultTableModel(employeeData.toArray(new Object[0][0]), columnNames);
        employeeTable = new JTable(tableModel);
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Enable horizontal scroll

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Adding horizontal scroll wheel
        JScrollPane horizontalScroll = new JScrollPane(employeeTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(horizontalScroll, BorderLayout.CENTER);

        // Buttons for Add, Update, Delete
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void searchEmployee() {
        String searchTerm = searchField.getText().toLowerCase();

        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0); // Clear the table

        List<String[]> employeeData = CSVReaderWriter.readEmployees();

        for (String[] employee : employeeData) {
            for (String field : employee) {
                if (field.toLowerCase().contains(searchTerm)) {
                    model.addRow(employee);
                    break; // Add the row once and move to the next employee
                }
            }
        }
    }

    private void addEmployee() {
        // Create a dialog for adding employee details
        JDialog addEmployeeDialog = new JDialog(this, "Add Employee", true);
        addEmployeeDialog.setSize(400, 300);
        addEmployeeDialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel(new GridLayout(11, 2));

        dialogPanel.add(new JLabel("Employee #"));
        JTextField empNumberField = new JTextField();
        dialogPanel.add(empNumberField);

        dialogPanel.add(new JLabel("Last Name"));
        JTextField lastNameField = new JTextField();
        dialogPanel.add(lastNameField);

        dialogPanel.add(new JLabel("First Name"));
        JTextField firstNameField = new JTextField();
        dialogPanel.add(firstNameField);

        dialogPanel.add(new JLabel("Birthday"));
        JTextField birthdayField = new JTextField();
        dialogPanel.add(birthdayField);

        dialogPanel.add(new JLabel("Phone Number"));
        JTextField phoneNumberField = new JTextField();
        dialogPanel.add(phoneNumberField);

        dialogPanel.add(new JLabel("SSS#"));
        JTextField sssField = new JTextField();
        dialogPanel.add(sssField);

        dialogPanel.add(new JLabel("Philhealth #"));
        JTextField philhealthField = new JTextField();
        dialogPanel.add(philhealthField);

        dialogPanel.add(new JLabel("TIN #"));
        JTextField tinField = new JTextField();
        dialogPanel.add(tinField);

        dialogPanel.add(new JLabel("Pag-ibig #"));
        JTextField pagibigField = new JTextField();
        dialogPanel.add(pagibigField);

        dialogPanel.add(new JLabel("Address"));
        JTextField addressField = new JTextField();
        dialogPanel.add(addressField);

        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve values from text fields
                String empNumber = empNumberField.getText();
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String birthday = birthdayField.getText();
                String phoneNumber = phoneNumberField.getText();
                String sss = sssField.getText();
                String philhealth = philhealthField.getText();
                String tin = tinField.getText();
                String pagibig = pagibigField.getText();
                String address = addressField.getText();

                // Add a new row to the table
                DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
                model.addRow(new Object[]{empNumber, lastName, firstName, birthday, phoneNumber, sss, philhealth, tin, pagibig, address});

                // Save changes to CSV
                saveChangesToCSV(model);

                // Close the dialog
                addEmployeeDialog.dispose();
            }
        });
        dialogPanel.add(addButton);

        addEmployeeDialog.add(dialogPanel);
        addEmployeeDialog.setVisible(true);
    }

    private void updateEmployee() {
        int selectedRow = employeeTable.getSelectedRow();

        if (selectedRow >= 0) {
            // Create a dialog for updating employee details
            JDialog updateEmployeeDialog = new JDialog(this, "Update Employee", true);
            updateEmployeeDialog.setSize(400, 300);
            updateEmployeeDialog.setLocationRelativeTo(this);

            JPanel dialogPanel = new JPanel(new GridLayout(11, 2));

            // Get the values from the selected row
            String empNumber = (String) employeeTable.getValueAt(selectedRow, 0);
            String lastName = (String) employeeTable.getValueAt(selectedRow, 1);
            String firstName = (String) employeeTable.getValueAt(selectedRow, 2);
            String birthday = (String) employeeTable.getValueAt(selectedRow, 3);
            String phoneNumber = (String) employeeTable.getValueAt(selectedRow, 4);
            String sss = (String) employeeTable.getValueAt(selectedRow, 5);
            String philhealth = (String) employeeTable.getValueAt(selectedRow, 6);
            String tin = (String) employeeTable.getValueAt(selectedRow, 7);
            String pagibig = (String) employeeTable.getValueAt(selectedRow, 8);
            String address = (String) employeeTable.getValueAt(selectedRow, 9);

            // Add labels and text fields to the dialog
            dialogPanel.add(new JLabel("Employee #"));
            JTextField empNumberField = new JTextField(empNumber);
            dialogPanel.add(empNumberField);

            dialogPanel.add(new JLabel("Last Name"));
            JTextField lastNameField = new JTextField(lastName);
            dialogPanel.add(lastNameField);

            dialogPanel.add(new JLabel("First Name"));
            JTextField firstNameField = new JTextField(firstName);
            dialogPanel.add(firstNameField);

            dialogPanel.add(new JLabel("Birthday"));
            JTextField birthdayField = new JTextField(birthday);
            dialogPanel.add(birthdayField);

            dialogPanel.add(new JLabel("Phone Number"));
            JTextField phoneNumberField = new JTextField(phoneNumber);
            dialogPanel.add(phoneNumberField);

            dialogPanel.add(new JLabel("SSS#"));
            JTextField sssField = new JTextField(sss);
            dialogPanel.add(sssField);

            dialogPanel.add(new JLabel("Philhealth #"));
            JTextField philhealthField = new JTextField(philhealth);
            dialogPanel.add(philhealthField);

            dialogPanel.add(new JLabel("TIN #"));
            JTextField tinField = new JTextField(tin);
            dialogPanel.add(tinField);

            dialogPanel.add(new JLabel("Pag-ibig #"));
            JTextField pagibigField = new JTextField(pagibig);
            dialogPanel.add(pagibigField);

            dialogPanel.add(new JLabel("Address"));
            JTextField addressField = new JTextField(address);
            dialogPanel.add(addressField);

            JButton updateButton = new JButton("Update Employee");
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Update values in the selected row
                    employeeTable.setValueAt(empNumberField.getText(), selectedRow, 0);
                    employeeTable.setValueAt(lastNameField.getText(), selectedRow, 1);
                    employeeTable.setValueAt(firstNameField.getText(), selectedRow, 2);
                    employeeTable.setValueAt(birthdayField.getText(), selectedRow, 3);
                    employeeTable.setValueAt(phoneNumberField.getText(), selectedRow, 4);
                    employeeTable.setValueAt(sssField.getText(), selectedRow, 5);
                    employeeTable.setValueAt(philhealthField.getText(), selectedRow, 6);
                    employeeTable.setValueAt(tinField.getText(), selectedRow, 7);
                    employeeTable.setValueAt(pagibigField.getText(), selectedRow, 8);
                    employeeTable.setValueAt(addressField.getText(), selectedRow, 9);

                    // Save changes to CSV
                    saveChangesToCSV((DefaultTableModel) employeeTable.getModel());

                    // Close the dialog
                    updateEmployeeDialog.dispose();
                }
            });
            dialogPanel.add(updateButton);

            updateEmployeeDialog.add(dialogPanel);
            updateEmployeeDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a record first.");
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();

        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
            model.removeRow(selectedRow);

            // Save changes to CSV
            saveChangesToCSV(model);

            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a record first.");
        }
    }

    private void saveChangesToCSV(DefaultTableModel model) {
        // Save the updated data to CSV
        List<String[]> data = CSVReaderWriter.readEmployees();
        data.clear(); // Clear the existing data

        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] rowData = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                rowData[j] = model.getValueAt(i, j);
            }
            // Convert Object[] to String[]
            String[] rowStringArray = new String[rowData.length];
            for (int k = 0; k < rowData.length; k++) {
                rowStringArray[k] = String.valueOf(rowData[k]);
            }
            data.add(rowStringArray);
        }

        CSVReaderWriter.writeEmployees(data);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewEmployeePage viewEmployeePage = new ViewEmployeePage();
            viewEmployeePage.setVisible(true);
        });
    }
}
