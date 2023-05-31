package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.*;

public class AppointmentWindow extends JDialog {
    Appointment appointment;
    private String patientName;
    private DefaultTableModel doctorTableModel;


    private JComboBox<Doctor> doctorComboBox;
    JTextField dayTextField ;
    JTextField monthTextField;
    JTextField yearTextField;

    // Create time input fields
    JTextField hourTextField;
    JTextField minuteTextField;

    JTextArea notesTextArea;
    Hospital hospital;

    public void openWindow() {

    }
    public AppointmentWindow(Hospital hospital) {
        this.hospital = hospital;
        this.patientName = hospital.ActivePatient.getPatientInfo();

        setTitle("Make Appointment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Create labels
        JLabel nameLabel = new JLabel("Patient: " + patientName);
        JLabel dateLabel = new JLabel("Date:");
        JLabel timeLabel = new JLabel("Time:");
        JLabel doctorLabel = new JLabel("Doctor:");
        JLabel notesLabel = new JLabel("Notes:");

        // Create number input fields
         dayTextField = new JTextField();
         monthTextField = new JTextField();
         yearTextField = new JTextField();

        // Create time input fields
         hourTextField = new JTextField();
         minuteTextField = new JTextField();

        // Set current date and time to the input fields
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

        Date currentDate = new Date();
        dayTextField.setText(dateFormat.format(currentDate));
        monthTextField.setText(monthFormat.format(currentDate));
        yearTextField.setText(yearFormat.format(currentDate));
        hourTextField.setText(hourFormat.format(currentDate));
        minuteTextField.setText(minuteFormat.format(currentDate));

        // Create doctor selection combo box
        List<Doctor> doctors = hospital.getDoctors();
        DefaultComboBoxModel<Doctor> comboBoxModel = new DefaultComboBoxModel<>(doctors.toArray(new Doctor[0]));
        doctorComboBox = new JComboBox<>(comboBoxModel);
        doctorComboBox.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Doctor) {
                    Doctor doctor = (Doctor) value;
                    setText(doctor.getFullName());
                }
                return this;
            }
        });

        // Create notes text area
         notesTextArea = new JTextArea();
        JScrollPane notesScrollPane = new JScrollPane(notesTextArea);

        // Create buttons
        JButton searchButton = new JButton("Search");
        JButton bookButton = new JButton("Book Appointment");
        JButton cancelButton = new JButton("Cancel");
        JButton addDoctorButton = new JButton("Add Doctor");

        // Create the bottom panel and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add buttons to the bottom panel
        bottomPanel.add(searchButton);
        bottomPanel.add(bookButton);
        bottomPanel.add(cancelButton);

        // Set layout for the frame
        setLayout(new BorderLayout());

        // Create the main panel and set its layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));

        // Create the left panel and set its layout
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(5, 5, 5, 5);

        // Add components to the left panel
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.anchor = GridBagConstraints.WEST;
        leftPanel.add(nameLabel, gbcLeft);

        gbcLeft.gridy = 1;
        leftPanel.add(dateLabel, gbcLeft);

        gbcLeft.gridy = 2;
        leftPanel.add(timeLabel, gbcLeft);

        gbcLeft.gridy = 3;
        leftPanel.add(notesLabel, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 1;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(dayTextField, gbcLeft);

        gbcLeft.gridx = 2;
        leftPanel.add(monthTextField, gbcLeft);

        gbcLeft.gridx = 3;
        leftPanel.add(yearTextField, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 2;
        leftPanel.add(hourTextField, gbcLeft);

        gbcLeft.gridx = 2;
        leftPanel.add(minuteTextField, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 3;
        gbcLeft.gridwidth = 3;
        gbcLeft.fill = GridBagConstraints.BOTH;
        gbcLeft.weighty = 2.0;
        leftPanel.add(notesScrollPane, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 4;
        gbcLeft.gridwidth = 3;
        gbcLeft.fill = GridBagConstraints.NONE;
        gbcLeft.weighty = 0.0;
        leftPanel.add(new JLabel(), gbcLeft);

        mainPanel.add(leftPanel);

        // Create the right panel and set its layout
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 5);

        // Create table for displaying doctors
        doctorTableModel = new DefaultTableModel();
        doctorTableModel.addColumn("Doctor Name");

        JTable doctorTable = new JTable(doctorTableModel);
        JScrollPane doctorScrollPane = new JScrollPane(doctorTable);

        // Add doctor selection and table to the right panel
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.NORTH;
        rightPanel.add(doctorLabel, gbcRight);

        gbcRight.gridy = 1;
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(doctorComboBox, gbcRight);

        gbcRight.gridy = 2;
        gbcRight.fill = GridBagConstraints.NONE;
        rightPanel.add(addDoctorButton, gbcRight);

        gbcRight.gridy = 3;
        gbcRight.fill = GridBagConstraints.BOTH;
        gbcRight.weightx = 1.0;
        gbcRight.weighty = 1.0;
        rightPanel.add(doctorScrollPane, gbcRight);

        mainPanel.add(rightPanel);


        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle book appointment button click event
                saveAppointment();
                setVisible(false);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        addDoctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDoctorToTable();
            }
        });

        //setVisible(true);
    }

    private void addDoctorToTable() {
        Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
        doctorTableModel.addRow(new Object[]{selectedDoctor.getFullName()});
    }

    private void saveAppointment() {
        //String selectedDoctor = (String) doctorComboBox.getSelectedItem();


        appointment = new Appointment(yearTextField.getText(), monthTextField.getText(), dayTextField.getText(),
                hourTextField.getText(), minuteTextField.getText(), hospital.ActivePatient, notesTextArea.getText());


        List<String> selectedDoctors;
        selectedDoctors = new ArrayList<>();


        for (int i = 0; i < doctorTableModel.getRowCount(); i++) {
            String doctorFullName = (String) doctorTableModel.getValueAt(i, 0);

            if (doctorFullName != null) {
                selectedDoctors.add(doctorFullName);
            }
        }


        appointment.setDoctors(selectedDoctors);



        hospital.addAppointment(appointment);
        hospital.saveAppointmentsToFile();
    }

    public Appointment getNewAppointment() {
        return appointment;
    }

}

