import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private String username;

    public HomePage(String username) {
        this.username = username;

        setTitle("Employee App - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        JButton leaveRequestButton = new JButton("Leave Request");
        JButton calendarButton = new JButton("Calendar");
        JButton viewEmployeesButton = new JButton("View Employees");
        JButton logoutButton = new JButton("Logout");

        leaveRequestButton.addActionListener(e -> openLeaveRequest());
        calendarButton.addActionListener(e -> openCalendar());
        viewEmployeesButton.addActionListener(e -> openViewEmployees());
        logoutButton.addActionListener(e -> logout());

        panel.setLayout(new GridLayout(5, 1));
        panel.add(welcomeLabel);
        panel.add(leaveRequestButton);
        panel.add(calendarButton);
        panel.add(viewEmployeesButton);
        panel.add(logoutButton);

        add(panel);
    }

    private void openLeaveRequest() {
        LeaveRequestPage leaveRequestPage = new LeaveRequestPage();
        leaveRequestPage.setVisible(true);
    }

    private void openCalendar() {
        CalendarPage calendarPage = new CalendarPage();
        calendarPage.setVisible(true);
    }

    private void openViewEmployees() {
        ViewEmployeePage viewEmployeesPage = new ViewEmployeePage();
        viewEmployeesPage.setVisible(true);
    }

    private void logout() {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
        dispose(); // Close the home page
    }
}
