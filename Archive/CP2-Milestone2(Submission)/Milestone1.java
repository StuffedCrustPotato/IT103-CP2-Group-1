

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Milestone1 extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private List<String[]> authorizedAccounts;

    public Milestone1() {
        // Set up the frame
        setTitle("MotorPH Employee Central");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");

        // Set layout
        setLayout(new GridLayout(3, 2));

        // Add components to the frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Placeholder
        add(loginButton);

        // Load authorized accounts from CSV
        loadAuthorizedAccounts();

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Display the frame
        setVisible(true);
    }

    private void loadAuthorizedAccounts() {
        authorizedAccounts = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("EmpDeetsNew.csv"))) {
            String[] line;
            // Read header line
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                authorizedAccounts.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        String enteredUsername = usernameField.getText().trim();
        String enteredPassword = new String(passwordField.getPassword()).trim();

        System.out.println("Entered username: " + enteredUsername);
        System.out.println("Entered password: " + enteredPassword);

        for (String[] account : authorizedAccounts) {
            // Check if the entered username and password match
            if (account[0].equals(enteredUsername) && account[1].equals(enteredPassword)) {
                // Successful login
                String firstName = account[2];
                String lastName = account[3];
                showMainApplication(firstName, lastName);
                return;
            }
        }

        // Unsuccessful login
        System.out.println("Login failed. No match found.");
        JOptionPane.showMessageDialog(this, "Incorrect login credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMainApplication(String firstName, String lastName) {
        // Implement the main application window here
        // Display the welcome message and proceed to the next steps
        // You can either hide the login window or close it
        JOptionPane.showMessageDialog(this, "Welcome " + firstName + " " + lastName + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        // Proceed to the main application window
        // Add the code for the main application window based on your requirements
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Milestone1::new);
    }
}
