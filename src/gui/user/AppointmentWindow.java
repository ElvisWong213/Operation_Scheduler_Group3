package gui.user;

import javax.swing.*;

import backend.appointment.Appointment;
import backend.appointment.AppointmentEntry;
import backend.dataStructure.MyLinkedList;
import backend.dataStructure.MySet;
import backend.type.TreatmentType;
import backend.user.Patient;
import backend.user.Professional;
import backend.user.UserManager;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentWindow extends JDialog {
    private MySet<LocalTime> availableTime;
    private Integer[] dayList;
    private Integer[] monthList;
    private Integer[] yearList;
    
    private JComboBox<Professional> doctorComboBox;
    private DefaultComboBoxModel<Professional> doctorComboBoxModel;

    private JComboBox<Patient> patientComboBox;
    private DefaultComboBoxModel<Patient> patientComboBoxModel;
    
    private JComboBox<Integer> dayComboBox;
    private DefaultComboBoxModel<Integer> dayComboBoxModel;
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<LocalTime> timeComboBox;
    private DefaultComboBoxModel<LocalTime> timeComboBoxModel;
    
    private JComboBox<TreatmentType> treatmentTypeComboBox;
    
    private JTextArea notesTextArea;
    private String userName;
    private Patient patient;
    private Professional professional;
    private AppointmentEntry appointmentEntry;

    public AppointmentWindow(Patient patient, AppointmentEntry appointmentEntry) {
        this.patient = patient;
        this.userName = patient.getName();
        this.appointmentEntry = appointmentEntry;
        openWindow();
    }

    public AppointmentWindow(Professional professional, AppointmentEntry appointmentEntry) {
        this.professional = professional;
        this.userName = professional.getName();
        this.appointmentEntry = appointmentEntry;
        openWindow();
    }

    public AppointmentWindow(AppointmentEntry appointmentEntry) {
        this.appointmentEntry = appointmentEntry;
        openWindow();
    }

    public void openWindow() {
        LocalDate now = LocalDate.now();
        this.availableTime = new MySet<>();
        this.dayList = getDayList(now.getMonthValue(), now.getYear());
        this.monthList = getMonthList();
        this.yearList = getYearList(now.getYear());

        if (appointmentEntry == null) {
            setTitle("Make Appointment");
        } else {
            setTitle("Edit Appointment");
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Create labels
        JLabel patientLabel = new JLabel("Patient: ");
        JLabel nameLabel = new JLabel(userName);
        JLabel dateLabel = new JLabel("Date:");
        JLabel timeLabel = new JLabel("Time:");
        JLabel doctorLabel = new JLabel("Doctor:");
        JLabel notesLabel = new JLabel("Notes:");
        JLabel treatmentTypeLabel = new JLabel("Treatment Type: ");

        // Create doctor selection combo box
        MyLinkedList<Professional> doctors = UserManager.getAllProfessionals();
        doctorComboBoxModel = new DefaultComboBoxModel<>(doctors.toArray(new Professional[0]));
        doctorComboBox = new JComboBox<>(doctorComboBoxModel);
        doctorComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Professional) {
                    Professional doctor = (Professional) value;
                    setText(doctor.getName());
                }
                return this;
            }
        });
        if (appointmentEntry != null) {
            Professional professionalTS = new Professional();
            try {
                professionalTS.getUserById(appointmentEntry.getProfessionalID());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int index = doctors.indexOf(professionalTS);
            doctorComboBox.setSelectedIndex(index);
        }

        // Create patient selection combo box
        MyLinkedList<Patient> patients = UserManager.getAllPatients();
        patientComboBoxModel = new DefaultComboBoxModel<>(patients.toArray(new Patient[0]));
        patientComboBox = new JComboBox<>(patientComboBoxModel);
        patientComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Patient) {
                    Patient patient = (Patient) value;
                    setText(patient.getName());
                }
                return this;
            }
        });
        if (appointmentEntry != null) {
            Patient PatientTS = new Patient();
            try {
                PatientTS.getUserById(appointmentEntry.getPatientID());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int index = patients.indexOf(PatientTS);
            patientComboBox.setSelectedIndex(index);
        }
        
        // Create date selection combo box
        dayComboBoxModel = new DefaultComboBoxModel<>(dayList);
        dayComboBox = new JComboBox<>(dayComboBoxModel);
        monthComboBox = new JComboBox<>(monthList);
        yearComboBox = new JComboBox<>(yearList);
        if (appointmentEntry == null) {
            dayComboBox.setSelectedItem(now.getDayOfMonth());
            monthComboBox.setSelectedItem(now.getMonthValue());
            yearComboBox.setSelectedItem(now.getYear());
        } else {
            LocalDate editDate = appointmentEntry.getDate().toLocalDate();
            int editDay = editDate.getDayOfMonth();
            int editMonth = editDate.getMonthValue();
            int editYear = editDate.getYear();
            dayComboBox.setSelectedItem(editDay);
            monthComboBox.setSelectedItem(editMonth);
            yearComboBox.setSelectedItem(editYear);
            now = editDate;
        }
        
        // Create time selection combo box
        if (patient != null) {
            this.availableTime = UserManager.availableTime(now, (Patient) patientComboBox.getSelectedItem(), professional);
        } else if (professional != null) {
            this.availableTime = UserManager.availableTime(now, patient, (Professional) doctorComboBox.getSelectedItem());
        } else {
            this.availableTime = UserManager.availableTime(now, (Patient) patientComboBox.getSelectedItem(), (Professional) doctorComboBox.getSelectedItem());
        }
        if (appointmentEntry != null) {
            this.availableTime.add(appointmentEntry.getStartTime().toLocalTime());
        }
        timeComboBoxModel = new DefaultComboBoxModel<>(availableTime.toArray(new LocalTime[0]));
        timeComboBox = new JComboBox<>(timeComboBoxModel);
        timeComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    LocalTime startTime = (LocalTime) value;
                    LocalTime endTime = startTime.plusHours(1);
                    setText(startTime.toString() + " - " + endTime.toString());
                }
                return this;
            }
        });
        if (appointmentEntry != null) {
            timeComboBoxModel.setSelectedItem(appointmentEntry.getStartTime().toLocalTime());
        }

        // Create treatment type combo box
        treatmentTypeComboBox = new JComboBox<>(TreatmentType.values());
        if (appointmentEntry != null) {
            treatmentTypeComboBox.setSelectedItem(appointmentEntry.getTreatmentType());
        }

        // Create notes text area
        notesTextArea = new JTextArea();
        JScrollPane notesScrollPane = new JScrollPane(notesTextArea);
        if (appointmentEntry != null) {
            notesTextArea.setText(appointmentEntry.getDescription());
        }


        // Create buttons
        JButton bookButton = new JButton("Book Appointment");
        if (appointmentEntry != null) {
            bookButton = new JButton("Edit Appointment");
        }
        JButton cancelButton = new JButton("Cancel");

        // Create the bottom panel and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add buttons to the bottom panel
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
        leftPanel.add(patientLabel, gbcLeft);
        
        gbcLeft.gridy = 1;
        leftPanel.add(doctorLabel, gbcLeft);

        gbcLeft.gridy = 2;
        leftPanel.add(dateLabel, gbcLeft);

        gbcLeft.gridy = 3;
        leftPanel.add(timeLabel, gbcLeft);

        gbcLeft.gridy = 4;
        leftPanel.add(treatmentTypeLabel, gbcLeft);

        gbcLeft.gridy = 5;
        leftPanel.add(notesLabel, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 0;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        gbcLeft.gridwidth = 3;
        if (patient != null) {
            leftPanel.add(nameLabel, gbcLeft);
        } else {
            leftPanel.add(patientComboBox, gbcLeft);
        }

        gbcLeft.gridy = 1;
        if (professional != null) {
            leftPanel.add(nameLabel, gbcLeft);
        } else {
            leftPanel.add(doctorComboBox, gbcLeft);
        }
        
        gbcLeft.gridy = 2;
        gbcLeft.gridwidth = 1;
        leftPanel.add(dayComboBox, gbcLeft);

        gbcLeft.gridx = 2;
        leftPanel.add(monthComboBox, gbcLeft);

        gbcLeft.gridx = 3;
        leftPanel.add(yearComboBox, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 3;
        gbcLeft.gridwidth = 3;
        leftPanel.add(timeComboBox, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 4;
        gbcLeft.gridwidth = 3;
        gbcLeft.fill = GridBagConstraints.BOTH;
        leftPanel.add(treatmentTypeComboBox, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 5;
        gbcLeft.weighty = 2.0;
        leftPanel.add(notesScrollPane, gbcLeft);

        gbcLeft.gridx = 1;
        gbcLeft.gridy = 4;
        gbcLeft.gridwidth = 3;
        gbcLeft.fill = GridBagConstraints.NONE;
        gbcLeft.weighty = 0.0;
        leftPanel.add(new JLabel(), gbcLeft);

        mainPanel.add(leftPanel);


        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle book appointment button click event
                if (isFieldsNull()) {
                    JOptionPane.showMessageDialog(mainPanel, "Sorry, that day is fully booked. Please choose another day");
                } else {
                    if (appointmentEntry != null) {
                        editAppointment(appointmentEntry);
                    } else {
                        saveAppointment();
                    }
                    setVisible(false);
                    dispose();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
        
        patientComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeComboBox();
            }
        });

        doctorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeComboBox();
            }
        });

        dayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDayTimeComboBox();
            }
        });
        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDayTimeComboBox();
            }
        });
        yearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDayTimeComboBox();
            }
        });

        updateDayTimeComboBox();

        //setVisible(true);
    }

    private void saveAppointment() {
        int selectedDay = (int) dayComboBoxModel.getSelectedItem();
        int selectedMonth = (int) monthComboBox.getSelectedItem();
        int selectedYear = (int) yearComboBox.getSelectedItem();
        Date date = Date.valueOf(String.format("%04d-%02d-%02d", selectedYear, selectedMonth, selectedDay));
        Time startTime = Time.valueOf((LocalTime) timeComboBoxModel.getSelectedItem());
        Time endTime = Time.valueOf(((LocalTime) timeComboBoxModel.getSelectedItem()).plusHours(1));
        TreatmentType treatmentType = (TreatmentType) treatmentTypeComboBox.getSelectedItem();
        int professionalID = 0;
        int patientID = 0;
        if (patient != null) {
            professionalID = ((Professional) doctorComboBoxModel.getSelectedItem()).getProfessionalID();
            patientID = patient.getPatientID();
        } else if (professional != null) {
            professionalID = professional.getProfessionalID();
            patientID = ((Patient) patientComboBoxModel.getSelectedItem()).getPatientID(); 
        } else {
            professionalID = ((Professional) doctorComboBoxModel.getSelectedItem()).getProfessionalID();
            patientID = ((Patient) patientComboBoxModel.getSelectedItem()).getPatientID(); 
        }
        AppointmentEntry appointment = new AppointmentEntry(date, startTime, endTime, treatmentType, notesTextArea.getText(), professionalID, patientID);

        Appointment.bookAppointment(appointment);
    }

    private void editAppointment(AppointmentEntry oldAppointmentEntry) {  
        int id = oldAppointmentEntry.getId();      
        int selectedDay = (int) dayComboBoxModel.getSelectedItem();
        int selectedMonth = (int) monthComboBox.getSelectedItem();
        int selectedYear = (int) yearComboBox.getSelectedItem();
        Date date = Date.valueOf(String.format("%04d-%02d-%02d", selectedYear, selectedMonth, selectedDay));
        Time startTime = Time.valueOf((LocalTime) timeComboBoxModel.getSelectedItem());
        Time endTime = Time.valueOf(((LocalTime) timeComboBoxModel.getSelectedItem()).plusHours(1));
        TreatmentType treatmentType = (TreatmentType) treatmentTypeComboBox.getSelectedItem();
        int professionalID = 0;
        int patientID = 0;
        if (patient != null) {
            professionalID = ((Professional) doctorComboBoxModel.getSelectedItem()).getProfessionalID();
            patientID = patient.getPatientID();
        } else if (professional != null) {
            professionalID = professional.getProfessionalID();
            patientID = ((Patient) patientComboBoxModel.getSelectedItem()).getPatientID(); 
        } else {
            professionalID = ((Professional) doctorComboBoxModel.getSelectedItem()).getProfessionalID();
            patientID = ((Patient) patientComboBoxModel.getSelectedItem()).getPatientID(); 
        }
        AppointmentEntry appointment = new AppointmentEntry(id, date, startTime, endTime, treatmentType, notesTextArea.getText(), professionalID, patientID);

        Appointment.editAppointment(appointment);
    }

    private Integer[] getDayList(int month, int year) {
        int[] numberOfDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int maxDay = numberOfDay[month - 1];
        if (year % 4 == 0 && month == 2) {
            maxDay += 1;
        }
        Integer[] dayList = new Integer[maxDay];
        for (int i = 0; i < maxDay; i++) {
            dayList[i] = i + 1;
        }
        return dayList;
    }

    private Integer[] getMonthList() {
        Integer[] month = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        return month;
    }
    
    private Integer[] getYearList(int year) {
        int size = 20;
        Integer[] yearList = new Integer[size];
        for (int i = 0; i < size; i++) {
            yearList[i] = year + i;
        }
        return yearList;
    }

    private void updateDayComboBox() {
        Integer selectedDay = (Integer) dayComboBoxModel.getSelectedItem();
        dayList = getDayList((int) monthComboBox.getSelectedItem(), (int) yearComboBox.getSelectedItem());
        dayComboBoxModel.removeAllElements();
        for (Integer element : dayList) {
            dayComboBoxModel.addElement(element);
        }
        if (selectedDay != null) {
            if (selectedDay > dayList.length) {
                selectedDay = dayList.length;
            }
            dayComboBoxModel.setSelectedItem(selectedDay);
        }
    }

    private void updateTimeComboBox() {
        int selectedDay = (int) dayComboBoxModel.getSelectedItem();
        int selectedMonth = (int) monthComboBox.getSelectedItem();
        int selectedYear = (int) yearComboBox.getSelectedItem();
        LocalDate selectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
        if (patient != null) {
            availableTime = UserManager.availableTime(selectedDate, patient, (Professional) doctorComboBox.getSelectedItem());
        } else if (professional != null) {
            availableTime = UserManager.availableTime(selectedDate, (Patient) patientComboBox.getSelectedItem(), professional);
        } else {
            availableTime = UserManager.availableTime(selectedDate, (Patient) patientComboBox.getSelectedItem(), (Professional) doctorComboBox.getSelectedItem());
        }
        timeComboBoxModel.removeAllElements();
        for (LocalTime time : availableTime.toArray(new LocalTime[0])) {
            timeComboBoxModel.addElement(time);
        }
    }

    private void updateDayTimeComboBox() {
        Thread t1 = new Thread(() -> {
            updateDayComboBox();
        });
        Thread t2 = new Thread(() -> {
            updateTimeComboBox();
        });

        t1.start();

        try {
            t1.join();
            t2.start();
            t2.join();
        } catch (InterruptedException f) {
            f.printStackTrace();
        }
    }

    private boolean isFieldsNull() {
        if (doctorComboBoxModel.getSelectedItem() == null) {
            return true;
        }
        if (dayComboBoxModel.getSelectedItem() == null) {
            return true;
        }
        if (monthComboBox.getSelectedItem() == null) {
            return true;
        }
        if (yearComboBox.getSelectedItem() == null) {
            return true;
        }
        if (timeComboBoxModel.getSelectedItem() == null) {
            return true;
        }
        if (treatmentTypeComboBox.getSelectedItem() == null) {
            return true;
        }
        return false;
    }
}

