package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowUserDoctorWindow {
    private JFrame addDoctorFrame;
    private Hospital hospital;
    private JTable doctorsTable;
    private DefaultTableModel tableModel;

    public ShowUserDoctorWindow(Hospital hospital) {
        this.hospital = hospital;
    }

    public void openWindow() {
        addDoctorFrame = new JFrame("Doctors");
        addDoctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDoctorFrame.setSize(600, 400);
        addDoctorFrame.setLocationRelativeTo(null);

        // Check if there are doctors in the Hospital object
        if (hospital.getDoctors().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No doctors found. Please add doctors first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a table to display the list of doctors
        doctorsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(doctorsTable);

        // Create buttons

        JButton searchButton = new JButton("Search");
        JButton exitButton = new JButton("Exit");

        // Create a panel for the buttons and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0)); // 1 row, 5 columns, horizontal and vertical gaps


        searchButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        exitButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add buttons to the panel

        bottomPanel.add(searchButton);
        bottomPanel.add(exitButton);

        // Set the layout for the frame
        addDoctorFrame.setLayout(new BorderLayout());

        // Add components to the frame
        addDoctorFrame.add(scrollPane, BorderLayout.CENTER);
        addDoctorFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Create the table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Middle Name");
        tableModel.addColumn("Specialization");

        // Fill the table model with data from the list of doctors
        List<Doctor> doctorList = hospital.getDoctors();
        for (Doctor doctor : doctorList) {
            Object[] rowData = {
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getMiddleName(),
                    doctor.getSpecialization()
            };
            tableModel.addRow(rowData);
        }

        // Set the table model
        doctorsTable.setModel(tableModel);

        doctorsTable.setEnabled(true);
        doctorsTable.setCellSelectionEnabled(false);
        doctorsTable.setColumnSelectionAllowed(false);
        doctorsTable.setRowSelectionAllowed(true);




        // Add action listener for Search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = JOptionPane.showInputDialog(null, "Enter a search value:", "Search", JOptionPane.PLAIN_MESSAGE);
                if (searchValue == null || searchValue.isEmpty()) {
                    return;
                }
                List<Doctor> doctorList = hospital.getDoctors();
                DefaultTableModel searchTableModel = new DefaultTableModel();
                searchTableModel.addColumn("First Name");
                searchTableModel.addColumn("Last Name");
                searchTableModel.addColumn("Middle Name");
                searchTableModel.addColumn("Specialization");
                for (Doctor doctor : doctorList) {
                    if (doctor.getFirstName().equalsIgnoreCase(searchValue) ||
                            doctor.getLastName().equalsIgnoreCase(searchValue) ||
                            doctor.getMiddleName().equalsIgnoreCase(searchValue) ||
                            doctor.getSpecialization().equalsIgnoreCase(searchValue)) {
                        Object[] rowData = {
                                doctor.getFirstName(),
                                doctor.getLastName(),
                                doctor.getMiddleName(),
                                doctor.getSpecialization()
                        };
                        searchTableModel.addRow(rowData);
                    }
                }
                doctorsTable.setModel(searchTableModel);
            }
        });

        // Add action listener for Exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctorFrame.dispose();
            }
        });

        // Display the window
        addDoctorFrame.setVisible(true);
    }
}
