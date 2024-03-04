import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class LeaveRequestPage extends JFrame {
    private JTable leaveTable;
    private JTextField nameTextField, positionTextField;
    private JComboBox<String> reasonComboBox;
    private JSpinner startDateSpinner, endDateSpinner;
    private JButton addButton, deleteButton, remainingDaysButton, backButton;
    private String csvFilePath = "leave_requests.csv";

    public LeaveRequestPage() {
        setTitle("Employee App - Leave Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1152, 864);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Updated column names to include "Status"
        String[] columnNames = {"Name", "Position", "Reason", "Start Date", "End Date", "Duration", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        leaveTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(leaveTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel addPanel = new JPanel(new GridLayout(8, 2, 10, 5));
        addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addPanel.add(new JLabel("Name:"));
        nameTextField = new JTextField();
        addPanel.add(nameTextField);

        addPanel.add(new JLabel("Position:"));
        positionTextField = new JTextField();
        addPanel.add(positionTextField);

        addPanel.add(new JLabel("Reason:"));
        reasonComboBox = new JComboBox<>(new String[]{"Vacation", "Sick Leave", "Personal Leave"});
        addPanel.add(reasonComboBox);

        addPanel.add(new JLabel("Start Date:"));
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        addPanel.add(startDateSpinner);

        addPanel.add(new JLabel("End Date:"));
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        addPanel.add(endDateSpinner);

        addButton = new JButton("Add Leave Request");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLeaveRequest();
            }
        });
        addPanel.add(addButton);

        deleteButton = new JButton("Delete Leave Request");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLeaveRequest();
            }
        });
        addPanel.add(deleteButton);

        remainingDaysButton = new JButton("Remaining Days");
        remainingDaysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkRemainingDays();
            }
        });
        addPanel.add(remainingDaysButton);

        // Back button
        backButton = new JButton("Back to Home");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHomePage(); // Call method to open Home Page
            }
        });
        addPanel.add(backButton); // Add back button to panel

        panel.add(addPanel, BorderLayout.SOUTH);

        loadLeaveRequests();

        add(panel);
    }

    private void openHomePage() {
        HomePage homePage = new HomePage("username"); // Adjust as needed
        homePage.setVisible(true);
        dispose();
    }

    private void addLeaveRequest() {
        String name = nameTextField.getText();
        String position = positionTextField.getText();
        String reason = (String) reasonComboBox.getSelectedItem();
        java.util.Date startDate = (java.util.Date) startDateSpinner.getValue();
        java.util.Date endDate = (java.util.Date) endDateSpinner.getValue();

        long duration = calculateDuration(startDate, endDate);

        if (name.isEmpty() || position.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide name and position.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Set default status as "Pending"
        String status = "Pending";

        DefaultTableModel model = (DefaultTableModel) leaveTable.getModel();
        model.addRow(new Object[]{name, position, reason, startDate, endDate, duration, status});

        saveLeaveRequests();

        JOptionPane.showMessageDialog(this, "Leave request added successfully!");

        nameTextField.setText("");
        positionTextField.setText("");
        startDateSpinner.setValue(new java.util.Date());
        endDateSpinner.setValue(new java.util.Date());
    }

    private long calculateDuration(Date startDate, Date endDate) {
        long differenceInMillis = endDate.getTime() - startDate.getTime();
        long duration = differenceInMillis / (1000 * 60 * 60 * 24);

        if (differenceInMillis > 0) {
            duration += 1;
        }

        return duration;
    }

    private void deleteLeaveRequest() {
        DefaultTableModel model = (DefaultTableModel) leaveTable.getModel();
        int selectedRow = leaveTable.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
            saveLeaveRequests();
            JOptionPane.showMessageDialog(this, "Leave request deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkRemainingDays() {
        int selectedRow = leaveTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) leaveTable.getModel();
            String endDateStr = model.getValueAt(selectedRow, 4).toString();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date endDate = dateFormat.parse(endDateStr);

                Calendar currentCal = Calendar.getInstance();
                Date currentDate = currentCal.getTime();

                currentCal.set(Calendar.HOUR_OF_DAY, 0);
                currentCal.set(Calendar.MINUTE, 0);
                currentCal.set(Calendar.SECOND, 0);
                currentCal.set(Calendar.MILLISECOND, 0);
                currentDate = currentCal.getTime();

                long remainingDays = calculateRemainingDays(currentDate, endDate);
                JOptionPane.showMessageDialog(this, "Remaining days: " + remainingDays);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error parsing end date.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to check remaining days.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private long calculateRemainingDays(Date currentDate, Date endDate) {
        long differenceInMillis = endDate.getTime() - currentDate.getTime();
        long remainingDays = differenceInMillis / (1000 * 60 * 60 * 24);

        if (differenceInMillis > 0) {
            remainingDays += 0;
        }

        return remainingDays;
    }

    private void loadLeaveRequests() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            DefaultTableModel model = (DefaultTableModel) leaveTable.getModel();
            model.setRowCount(0);

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLeaveRequests() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
            DefaultTableModel model = (DefaultTableModel) leaveTable.getModel();

            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    bw.write(model.getValueAt(row, col).toString());
                    if (col < model.getColumnCount() - 1) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LeaveRequestPage().setVisible(true);
        });
    }
}
