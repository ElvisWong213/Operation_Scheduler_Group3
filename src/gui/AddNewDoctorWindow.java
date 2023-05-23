package gui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AddNewDoctorWindow {
    private JFrame addDoctorFrame;

    public void openWindow() {
        addDoctorFrame = new JFrame("Add New Doctor");
        addDoctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDoctorFrame.setSize(400, 300); // Уменьшаем высоту формы
        addDoctorFrame.setLocationRelativeTo(null);

        // Create labels
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel middleNameLabel = new JLabel("Middle Name:");
        JLabel specializationLabel = new JLabel("Specialization:");

        // Create text fields
        JTextField firstNameTextField = new JTextField();
        JTextField lastNameTextField = new JTextField();
        JTextField middleNameTextField = new JTextField();

        // Set preferred height for text fields based on font size
        Font textFieldFont = firstNameTextField.getFont();
        int preferredHeight = textFieldFont.getSize() + 8; // 8 is an arbitrary value for padding
        Dimension textFieldDimension = new Dimension(firstNameTextField.getPreferredSize().width, preferredHeight);
        firstNameTextField.setPreferredSize(textFieldDimension);
        lastNameTextField.setPreferredSize(textFieldDimension);
        middleNameTextField.setPreferredSize(textFieldDimension);

        // Create specialization combo box
        String[] specializations = {"Cardiology", "Dermatology", "Endocrinology", "Gastroenterology", "Neurology",
                "Ophthalmology", "Orthopedics", "Pediatrics", "Psychiatry", "Urology"};
        JComboBox<String> specializationComboBox = new JComboBox<>(specializations);

        // Create the main panel and set its layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns, horizontal and vertical gaps

        // Add components to the main panel with proper spacing
        mainPanel.add(createPaddedPanel(firstNameLabel, 20)); // Add First Name label with left padding
        mainPanel.add(firstNameTextField);
        mainPanel.add(createPaddedPanel(lastNameLabel, 20)); // Add Last Name label with left padding
        mainPanel.add(lastNameTextField);
        mainPanel.add(createPaddedPanel(middleNameLabel, 20)); // Add Middle Name label with left padding
        mainPanel.add(middleNameTextField);
        mainPanel.add(createPaddedPanel(specializationLabel, 20)); // Add Specialization label with left padding
        mainPanel.add(specializationComboBox);

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
        addDoctorFrame.setLayout(new BorderLayout());

        // Add components to the frame
        addDoctorFrame.add(mainPanel, BorderLayout.CENTER);
        addDoctorFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle add button click event
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDoctorFrame.dispose(); // Close the window
            }
        });

        addDoctorFrame.setVisible(true);
    }

    // Helper method to create a padded panel with left padding
    private JPanel createPaddedPanel(JComponent component, int padding) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, padding, 0, 0));
        panel.add(component, BorderLayout.WEST);
        return panel;
    }
}
