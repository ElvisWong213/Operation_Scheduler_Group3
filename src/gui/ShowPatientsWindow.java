package gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class ShowPatientsWindow
{
    private JFrame addDoctorFrame;

    public void openWindow()
    {
        addDoctorFrame = new JFrame("Patients");
        addDoctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDoctorFrame.setSize(600, 400);

        addDoctorFrame.setLocationRelativeTo(null);

        // Create a table to display Doctors
        JTable DoctorsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(DoctorsTable);

        // Create buttons
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");
        JButton exitButton = new JButton("Exit");

        // Create the bottom panel and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0)); // 1 row, 5 columns, horizontal and vertical gaps

        // Add empty borders to the buttons for spacing
        addButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        deleteButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        cancelButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        exitButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add buttons to the bottom panel
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(searchButton);
        bottomPanel.add(cancelButton);
        bottomPanel.add(exitButton);

        // Set layout for the frame
        addDoctorFrame.setLayout(new BorderLayout());

        // Add components to the frame
        addDoctorFrame.add(scrollPane, BorderLayout.CENTER);
        addDoctorFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle add button click event
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle delete button click event
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle search button click event
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDoctorFrame.dispose(); // Close the window
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDoctorFrame.dispose();  // Exit the application
            }
        });

        addDoctorFrame.setVisible(true);

    }
}