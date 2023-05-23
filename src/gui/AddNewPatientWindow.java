package gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AddNewPatientWindow {
    private JFrame addPatientFrame;

    public void openWindow() {
        addPatientFrame = new JFrame("Add New Patient");
        addPatientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addPatientFrame.setSize(400, 400);
        addPatientFrame.setLocationRelativeTo(null);

        // Create labels
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel weightLabel = new JLabel("Weight:");
        JLabel heightLabel = new JLabel("Height:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel notesLabel = new JLabel("Notes:");

        // Create text fields
        JTextField firstNameTextField = new JTextField();
        JTextField lastNameTextField = new JTextField();
        JTextField ageTextField = new JTextField();
        JTextField weightTextField = new JTextField();
        JTextField heightTextField = new JTextField();
        JTextField phoneTextField = new JTextField();
        JTextField addressTextField = new JTextField();
        JTextArea notesTextArea = new JTextArea();

        // Create the main panel and set its layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns, horizontal and vertical gaps

        // Add components to the main panel with proper spacing
        mainPanel.add(createPaddedPanel(firstNameLabel, 20)); // Add First Name label with left padding
        mainPanel.add(firstNameTextField);
        mainPanel.add(createPaddedPanel(lastNameLabel, 20)); // Add Last Name label with left padding
        mainPanel.add(lastNameTextField);
        mainPanel.add(createPaddedPanel(ageLabel, 20)); // Add Age label with left padding
        mainPanel.add(ageTextField);
        mainPanel.add(createPaddedPanel(weightLabel, 20)); // Add Weight label with left padding
        mainPanel.add(weightTextField);
        mainPanel.add(createPaddedPanel(heightLabel, 20)); // Add Height label with left padding
        mainPanel.add(heightTextField);
        mainPanel.add(createPaddedPanel(phoneLabel, 20)); // Add Phone label with left padding
        mainPanel.add(phoneTextField);
        mainPanel.add(createPaddedPanel(addressLabel, 20)); // Add Address label with left padding
        mainPanel.add(addressTextField);
        mainPanel.add(createPaddedPanel(notesLabel, 20)); // Add Notes label with left padding
        mainPanel.add(notesTextArea);

        // Create buttons
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        // Create the bottom panel and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add buttons to the bottom panel with right spacing
        bottomPanel.add(addButton);
        bottomPanel.add(Box.createHorizontalStrut(30)); // Add 30-pixel horizontal spacing
        bottomPanel.add(cancelButton);

        // Set layout for the frame
        addPatientFrame.setLayout(new BorderLayout());

        // Add components to the frame
        addPatientFrame.add(mainPanel, BorderLayout.CENTER);
        addPatientFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle add button click event
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPatientFrame.dispose(); // Close the window
            }
        });
        addPatientFrame.setVisible(true);
    }

    // Helper method to create a padded panel with left padding
    private JPanel createPaddedPanel(JComponent component, int padding) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, padding, 0, 0));
        panel.add(component, BorderLayout.WEST);
        return panel;
    }
}
