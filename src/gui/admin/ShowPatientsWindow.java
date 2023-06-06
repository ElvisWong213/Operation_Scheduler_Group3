package gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import backend.dataStructure.MyLinkedList;
import backend.user.Patient;
import backend.user.UserManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ShowPatientsWindow {
    private JFrame showPatientsFrame;
    private JTable patientsTable;
    private DefaultTableModel tableModel;

    private MyLinkedList<Patient> patients;

    public ShowPatientsWindow() {
        this.patients = UserManager.getAllPatients();
        openWindow();
    }

    public void openWindow() {
        showPatientsFrame = new JFrame("Patients");
        showPatientsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showPatientsFrame.setSize(600, 400);
        showPatientsFrame.setLocationRelativeTo(null);

        // Check if there are patients in the Hospital object
        if (patients.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No patients found. Please add patients first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a table to display the list of patients
        patientsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(patientsTable);

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
        showPatientsFrame.setLayout(new BorderLayout());

        // Add components to the frame
        showPatientsFrame.add(scrollPane, BorderLayout.CENTER);
        showPatientsFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Create the table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Age");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Address");

        // Fill the table model with data from the list of patients
        for (Patient patient : patients) {
            int age = LocalDate.now().minusYears(patient.getDateOfBirth().toLocalDate().getYear()).getYear();
            Object[] rowData = {
                    patient.getPatientID(),
                    patient.getName(),
                    patient.getGender(),
                    age,
                    patient.getPhoneNumber(),
                    patient.getAddress()
            };
            tableModel.addRow(rowData);
        }

        // Set the table model
        patientsTable.setModel(tableModel);

        patientsTable.setEnabled(true);
        patientsTable.setCellSelectionEnabled(false);
        patientsTable.setColumnSelectionAllowed(false);
        patientsTable.setRowSelectionAllowed(true);

        // Add action listener to handle row selection
        patientsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = patientsTable.getSelectedRow();
                deleteButton.setEnabled(selectedRow != -1); // Enable or disable the Delete button based on row selection
            }
        });

        // Add action listener for All Patients button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = patientsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AddNewPatientWindow editPatientWindow = new AddNewPatientWindow(patients.get(selectedRow));
                editPatientWindow.setModal(true);
                editPatientWindow.setVisible(true);

                refreshTable();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the form for adding a new patient
                AddNewPatientWindow addNewPatientWindow = new AddNewPatientWindow();
                addNewPatientWindow.setModal(true); // Set the dialog as modal
                addNewPatientWindow.setVisible(true);

                refreshTable();
            }
        });

        // Add action listener for Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = patientsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    Patient patient = new Patient();
                    try {
                        patient.removeUser(patients.get(selectedRow).getPatientID());
                        JOptionPane.showMessageDialog(null, "Patient deleted successfully!");
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Unable to delete patient.", "Error", JOptionPane.ERROR_MESSAGE);
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
        //         List<Patient> patientList = hospital.getPatients();
        //         DefaultTableModel searchTableModel = new DefaultTableModel();
        //         searchTableModel.addColumn("First Name");
        //         searchTableModel.addColumn("Last Name");
        //         searchTableModel.addColumn("Age");
        //         searchTableModel.addColumn("Weight");
        //         searchTableModel.addColumn("Height");
        //         searchTableModel.addColumn("Phone");
        //         searchTableModel.addColumn("Address");
        //         searchTableModel.addColumn("Notes");
        //         for (Patient patient : patientList) {
        //             if (patient.getFirstName().equalsIgnoreCase(searchValue) ||
        //                     patient.getLastName().equalsIgnoreCase(searchValue) ||
        //                     String.valueOf(patient.getAge()).equalsIgnoreCase(searchValue) ||
        //                     String.valueOf(patient.getWeight()).equalsIgnoreCase(searchValue) ||
        //                     String.valueOf(patient.getHeight()).equalsIgnoreCase(searchValue) ||
        //                     patient.getPhone().equalsIgnoreCase(searchValue) ||
        //                     patient.getAddress().equalsIgnoreCase(searchValue) ||
        //                     patient.getNotes().equalsIgnoreCase(searchValue)) {
        //                 Object[] rowData = {
        //                         patient.getFirstName(),
        //                         patient.getLastName(),
        //                         patient.getAge(),
        //                         patient.getWeight(),
        //                         patient.getHeight(),
        //                         patient.getPhone(),
        //                         patient.getAddress(),
        //                         patient.getNotes()
        //                 };
        //                 searchTableModel.addRow(rowData);
        //             }
        //         }
        //         patientsTable.setModel(searchTableModel);
        //     }
        // });

        // Add action listener for Exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatientsFrame.dispose();
            }
        });

        // Display the window
        showPatientsFrame.setVisible(true);
    }

    private void refreshTable() {
        patients = UserManager.getAllPatients();

        tableModel.setRowCount(0);
        for (Patient patient : patients) {
            int age = LocalDate.now().minusYears(patient.getDateOfBirth().toLocalDate().getYear()).getYear();
            Object[] rowData = {
                    patient.getPatientID(),
                    patient.getName(),
                    patient.getGender(),
                    age,
                    patient.getPhoneNumber(),
                    patient.getAddress()
            };
            tableModel.addRow(rowData);
        }
    }
}
