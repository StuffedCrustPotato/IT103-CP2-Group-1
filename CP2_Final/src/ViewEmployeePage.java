import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import pkg.CSVReaderWriter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
// This part is so hard to do
@SuppressWarnings("serial")
public class ViewEmployeePage extends JFrame {
    private JTable employeeTable;
    private JTextField searchField;

    public ViewEmployeePage() {
        setTitle("Employee App - View Employees");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1152, 864);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center alignment
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

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HomePage homepage = new HomePage(getTitle());
                homepage.setVisible(true);
            }
        });
        searchPanel.add(backButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        List<String[]> employeeData = CSVReaderWriter.readEmployees();

        String[] columnNames = {"Employee #", "Last Name", "First Name", "Birthday", "Phone Number",
                "SSS#", "Philhealth #", "TIN #", "Pag-ibig #", "Address"};

        DefaultTableModel tableModel = new DefaultTableModel(employeeData.toArray(new Object[0][0]), columnNames);
        employeeTable = new JTable(tableModel);
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(9).setPreferredWidth(300);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(7).setPreferredWidth(120);
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(90);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

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
    //For the Search Bar
    private void searchEmployee() {
        String searchTerm = searchField.getText().toLowerCase();

        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0);

        List<String[]> employeeData = CSVReaderWriter.readEmployees();

        for (String[] employee : employeeData) {
            for (String field : employee) {
                if (field.toLowerCase().contains(searchTerm)) {
                    model.addRow(employee);
                    break;
                }
            }
        }
    }

    private void addEmployee() {
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

        dialogPanel.add(new JLabel("Birthday (mm/dd/year)"));
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
                // This is the Error Dialogue :)
                if (lastName.isEmpty() || firstName.isEmpty()) {
                    showError(addEmployeeDialog, "Please enter both Last Name and First Name.");
                    return;
                }

                if (!isValidDate(birthday)) {
                    showError(addEmployeeDialog, "Please enter a valid date in mm/dd/year format.");
                    return;
                }

                if (!isValidPhoneNumber(phoneNumber)) {
                    showError(addEmployeeDialog, "Please enter a valid Phone Number in XXX-XXX-XXX format.");
                    return;
                }

                if (!isValidSSS(sss)) {
                    showError(addEmployeeDialog, "Please enter a valid SSS# in XX-XXXXXXX-X format.");
                    return;
                }

                if (!isValidNumberFormat(philhealth)) {
                    showError(addEmployeeDialog, "Please enter a valid Philhealth # in X.XXE+XX format.");
                    return;
                }

                if (!isValidTIN(tin)) {
                    showError(addEmployeeDialog, "Please enter a valid TIN # in XXX-XXX-XXX-XXX format.");
                    return;
                }

                if (!isValidNumberFormat(pagibig)) {
                    showError(addEmployeeDialog, "Please enter a valid Pag-ibig # in X.XXE+XX format.");
                    return;
                }
                
                if (address.isEmpty()) {
                    showError(addEmployeeDialog, "Please enter Address.");
                    return;
                }


                DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
                model.addRow(new Object[]{empNumber, lastName, firstName, birthday, phoneNumber, sss, philhealth, tin, pagibig, address});
                saveChangesToCSV(model);
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
            JDialog updateEmployeeDialog = new JDialog(this, "Update Employee", true);
            updateEmployeeDialog.setSize(400, 300);
            updateEmployeeDialog.setLocationRelativeTo(this);

            JPanel dialogPanel = new JPanel(new GridLayout(11, 2));

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

            dialogPanel.add(new JLabel("Employee #"));
            JTextField empNumberField = new JTextField(empNumber);
            dialogPanel.add(empNumberField);

            dialogPanel.add(new JLabel("Last Name"));
            JTextField lastNameField = new JTextField(lastName);
            dialogPanel.add(lastNameField);

            dialogPanel.add(new JLabel("First Name"));
            JTextField firstNameField = new JTextField(firstName);
            dialogPanel.add(firstNameField);

            dialogPanel.add(new JLabel("Birthday (mm/dd/year)"));
            JTextField birthdayField = new JTextField(birthday);
            dialogPanel.add(birthdayField);

            dialogPanel.add(new JLabel("Phone Number (XXX-XXX-XXX)"));
            JTextField phoneNumberField = new JTextField(phoneNumber);
            dialogPanel.add(phoneNumberField);

            dialogPanel.add(new JLabel("SSS# (00-0000000-0)"));
            JTextField sssField = new JTextField(sss);
            dialogPanel.add(sssField);

            dialogPanel.add(new JLabel("Philhealth # (X.XXE+XX)"));
            JTextField philhealthField = new JTextField(philhealth);
            dialogPanel.add(philhealthField);

            dialogPanel.add(new JLabel("TIN # (000-000-000-000)"));
            JTextField tinField = new JTextField(tin);
            dialogPanel.add(tinField);

            dialogPanel.add(new JLabel("Pag-ibig # (X.XXE+XX)"));
            JTextField pagibigField = new JTextField(pagibig);
            dialogPanel.add(pagibigField);

            dialogPanel.add(new JLabel("Address"));
            JTextField addressField = new JTextField(address);
            dialogPanel.add(addressField);

            JButton updateButton = new JButton("Update Employee");
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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

                    saveChangesToCSV((DefaultTableModel) employeeTable.getModel());
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

            saveChangesToCSV(model);

            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a record first.");
        }
    }
    // The CSV for permanent recording of the data on the Table :O
    private void saveChangesToCSV(DefaultTableModel model) {
        List<String[]> data = CSVReaderWriter.readEmployees();
        data.clear();

        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] rowData = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                rowData[j] = model.getValueAt(i, j);
            }
            String[] rowStringArray = new String[rowData.length];
            for (int k = 0; k < rowData.length; k++) {
                rowStringArray[k] = String.valueOf(rowData[k]);
            }
            data.add(rowStringArray);
        }

        CSVReaderWriter.writeEmployees(data);
    }

    private void showError(Component parentComponent, String errorMessage) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isValidDate(String date) {
        if (date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            String[] parts = date.split("/");
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return month >= 1 && month <= 12 && day >= 1 && day <= 31 && year >= 1900 && year <= 9999;
        }
        return false;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{3}-\\d{3}-\\d{3}");
    }

    private boolean isValidSSS(String sss) {
        return sss.matches("\\d{2}-\\d{7}-\\d");
    }

    private boolean isValidTIN(String tin) {
        return tin.matches("\\d{3}-\\d{3}-\\d{3}-\\d{3}");
    }

    private boolean isValidNumberFormat(String number) {
        return number.matches("\\d{1,}\\.\\d{1,}E[+-]\\d{2}");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewEmployeePage viewEmployeePage = new ViewEmployeePage();
            viewEmployeePage.setVisible(true);
        });
    }
}
//Joaquin was here :P