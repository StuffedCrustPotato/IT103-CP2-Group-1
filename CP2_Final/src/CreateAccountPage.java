import javax.swing.*;

import pkg.CSVReaderWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class CreateAccountPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public CreateAccountPage() {
        setTitle("MotorPH - Create Account");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1152, 864);
        setLocationRelativeTo(null);

        // Set fonts
        Font helvetica = new Font("Helvetica", Font.PLAIN, 16);

        // Dark mode background color
        Color darkBackground = new Color(40, 40, 40);

        // Main panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(darkBackground);

        // Labels
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        usernameLabel.setFont(helvetica);
        passwordLabel.setFont(helvetica);
        confirmPasswordLabel.setFont(helvetica);
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setForeground(Color.WHITE);

        // Text fields
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        usernameField.setFont(helvetica);
        passwordField.setFont(helvetica);
        confirmPasswordField.setFont(helvetica);

        // Buttons
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(helvetica);

        JButton backButton = new JButton("Back");
        backButton.setFont(helvetica);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount(); // Modified method call
            }
        });

        // Set button colors
        createAccountButton.setBackground(Color.DARK_GRAY);
        createAccountButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.WHITE);

        // Add components to panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(20, 0, 10, 0); // Padding
        panel.add(titleLabel, constraints);

        constraints.gridy++;
        panel.add(usernameLabel, constraints);
        constraints.gridy++;
        panel.add(usernameField, constraints);
        constraints.gridy++;
        panel.add(passwordLabel, constraints);
        constraints.gridy++;
        panel.add(passwordField, constraints);
        constraints.gridy++;
        panel.add(confirmPasswordLabel, constraints);
        constraints.gridy++;
        panel.add(confirmPasswordField, constraints);
        constraints.gridy++;
        constraints.insets = new Insets(20, 0, 0, 0); // Padding for button
        panel.add(createAccountButton, constraints);
        constraints.gridy++;
        panel.add(backButton, constraints);

        // Set panel to content pane
        getContentPane().setBackground(darkBackground);
        add(panel);
    }

    private void createAccount() {
        String username = usernameField.getText().trim(); 
        char[] password = passwordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        String passwordString = new String(password);
        String confirmPasswordString = new String(confirmPassword);

        if (username.isEmpty() || passwordString.isEmpty() || confirmPasswordString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!passwordString.equals(confirmPasswordString)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }

        List<String[]> authorizedAccounts = CSVReaderWriter.readAuthorizedAccounts();
        for (String[] accountInfo : authorizedAccounts) {
            if (accountInfo[0].equals(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different one.");
                usernameField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
                return;
            }
        }

        // Here is the Add new account 
        String[] newAccount = {username, passwordString};
        authorizedAccounts.add(newAccount);
        CSVReaderWriter.writeAuthorizedAccounts(authorizedAccounts);

        JOptionPane.showMessageDialog(this, "Account created successfully!");
        
        // Close the current window (ez)
        dispose();
        
        // Show the login page again of courese lol
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
