import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CalendarPage extends JFrame {
    private JTextArea eventTextArea;

    public CalendarPage() {
        setTitle("Employee App - Calendar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // JCalendar from the JCalendar library
        JCalendar calendar = new JCalendar();
        eventTextArea = new JTextArea();
        JButton addEventButton = new JButton("Add Event");

        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEvent(calendar.getDate());
            }
        });

        panel.add(calendar, BorderLayout.NORTH);
        panel.add(new JScrollPane(eventTextArea), BorderLayout.CENTER);
        panel.add(addEventButton, BorderLayout.SOUTH);

        add(panel);
    }

    private void addEvent(Date date) {
        String event = JOptionPane.showInputDialog(this, "Enter event for " + date + ":");
        if (event != null && !event.isEmpty()) {
            eventTextArea.append(date + ": " + event + "\n");
        }
    }
}
