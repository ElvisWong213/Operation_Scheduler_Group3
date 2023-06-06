package gui.user;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dataStructure.MyLinkedList;
import user.Patient;
import user.Professional;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ShowUserPatientsWindow {
    private JFrame addDoctorFrame;
    private MyLinkedList<Patient> patients;
    private JTable doctorsTable;
    private DefaultTableModel tableModel;

    public ShowUserPatientsWindow(MyLinkedList<Patient> patients) {
        this.patients = patients;
        openWindow();
    }

    private void openWindow() {
        addDoctorFrame = new JFrame("Patients");
        addDoctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDoctorFrame.setSize(600, 400);
        addDoctorFrame.setLocationRelativeTo(null);

        // Check if there are doctors in the Hospital object
        if (patients.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No patients found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a table to display the list of doctors
        doctorsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(doctorsTable);

        // Create buttons

        // JButton searchButton = new JButton("Search");
        JButton exitButton = new JButton("Exit");

        // Create a panel for the buttons and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0)); // 1 row, 5 columns, horizontal and vertical gaps


        // searchButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        exitButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add buttons to the panel

        // bottomPanel.add(searchButton);
        bottomPanel.add(exitButton);

        // Set the layout for the frame
        addDoctorFrame.setLayout(new BorderLayout());

        // Add components to the frame
        addDoctorFrame.add(scrollPane, BorderLayout.CENTER);
        addDoctorFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Create the table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Name");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Age");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Address");

        // Fill the table model with data from the list of doctors
        for (Patient patient : patients) {
            int age = LocalDate.now().minusYears(patient.getDateOfBirth().toLocalDate().getYear()).getYear();
            Object[] rowData = {
                    patient.getName(),
                    patient.getGender(),
                    age,
                    patient.getPhoneNumber(),
                    patient.getAddress()
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
        // searchButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         String searchValue = JOptionPane.showInputDialog(null, "Enter a search value:", "Search", JOptionPane.PLAIN_MESSAGE);
        //         if (searchValue == null || searchValue.isEmpty()) {
        //             return;
        //         }
        //         DefaultTableModel searchTableModel = new DefaultTableModel();
        //         searchTableModel.addColumn("First Name");
        //         searchTableModel.addColumn("Specialization");
        //         searchTableModel.addColumn("Work Location");
        //         searchTableModel.addColumn("Email");
        //         for (Professional doctor : patients) {
        //             if (doctor.getName().equalsIgnoreCase(searchValue) ||
        //                     doctor.getProfession().toString().equalsIgnoreCase(searchValue) ||
        //                     doctor.getWorkLocation().equalsIgnoreCase(searchValue) ||
        //                     doctor.getEmail().equalsIgnoreCase(searchValue)) {
        //                 Object[] rowData = {
        //                         doctor.getName(),
        //                         doctor.getProfession(),
        //                         doctor.getWorkLocation(),
        //                         doctor.getEmail()
        //                 };
        //                 searchTableModel.addRow(rowData);
        //             }
        //         }
        //         doctorsTable.setModel(searchTableModel);
        //     }
        // });

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
