import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.*;

public class HomePage extends JFrame {
    private String username;
    private JList<String> toDoList;
    private DefaultListModel<String> listModel;

    public HomePage(String username) {
        this.username = username;
        setTitle("MotorPH - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1152, 864);
        setLocationRelativeTo(null);

        // Set fonts
        Font helvetica = new Font("Helvetica", Font.PLAIN, 16);

        // Background color
        Color lightBackground = new Color(240, 240, 240);
        Color darkBackground = new Color(40, 40, 40);
        Color navBarColor = new Color(60, 60, 60);
        Color buttonBorderColor = navBarColor;

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(lightBackground);

        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navPanel.setBackground(navBarColor);
        navPanel.setPreferredSize(new Dimension(getWidth(), 80));

        // Logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(navBarColor);
        JLabel logoLabel = new JLabel("MOTORPH");
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);

        // Buttons
        JButton leaveRequestButton = createButton("Leave Request");
        JButton viewEmployeesButton = createButton("View Employees");
        JButton accountButton = createButton("Account");
        JButton logoutButton = createButton("Logout");

        // Set button borders
        leaveRequestButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor));
        viewEmployeesButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor));
        accountButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor));
        logoutButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor));

        // Add action listeners
        leaveRequestButton.addActionListener(e -> openPage(new LeaveRequestPage()));
        viewEmployeesButton.addActionListener(e -> openPage(new ViewEmployeePage()));
        accountButton.addActionListener(e -> openAccountSettings());
        logoutButton.addActionListener(e -> openPage(new LoginPage()));

        // Set button colors
        leaveRequestButton.setBackground(navBarColor);
        leaveRequestButton.setForeground(Color.WHITE);
        viewEmployeesButton.setBackground(navBarColor);
        viewEmployeesButton.setForeground(Color.WHITE);
        accountButton.setBackground(navBarColor);
        accountButton.setForeground(Color.WHITE);
        logoutButton.setBackground(navBarColor);
        logoutButton.setForeground(Color.WHITE);

        // Add components to navigation panel
        navPanel.add(logoPanel);
        navPanel.add(leaveRequestButton);
        navPanel.add(viewEmployeesButton);
        navPanel.add(accountButton);

        // Create a Box container for the logout button
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(logoutButton);

        // Add the Box container to the navigation panel
        navPanel.add(buttonBox);

        // Add navigation panel to main panel
        mainPanel.add(navPanel, BorderLayout.NORTH);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(lightBackground);
        tabbedPane.setPreferredSize(new Dimension(832, 624)); // Set dimensions

        // Create and add calendar panel
        JPanel calendarPanel = new CalendarPage();
        calendarPanel.setPreferredSize(new Dimension(832, 500)); // Resize events panel
        tabbedPane.addTab("Calendar", calendarPanel);

        // Create and add to-do list panel
        JPanel toDoListPanel = createToDoListPanel();
        tabbedPane.addTab("To-Do List", toDoListPanel);

        // Create and add note-taking panel
        JPanel noteTakingPanel = createNoteTakingPanel();
        tabbedPane.addTab("Note Taking", noteTakingPanel);

        // Add tabbed pane to main panel
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Create and add progress bar panel
        JPanel progressBarPanel = createProgressBarPanel();
        mainPanel.add(progressBarPanel, BorderLayout.SOUTH);

        // Set content pane
        getContentPane().setBackground(lightBackground);
        add(mainPanel);

        // Load to-do list items from CSV
        loadToDoList();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Helvetica", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openPage(JFrame page) {
        page.setVisible(true);
        dispose();
    }

    private void openAccountSettings() {
        JOptionPane.showMessageDialog(this, "Account settings is W.I.P :p (Might not continue)");
    }

    private JPanel createProgressBarPanel() {
        JPanel progressBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progressBarPanel.setBackground(Color.darkGray);

        // Create and configure the progress bar
        JProgressBar progressBar = new JProgressBar(0, 31); // 31 days in March
        progressBar.setStringPainted(true); // Show text

        // Calculate current day of the month
        Calendar now = Calendar.getInstance();
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        // Set progress bar value to the current day
        progressBar.setValue(currentDay);

        // Add the progress bar to the panel
        progressBarPanel.add(progressBar);

        // Add "Work Progress" label
        JLabel workProgressLabel = new JLabel(": Work Progress");
        workProgressLabel.setForeground(Color.WHITE);
        progressBarPanel.add(workProgressLabel);

        return progressBarPanel;
    }

    private JPanel createToDoListPanel() {
        JPanel toDoListPanel = new JPanel(new BorderLayout());
        toDoListPanel.setBackground(Color.lightGray);

        // Create a list with check marks
        listModel = new DefaultListModel<>();
        toDoList = new JList<>(listModel);
        toDoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDoListPanel.add(new JScrollPane(toDoList), BorderLayout.CENTER);

        // Add item button
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JTextField itemNameField = new JTextField();
            JTextField dueDateField = new JTextField();
            JComboBox<String> priorityComboBox = new JComboBox<>(new String[]{"High", "Medium", "Low"});

            panel.add(new JLabel("Item Name:"));
            panel.add(itemNameField);
            panel.add(new JLabel("Due Date:"));
            panel.add(dueDateField);
            panel.add(new JLabel("Priority:"));
            panel.add(priorityComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Add Item", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String itemName = itemNameField.getText();
                String dueDate = dueDateField.getText();
                String priority = (String) priorityComboBox.getSelectedItem();
                if (!itemName.isEmpty()) {
                    String item = String.format("☐ %s - Due Date: %s - Priority: %s", itemName, dueDate, priority);
                    listModel.addElement(item); // Add item with details
                }
            }
        });

        // Add delete button
        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(e -> {
            int selectedIndex = toDoList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        });

        // Add mark as done button
        JButton markAsDoneButton = new JButton("Mark as Done");
        markAsDoneButton.addActionListener(e -> {
            int selectedIndex = toDoList.getSelectedIndex();
            if (selectedIndex != -1) {
                String item = listModel.getElementAt(selectedIndex);
                if (item != null && item.startsWith("☐")) {
                    listModel.setElementAt(item.replace("☐", "☑"), selectedIndex); // Mark as done
                    toDoList.repaint(); // Refresh the list to apply strikethrough
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.add(addItemButton);
        buttonPanel.add(deleteItemButton);
        buttonPanel.add(markAsDoneButton);

        toDoListPanel.add(buttonPanel, BorderLayout.SOUTH);

        return toDoListPanel;
    }

    private JPanel createNoteTakingPanel() {
        JPanel noteTakingPanel = new JPanel(new BorderLayout());
        noteTakingPanel.setBackground(Color.lightGray);

        // Create a text area for note-taking
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        noteTakingPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        JMenuItem printMenuItem = new JMenuItem("Print");
        saveMenuItem.addActionListener(e -> saveNoteToFile(textArea.getText()));
        loadMenuItem.addActionListener(e -> loadNoteFromFile(textArea));
        printMenuItem.addActionListener(e -> {
            try {
                textArea.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        });
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.add(printMenuItem);
        menuBar.add(fileMenu);
        noteTakingPanel.add(menuBar, BorderLayout.NORTH);

        return noteTakingPanel;
    }

    private void saveNoteToFile(String text) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadNoteFromFile(JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(file)) {
                StringBuilder content = new StringBuilder();
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
                textArea.setText(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadToDoList() {
        File file = new File("todo_list.csv");
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    listModel.addElement(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePage("username").setVisible(true);
        });
    }
}
