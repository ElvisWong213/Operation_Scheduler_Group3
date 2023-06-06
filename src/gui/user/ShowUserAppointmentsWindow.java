package gui.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import appointment.Appointment;
import appointment.AppointmentEntry;
import dataStructure.MyLinkedList;
import user.Patient;
import user.Professional;

public class ShowUserAppointmentsWindow {
    private JFrame showAppointmentsFrame;
    private Patient patient;
    private Professional professional;
    private Appointment appointment;
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ShowUserAppointmentsWindow(Patient patient) {
        this.patient = patient;
        this.appointment = new Appointment();
        this.appointment.getAllAppointmentsByID(0, patient.getPatientID());
        openWindow();
    }

    public ShowUserAppointmentsWindow(Professional professional) {
        this.professional = professional;
        this.appointment = new Appointment();
        this.appointment.getAllAppointmentsByID(professional.getProfessionalID(), 0);
        openWindow();
    }

    public ShowUserAppointmentsWindow() {
        this.appointment = new Appointment();
        this.appointment.getAllAppointments();
        openWindow();
    }

    private void openWindow() {
        if (patient != null) {
            showAppointmentsFrame = new JFrame("Appointments " + patient.getName());
        } else if (professional != null) {
            showAppointmentsFrame = new JFrame("Appointments " + professional.getName());
        } else {
            showAppointmentsFrame = new JFrame("Appointments");
        }
        showAppointmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showAppointmentsFrame.setSize(800, 500);
        showAppointmentsFrame.setLocationRelativeTo(null);

        // Check if there are appointments in the Hospital object
        if (appointment.getAppointments().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No appointments found.", "Error", JOptionPane.ERROR_MESSAGE);
            showAppointmentsFrame.dispose();
        }
        // Create search bar
        searchField = new JTextField(20);

        // Add search bar to a panel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }

            
        });
        // Create a table to display the list of appointments
        appointmentsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);

        // Create buttons
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton detailsButton = new JButton("Details");
        JButton exitButton = new JButton("Exit");

        // Create a panel for the buttons and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0)); // 1 row, 5 columns, horizontal and vertical gaps

        // Add empty borders to the buttons to create spacing
        addButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        editButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        deleteButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        detailsButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        exitButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add buttons to the panel
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(detailsButton);
        bottomPanel.add(exitButton);

        // Set the layout for the frame
        showAppointmentsFrame.setLayout(new BorderLayout());

        // Add components to the frame
        showAppointmentsFrame.add(scrollPane, BorderLayout.CENTER);
        showAppointmentsFrame.add(bottomPanel, BorderLayout.SOUTH);
        showAppointmentsFrame.add(searchPanel, BorderLayout.NORTH);

        // Create the table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Date");
        tableModel.addColumn("Start Time");
        tableModel.addColumn("End Time");
        tableModel.addColumn("Patient");
        tableModel.addColumn("Treatment Type");
        tableModel.addColumn("Description");
        tableModel.addColumn("Doctors");

        // Fill the table model with data from the list of appointments
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
                deleteButton.setEnabled(selectedRow != -1); // Enable or disable the Delete button based on row
                                                            // selection
                detailsButton.setEnabled(selectedRow != -1); // Enable or disable the Details button based on row
                                                             // selection
            }
        });

        // Add action listener for All Appointments button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AppointmentWindow addNewAppointmentWindow = null;
                if (patient != null) {
                    addNewAppointmentWindow = new AppointmentWindow(patient,
                            appointment.getAppointments().get(selectedRow));
                } else if (professional != null) {
                    addNewAppointmentWindow = new AppointmentWindow(professional,
                            appointment.getAppointments().get(selectedRow));
                } else {
                    addNewAppointmentWindow = new AppointmentWindow(appointment.getAppointments().get(selectedRow));
                }
                addNewAppointmentWindow.setModal(true); // Set the dialog as modal
                addNewAppointmentWindow.setVisible(true);

                fetchAppointment();
                initialiseTable();

            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the form for adding a new appointment
                AppointmentWindow addNewAppointmentWindow = null;

                if (patient != null) {
                    addNewAppointmentWindow = new AppointmentWindow(patient, null);
                } else if (professional != null) {
                    addNewAppointmentWindow = new AppointmentWindow(professional, null);
                } else {
                    addNewAppointmentWindow = new AppointmentWindow(null);
                }
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
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
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
                    JOptionPane.showMessageDialog(null, "Please select a row to view details.", "Error",
                            JOptionPane.ERROR_MESSAGE);
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

    private void initialiseTable() {
        tableModel.setRowCount(0);
        for (AppointmentEntry appointmentEntry : appointment.getAppointments()) {
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

    private void fetchAppointment() {
        if (patient != null) {
            appointment.getAllAppointmentsByID(0, patient.getPatientID());
        } else if (professional != null) {
            appointment.getAllAppointmentsByID(professional.getProfessionalID(), 0);
        } else {
            appointment.getAllAppointments();
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

    private void performSearch() {
        fetchAppointment();
        if (!searchField.getText().isEmpty() && !searchField.getText().isBlank()) {
            String query = searchField.getText().trim().toLowerCase();
    
            MyLinkedList<AppointmentEntry> filteredAppointments = new MyLinkedList<>();
            for (AppointmentEntry appointmentEntry : appointment.getAppointments()) {
                Patient patientTS = new Patient();
                Professional professionalTS = new Professional();
                try {
                    patientTS.getUserById(appointmentEntry.getPatientID());
                    professionalTS.getUserById(appointmentEntry.getProfessionalID());
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (appointmentEntry.getDate().toLocalDate().toString().toLowerCase().contains(query)
                    || appointmentEntry.getStartTime().toLocalTime().toString().toLowerCase().contains(query)
                    || appointmentEntry.getEndTime().toLocalTime().toString().toLowerCase().contains(query)
                    || appointmentEntry.getTreatmentType().toString().toLowerCase().contains(query)
                    || patientTS.getName().toLowerCase().contains(query)
                    || professionalTS.getName().toLowerCase().contains(query)) {
                    filteredAppointments.add(appointmentEntry);
                }
            }
            appointment.setAppointments(filteredAppointments);
        }

        // Update the table with the filtered appointments
        initialiseTable();
    }
}
