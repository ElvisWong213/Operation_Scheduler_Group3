package gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import backend.appointment.Appointment;
import backend.appointment.AppointmentEntry;
import backend.user.Patient;
import backend.user.Professional;
import gui.user.AppointmentWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ShowAppointmentsWindow {
    private JFrame showAppointmentsFrame;
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;

    private Appointment appointment;

    public ShowAppointmentsWindow() {
        this.appointment = new Appointment();
        this.appointment.getAllAppointments();
        openWindow();
    }

    public void openWindow() {
        showAppointmentsFrame = new JFrame("Appointments");
        showAppointmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showAppointmentsFrame.setSize(800, 500);
        showAppointmentsFrame.setLocationRelativeTo(null);

        // Check if there are appointments in the Hospital object
        if (appointment.getAppointments().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No appointments found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a table to display the list of appointments
        appointmentsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);

        // Create buttons
        JButton allAppointmentsButton = new JButton("All Appointments");
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton detailsButton = new JButton("Details");
        JButton exitButton = new JButton("Exit");

        // Create a panel for the buttons and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0)); // 1 row, 5 columns, horizontal and vertical gaps

        // Add empty borders to the buttons to create spacing
        allAppointmentsButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        addButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        deleteButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        detailsButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        exitButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add buttons to the panel
        bottomPanel.add(allAppointmentsButton);
        //bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(detailsButton);
        bottomPanel.add(exitButton);

        // Set the layout for the frame
        showAppointmentsFrame.setLayout(new BorderLayout());

        // Add components to the frame
        showAppointmentsFrame.add(scrollPane, BorderLayout.CENTER);
        showAppointmentsFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Create the table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Date");
        tableModel.addColumn("Patient");
        tableModel.addColumn("Description");
        tableModel.addColumn("Doctors");

        // Fill the table model with data from the list of appointments
        fetchAppointment();
        initialiseTable();

        // Set the table model
        appointmentsTable.setModel(tableModel);

        appointmentsTable.setEnabled(true);
        appointmentsTable.setCellSelectionEnabled(false);
        appointmentsTable.setColumnSelectionAllowed(false);
        appointmentsTable.setRowSelectionAllowed(true);

        // Add action listener to handle row selection
        appointmentsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = appointmentsTable.getSelectedRow();
                deleteButton.setEnabled(selectedRow != -1); // Enable or disable the Delete button based on row selection
                detailsButton.setEnabled(selectedRow != -1); // Enable or disable the Details button based on row selection
            }
        });

        // Add action listener for All Appointments button
        allAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appointmentsTable.setModel(tableModel); // Set the table model with all appointments data
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the form for adding a new appointment
                AppointmentWindow addNewAppointmentWindow = new AppointmentWindow(null);
                addNewAppointmentWindow.setModal(true); // Set the dialog as modal
                addNewAppointmentWindow.setVisible(true);

                fetchAppointment();
                initialiseTable();
            }
        });

        // Add action listener for Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    Appointment.removeAppointment(appointment.getAppointments().get(selectedRow));
                    fetchAppointment();
                    initialiseTable();
                    JOptionPane.showMessageDialog(null, "Appointment deleted successfully!");
                }
            }
        });

        // Add action listener for Details button
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to view details.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                AppointmentEntry selectedAppointment = appointment.getAppointments().get(selectedRow);
                showAppointmentDetails(selectedAppointment);
            }
        });

        // Add action listener for Exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointmentsFrame.dispose();
            }
        });

        // Show the frame
        showAppointmentsFrame.setVisible(true);
    }
    
    private void fetchAppointment() {
        appointment.getAllAppointments();
    }

    private void initialiseTable() {
        tableModel.setRowCount(0);
        for (AppointmentEntry appointmentEntry : appointment.getAppointments())
        {
            try {
                Patient patientTS = new Patient();
                patientTS.getUserById(appointmentEntry.getPatientID());
                Professional professionalTS = new Professional();
                professionalTS.getUserById(appointmentEntry.getProfessionalID());
                Object[] rowData = {
                    appointmentEntry.getDate(),
                    appointmentEntry.getStartTime(),
                    appointmentEntry.getEndTime(),
                    patientTS.getInfo(),
                    appointmentEntry.getTreatmentType(),
                    appointmentEntry.getDescription(),
                    professionalTS.getName()
                };
                tableModel.addRow(rowData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAppointmentDetails(AppointmentEntry appointment) {
        Patient patientTS = new Patient();
        Professional professionalTS = new Professional();
        try {
            patientTS.getUserById(appointment.getPatientID());
            professionalTS.getUserById(appointment.getProfessionalID());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        StringBuilder details = new StringBuilder();
        details.append("Date: ").append(appointment.getDate()).append("\n");
        details.append("Time: ").append(appointment.getStartTime() + " - " + appointment.getEndTime()).append("\n");
        details.append("Treatment Type: ").append(appointment.getTreatmentType()).append("\n");
        details.append("Description: ").append(appointment.getDescription()).append("\n");
        details.append("\n").append(patientTS.getFullInfo()).append("\n");
        details.append("\n").append(professionalTS.getFullInfo()).append("\n");

        JOptionPane.showMessageDialog(null, details.toString(), "Appointment Details", JOptionPane.INFORMATION_MESSAGE);
    }
}
