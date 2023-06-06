package gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dataStructure.MyLinkedList;
import user.Professional;
import user.UserManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ShowDoctorWindow {
    private JFrame addDoctorFrame;
    private JTable doctorsTable;
    private DefaultTableModel tableModel;

    private MyLinkedList<Professional> professionals;

    public ShowDoctorWindow() {
        this.professionals = UserManager.getAllProfessionals();
        openWindow();
    }

    public void openWindow() {
        addDoctorFrame = new JFrame("Doctors");
        addDoctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDoctorFrame.setSize(600, 400);
        addDoctorFrame.setLocationRelativeTo(null);

        // Check if there are doctors in the Hospital object
        if (professionals.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No doctors found. Please add doctors first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a table to display the list of doctors
        doctorsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(doctorsTable);

        // Create buttons
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton editButton = new JButton("Edit");
        // JButton searchButton = new JButton("Search");
        JButton exitButton = new JButton("Exit");

        // Create a panel for the buttons and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0)); // 1 row, 5 columns, horizontal and vertical gaps

        // Add empty borders to the buttons to create spacing
        addButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        deleteButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        editButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        // searchButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        exitButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add buttons to the panel
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(editButton);
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
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Specialization");
        tableModel.addColumn("Work Location");
        tableModel.addColumn("Email");

        // Fill the table model with data from the list of doctors
        for (Professional doctor : professionals) {
            Object[] rowData = {
                    doctor.getProfessionalID(),
                    doctor.getName(),
                    doctor.getProfession(),
                    doctor.getWorkLocation(),
                    doctor.getEmail(),
            };
            tableModel.addRow(rowData);
        }

        // Set the table model
        doctorsTable.setModel(tableModel);

        doctorsTable.setEnabled(true);
        doctorsTable.setCellSelectionEnabled(false);
        doctorsTable.setColumnSelectionAllowed(false);
        doctorsTable.setRowSelectionAllowed(true);

        // Add action listener to handle row selection
        doctorsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = doctorsTable.getSelectedRow();
                deleteButton.setEnabled(selectedRow != -1); // Enable or disable the Delete button based on row selection
            }
        });

        // Add action listener for All Doctors button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = doctorsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AddNewDoctorWindow editDoctorWindow = new AddNewDoctorWindow(professionals.get(selectedRow));
                editDoctorWindow.setModal(true);
                editDoctorWindow.setVisible(true);

                refreshTable();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the form for adding a new doctor
                AddNewDoctorWindow addNewDoctorWindow = new AddNewDoctorWindow();
                addNewDoctorWindow.setModal(true); // Set the dialog as modal
                addNewDoctorWindow.setVisible(true);
                
                refreshTable();
            }
        });

        // Add action listener for Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = doctorsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this doctor?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    Professional professional = new Professional();
                    try {
                        professional.removeUser(professionals.get(selectedRow).getProfessionalID());
                        JOptionPane.showMessageDialog(null, "Doctor deleted successfully!");
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Unable to delete doctor.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                refreshTable();
            }
        });

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
        //         searchTableModel.addColumn("Last Name");
        //         searchTableModel.addColumn("Middle Name");
        //         searchTableModel.addColumn("Specialization");
        //         for (Doctor doctor : doctorList) {
        //             if (doctor.getFirstName().equalsIgnoreCase(searchValue) ||
        //                     doctor.getLastName().equalsIgnoreCase(searchValue) ||
        //                     doctor.getMiddleName().equalsIgnoreCase(searchValue) ||
        //                     doctor.getSpecialization().equalsIgnoreCase(searchValue)) {
        //                 Object[] rowData = {
        //                         doctor.getFirstName(),
        //                         doctor.getLastName(),
        //                         doctor.getMiddleName(),
        //                         doctor.getSpecialization()
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

    private void refreshTable() {
        professionals = UserManager.getAllProfessionals();

        tableModel.setRowCount(0);
        for (Professional doctor : professionals) {
            Object[] rowData = {
                    doctor.getProfessionalID(),
                    doctor.getName(),
                    doctor.getProfession(),
                    doctor.getWorkLocation(),
                    doctor.getEmail(),
            };
            tableModel.addRow(rowData);
        }
    }
}
