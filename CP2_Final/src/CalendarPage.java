import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class CalendarPage extends JPanel {
    private JTextArea eventTextArea;
    private static final Color GRAY_LIGHT = new Color(245, 245, 245); // Light gray background color
    private static final Color TEXT_COLOR = Color.BLACK; // Text color
    private static final Font EVENT_FONT = new Font("Arial", Font.PLAIN, 16); // Font for event text

    public CalendarPage() {
        setLayout(new BorderLayout());
        setBackground(GRAY_LIGHT);

        // JCalendar from the JCalendar library
        JCalendar calendar = new JCalendar();
        calendar.setBorder(new EmptyBorder(10, 10, 10, 10));
        calendar.setBackground(Color.WHITE);

        // Adjust calendar size
        calendar.setPreferredSize(new Dimension(300, 300)); // Set size

        eventTextArea = new JTextArea();
        eventTextArea.setEditable(false);
        eventTextArea.setFont(EVENT_FONT);
        eventTextArea.setForeground(TEXT_COLOR); // Set text color
        JScrollPane eventScrollPane = new JScrollPane(eventTextArea);
        eventScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton addEventButton = createButton("Add Event");
        addEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEvent(calendar.getDate());
            }
        });

        JButton deleteEventButton = createButton("Delete Event");
        deleteEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteEvent();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(GRAY_LIGHT);
        buttonPanel.add(addEventButton);
        buttonPanel.add(deleteEventButton);

        JPanel eventPanel = new JPanel(new BorderLayout());
        eventPanel.setBackground(Color.WHITE);
        eventPanel.add(new JLabel("Events"), BorderLayout.NORTH);
        eventPanel.add(eventScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(GRAY_LIGHT);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, calendar, eventPanel);
        splitPane.setDividerLocation(300); // Set initial divider location

        add(splitPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40)); // Set preferred size
        return button;
    }

    private void addEvent(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String eventName = JOptionPane.showInputDialog(this, "Enter the event name:");
        String startTime = JOptionPane.showInputDialog(this, "Enter start time for attendance:");
        String endTime = JOptionPane.showInputDialog(this, "Enter end time for attendance:");
        if (eventName != null && !eventName.isEmpty() && startTime != null && !startTime.isEmpty() && endTime != null && !endTime.isEmpty()) {
            String formattedAttendance = "Event: " + eventName + " | Date: " + dateFormat.format(date) + " | Start Time: " + startTime + " | End Time: " + endTime + "\n";
            eventTextArea.append(formattedAttendance);
        }
    }

    private void deleteEvent() {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the event?", "Delete Event", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            eventTextArea.setText(""); // Clear the eventTextArea
        }
    }
}
