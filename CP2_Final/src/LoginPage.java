import javax.swing.*;
import pkg.CSVReaderWriter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createAccountButton; // Declare as instance variable

    public LoginPage() {
        setTitle("MotorPH - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1152, 864);
        setLocationRelativeTo(null);

        // Set fonts, Changed it to Helvetica
        Font helvetica = new Font("Helvetica", Font.PLAIN, 16);
        Font buffaloExtraBold = new Font("Buffalo Extra Bold", Font.BOLD, 36);

        // Dark mode background color
        Color darkBackground = new Color(40, 40, 40);

        // Main panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(darkBackground);

        // Logo dito :D
        JLabel logoLabel = new JLabel("MotorPH");
        logoLabel.setFont(buffaloExtraBold);
        logoLabel.setForeground(Color.WHITE);
        GridBagConstraints logoConstraints = new GridBagConstraints();
        logoConstraints.gridx = 0;
        logoConstraints.gridy = 0;
        logoConstraints.gridwidth = 2;
        logoConstraints.insets = new Insets(50, 0, 30, 0); // Padding
        panel.add(logoLabel, logoConstraints);

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameLabel.setFont(helvetica);
        passwordLabel.setFont(helvetica);
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);

        // Text fields
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        usernameField.setFont(helvetica);
        passwordField.setFont(helvetica);

        // Buttons
        JButton loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account"); // Initialize instance variable
        loginButton.setFont(helvetica);
        createAccountButton.setFont(helvetica);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Updated createAccount() method to handle button click
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccountPage createAccountPage = new CreateAccountPage();
                createAccountPage.setVisible(true);
                
                // Close the login page
                dispose();
            }
        });

        // Set button colors
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setForeground(Color.WHITE);
        createAccountButton.setBackground(Color.DARK_GRAY);
        createAccountButton.setForeground(Color.WHITE);

        // Add components to panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0); // Padding

        panel.add(usernameLabel, constraints);
        constraints.gridy++;
        panel.add(usernameField, constraints);
        constraints.gridy++;
        panel.add(passwordLabel, constraints);
        constraints.gridy++;
        panel.add(passwordField, constraints);
        constraints.gridy++;
        panel.add(loginButton, constraints);
        constraints.gridy++;
        panel.add(createAccountButton, constraints);

        // Set panel to content pane
        getContentPane().setBackground(darkBackground);
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

            // Transition to the HomePage from login
            HomePage homePage = new HomePage(enteredUsername);
            homePage.setVisible(true);

            // Dispose of the LoginPage
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username or password. Please try again.");
            // Clear the password field here
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
