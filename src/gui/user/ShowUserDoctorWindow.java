package gui.user;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import backend.dataStructure.MyLinkedList;
import backend.user.Professional;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowUserDoctorWindow {
    private JFrame addDoctorFrame;
    private MyLinkedList<Professional> professionals;
    private JTable doctorsTable;
    private DefaultTableModel tableModel;

    public ShowUserDoctorWindow(MyLinkedList<Professional> professionals) {
        this.professionals = professionals;
        openWindow();
    }

    private void openWindow() {
        addDoctorFrame = new JFrame("Doctors");
        addDoctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDoctorFrame.setSize(600, 400);
        addDoctorFrame.setLocationRelativeTo(null);

        // Check if there are doctors in the Hospital object
        if (professionals.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No doctors found.", "Error", JOptionPane.ERROR_MESSAGE);
            addDoctorFrame.dispose();
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
        tableModel.addColumn("Specialization");
        tableModel.addColumn("Work Location");
        tableModel.addColumn("Email");

        // Fill the table model with data from the list of doctors
        for (Professional doctor : professionals) {
            Object[] rowData = {
                    doctor.getName(),
                    doctor.getProfession(),
                    doctor.getWorkLocation(),
                    doctor.getEmail()
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
        //         for (Professional doctor : professionals) {
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
