import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class HomePage extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<String> toDoList;
    private DefaultListModel<String> listModel;

    public HomePage(String username) {
        setTitle("MotorPH - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1152, 864);
        setLocationRelativeTo(null);

        new Font("Helvetica", Font.PLAIN, 16);

        Color lightBackground = new Color(240, 240, 240);
        new Color(40, 40, 40);
        Color navBarColor = new Color(60, 60, 60);
        Color buttonBorderColor = navBarColor;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(lightBackground);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navPanel.setBackground(navBarColor);
        navPanel.setPreferredSize(new Dimension(getWidth(), 80));

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(navBarColor);
        JLabel logoLabel = new JLabel("MOTORPH");
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);

        JButton leaveRequestButton = createButton("Leave Request");
        JButton viewEmployeesButton = createButton("View Employees");
        JButton accountButton = createButton("Account");
        JButton logoutButton = createButton("Logout");

        leaveRequestButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor));
        viewEmployeesButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor));
        accountButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor));
        logoutButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor));

        leaveRequestButton.addActionListener(e -> openPage(new LeaveRequestPage()));
        viewEmployeesButton.addActionListener(e -> openPage(new ViewEmployeePage()));
        accountButton.addActionListener(e -> openAccountSettings());
        logoutButton.addActionListener(e -> openPage(new LoginPage()));

        leaveRequestButton.setBackground(navBarColor);
        leaveRequestButton.setForeground(Color.WHITE);
        viewEmployeesButton.setBackground(navBarColor);
        viewEmployeesButton.setForeground(Color.WHITE);
        accountButton.setBackground(navBarColor);
        accountButton.setForeground(Color.WHITE);
        logoutButton.setBackground(navBarColor);
        logoutButton.setForeground(Color.WHITE);

        navPanel.add(logoPanel);
        navPanel.add(leaveRequestButton);
        navPanel.add(viewEmployeesButton);
        navPanel.add(accountButton);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(logoutButton);

        navPanel.add(buttonBox);

        mainPanel.add(navPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(lightBackground);
        tabbedPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tabbedPane.setPreferredSize(new Dimension(832, 624));

        JPanel calendarPanel = new CalendarPage();
        calendarPanel.setPreferredSize(new Dimension(832, 500));
        tabbedPane.addTab("Calendar", calendarPanel);

        JPanel toDoListPanel = createToDoListPanel();
        tabbedPane.addTab("To-Do List", toDoListPanel);

        JPanel noteTakingPanel = createNoteTakingPanel();
        tabbedPane.addTab("Note Taking", noteTakingPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel progressBarPanel = createProgressBarPanel();
        mainPanel.add(progressBarPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(lightBackground);
        add(mainPanel);

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
        JOptionPane.showMessageDialog(this, "User Setting is underway, wait for the next update");
    }

    private JPanel createProgressBarPanel() {
        JPanel progressBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progressBarPanel.setBackground(Color.darkGray);

        JProgressBar progressBar = new JProgressBar(0, 31);
        progressBar.setStringPainted(true);

        Calendar now = Calendar.getInstance();
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        progressBar.setValue(currentDay);

        progressBarPanel.add(progressBar);

        JLabel workProgressLabel = new JLabel(": Work Progress");
        workProgressLabel.setForeground(Color.WHITE);
        progressBarPanel.add(workProgressLabel);

        return progressBarPanel;
    }

    private JPanel createToDoListPanel() {
        JPanel toDoListPanel = new JPanel(new BorderLayout());
        toDoListPanel.setBackground(Color.lightGray);

        listModel = new DefaultListModel<>();
        toDoList = new JList<>(listModel);
        toDoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDoListPanel.add(new JScrollPane(toDoList), BorderLayout.CENTER);

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
                    listModel.addElement(item);
                    saveToDoList();
                }
            }
        });

        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(e -> {
            int selectedIndex = toDoList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                saveToDoList();
            }
        });

        JButton markAsDoneButton = new JButton("Mark as Done");
        markAsDoneButton.addActionListener(e -> {
            int selectedIndex = toDoList.getSelectedIndex();
            if (selectedIndex != -1) {
                String item = listModel.getElementAt(selectedIndex);
                if (item != null && item.startsWith("☐")) {
                    listModel.setElementAt(item.replace("☐", "☑"), selectedIndex);
                    toDoList.repaint();
                    saveToDoList();
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

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        noteTakingPanel.add(scrollPane, BorderLayout.CENTER);

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

    private void saveToDoList() {
        File file = new File("todo_list.csv");
        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < listModel.size(); i++) {
                writer.println(listModel.getElementAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePage("username").setVisible(true);
        });
    }
}
