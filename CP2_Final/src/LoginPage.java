import javax.swing.*;
import pkg.CSVReaderWriter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@SuppressWarnings("serial")
public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createAccountButton;

    public LoginPage() {
        setTitle("MotorPH - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1152, 864);
        setLocationRelativeTo(null);

        Font helvetica = new Font("Helvetica", Font.PLAIN, 16);
        Font buffaloExtraBold = new Font("Buffalo Extra Bold", Font.BOLD, 36);

        Color darkBackground = new Color(40, 40, 40);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(darkBackground);

        JLabel logoLabel = new JLabel("MotorPH");
        logoLabel.setFont(buffaloExtraBold);
        logoLabel.setForeground(Color.WHITE);
        GridBagConstraints logoConstraints = new GridBagConstraints();
        logoConstraints.gridx = 0;
        logoConstraints.gridy = 0;
        logoConstraints.gridwidth = 2;
        logoConstraints.insets = new Insets(50, 0, 30, 0);
        panel.add(logoLabel, logoConstraints);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameLabel.setFont(helvetica);
        passwordLabel.setFont(helvetica);
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        usernameField.setFont(helvetica);
        passwordField.setFont(helvetica);

        JButton loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");
        loginButton.setFont(helvetica);
        createAccountButton.setFont(helvetica);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccountPage createAccountPage = new CreateAccountPage();
                createAccountPage.setVisible(true);
                dispose();
            }
        });

        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setForeground(Color.WHITE);
        createAccountButton.setBackground(Color.DARK_GRAY);
        createAccountButton.setForeground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

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

            HomePage homePage = new HomePage(enteredUsername);
            homePage.setVisible(true);

            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username or password. Please try again.");
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
