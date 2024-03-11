import javax.swing.*;

import pkg.CSVReaderWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Employee App - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
    }

    private void login() {
        String enteredUsername = usernameField.getText();
        char[] enteredPassword = passwordField.getPassword();
        String enteredPasswordString = new String(enteredPassword);

        List<String[]> authorizedAccounts = CSVReaderWriter.readAuthorizedAccounts();

        boolean isAuthenticated = false;

        for (String[] accountInfo : authorizedAccounts) {
            String storedUsername = accountInfo[0];
            String storedPassword = accountInfo[1];

            if (enteredUsername.equals(storedUsername) && enteredPasswordString.equals(storedPassword)) {
                isAuthenticated = true;
                break;
            }
        }

        if (isAuthenticated) {
            JOptionPane.showMessageDialog(this, "Login successful!");

            // Transition to the HomePage
            HomePage homePage = new HomePage(enteredUsername);
            homePage.setVisible(true);

            // Dispose of the LoginPage
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username or password. Please try again.");
            // Clear the password field
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}
