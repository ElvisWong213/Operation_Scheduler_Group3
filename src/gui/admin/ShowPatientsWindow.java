package gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import gui.basic.Hospital;
import gui.basic.Patient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowPatientsWindow {
    private JFrame showPatientsFrame;
    private Hospital hospital;
    private JTable patientsTable;
    private DefaultTableModel tableModel;

    public ShowPatientsWindow(Hospital hospital) {
        this.hospital = hospital;
    }

    public void openWindow() {
        showPatientsFrame = new JFrame("Patients");
        showPatientsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showPatientsFrame.setSize(600, 400);
        showPatientsFrame.setLocationRelativeTo(null);

        // Check if there are patients in the Hospital object
        if (hospital.getPatients().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No patients found. Please add patients first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a table to display the list of patients
        patientsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(patientsTable);

        // Create buttons
        JButton allPatientsButton = new JButton("All Patients");
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");
        JButton exitButton = new JButton("Exit");

        // Create a panel for the buttons and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0)); // 1 row, 5 columns, horizontal and vertical gaps

        // Add empty borders to the buttons to create spacing
        allPatientsButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        addButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        deleteButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        exitButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add buttons to the panel
        bottomPanel.add(allPatientsButton);
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(searchButton);
        bottomPanel.add(exitButton);

        // Set the layout for the frame
        showPatientsFrame.setLayout(new BorderLayout());

        // Add components to the frame
        showPatientsFrame.add(scrollPane, BorderLayout.CENTER);
        showPatientsFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Create the table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Weight");
        tableModel.addColumn("Height");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Address");
        tableModel.addColumn("Notes");

        // Fill the table model with data from the list of patients
        List<Patient> patientList = hospital.getPatients();
        for (Patient patient : patientList) {
            Object[] rowData = {
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getAge(),
                    patient.getWeight(),
                    patient.getHeight(),
                    patient.getPhone(),
                    patient.getAddress(),
                    patient.getNotes()
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
        allPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientsTable.setModel(tableModel); // Set the table model with all patients data
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the form for adding a new patient
                AddNewPatientWindow addNewPatientWindow = new AddNewPatientWindow(hospital);
                addNewPatientWindow.setModal(true); // Set the dialog as modal
                addNewPatientWindow.setVisible(true);

                Patient newPatient = addNewPatientWindow.getNewPatient();
                if (newPatient != null) {
                    // Update the table model with the new data
                    Object[] rowData = {
                            newPatient.getFirstName(),
                            newPatient.getLastName(),
                            newPatient.getAge(),
                            newPatient.getWeight(),
                            newPatient.getHeight(),
                            newPatient.getPhone(),
                            newPatient.getAddress(),
                            newPatient.getNotes()
                    };
                    tableModel.addRow(rowData);

                }
                addNewPatientWindow.dispose();

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
                    List<Patient> patientList = hospital.getPatients();
                    patientList.remove(selectedRow);
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Patient deleted successfully!");
                }
            }
        });

        // Add action listener for Search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = JOptionPane.showInputDialog(null, "Enter a search value:", "Search", JOptionPane.PLAIN_MESSAGE);
                if (searchValue == null || searchValue.isEmpty()) {
                    return;
                }
                List<Patient> patientList = hospital.getPatients();
                DefaultTableModel searchTableModel = new DefaultTableModel();
                searchTableModel.addColumn("First Name");
                searchTableModel.addColumn("Last Name");
                searchTableModel.addColumn("Age");
                searchTableModel.addColumn("Weight");
                searchTableModel.addColumn("Height");
                searchTableModel.addColumn("Phone");
                searchTableModel.addColumn("Address");
                searchTableModel.addColumn("Notes");
                for (Patient patient : patientList) {
                    if (patient.getFirstName().equalsIgnoreCase(searchValue) ||
                            patient.getLastName().equalsIgnoreCase(searchValue) ||
                            String.valueOf(patient.getAge()).equalsIgnoreCase(searchValue) ||
                            String.valueOf(patient.getWeight()).equalsIgnoreCase(searchValue) ||
                            String.valueOf(patient.getHeight()).equalsIgnoreCase(searchValue) ||
                            patient.getPhone().equalsIgnoreCase(searchValue) ||
                            patient.getAddress().equalsIgnoreCase(searchValue) ||
                            patient.getNotes().equalsIgnoreCase(searchValue)) {
                        Object[] rowData = {
                                patient.getFirstName(),
                                patient.getLastName(),
                                patient.getAge(),
                                patient.getWeight(),
                                patient.getHeight(),
                                patient.getPhone(),
                                patient.getAddress(),
                                patient.getNotes()
                        };
                        searchTableModel.addRow(rowData);
                    }
                }
                patientsTable.setModel(searchTableModel);
            }
        });

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
}
