import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class GradeCalculator extends JFrame implements ActionListener {

   
	private JTextField milestone1Field, milestone2Field, terminalAssessmentField;
    private JLabel totalPercentageLabel;

    public GradeCalculator() {
        setTitle("Milestone Grade Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setModernUI(); 

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel milestone1Label = new JLabel("Milestone 1 (Max 25 points):");
        milestone1Field = new JTextField();
        milestone1Field.setForeground(Color.BLACK); 
        panel.add(milestone1Label);
        panel.add(milestone1Field);

 
        JLabel milestone2Label = new JLabel("Milestone 2 (Max 40 points):");
        milestone2Field = new JTextField();
        milestone2Field.setForeground(Color.BLACK); 
        panel.add(milestone2Label);
        panel.add(milestone2Field);

    
        JLabel terminalAssessmentLabel = new JLabel("Terminal Assessment (Max 35 points):");
        terminalAssessmentField = new JTextField();
        terminalAssessmentField.setForeground(Color.BLACK); 
        panel.add(terminalAssessmentLabel);
        panel.add(terminalAssessmentField);

  
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        panel.add(calculateButton);

        totalPercentageLabel = new JLabel("Final Grade: ");
        panel.add(totalPercentageLabel);

        add(panel);

        setVisible(true);
    }

    private void setModernUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(255, 255, 255)); 
            UIManager.put("nimbusBlueGrey", new Color(255, 255, 255)); 
            UIManager.put("control", new Color(255, 255, 255)); 
            UIManager.put("nimbusLightBackground", new Color(255, 255, 255)); 
            UIManager.put("nimbusFocus", new Color(0, 0, 0)); 
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ((JComponent) getContentPane()).setOpaque(false);
        getContentPane().setBackground(new Color(255, 255, 255, 0));
        setLayout(new BorderLayout());
    }

 
    public void actionPerformed(ActionEvent e) {
        try {
            double milestone1 = validateInput(milestone1Field, 25);
            double milestone2 = validateInput(milestone2Field, 40);
            double terminalAssessment = validateInput(terminalAssessmentField, 35);

            double totalPoints = milestone1 + milestone2 + terminalAssessment;
            double maxPoints = 25 + 40 + 35;

    
            double totalPercentage = (totalPoints / maxPoints) * 100;

      
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedPercentage = decimalFormat.format(totalPercentage);

    
            totalPercentageLabel.setText("Total Percentage: " + formattedPercentage + "%");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double validateInput(JTextField textField, int maxPoints) {
        double inputValue;
        try {
            inputValue = Double.parseDouble(textField.getText());
            if (inputValue < 0 || inputValue > maxPoints) {
                throw new IllegalArgumentException("Input value must be between 0 and " + maxPoints + ".");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a valid number.");
        }
        return inputValue;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeCalculator());
    }
}
