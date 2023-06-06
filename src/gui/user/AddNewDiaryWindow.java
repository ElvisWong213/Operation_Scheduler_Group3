package gui.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import appointment.Appointment;
import appointment.AppointmentEntry;
import dataStructure.MyLinkedList;
import dataStructure.exception.DuplicateElementException;
import diary.Diary;
import diary.DiaryEntry;
import user.Patient;

public class AddNewDiaryWindow extends JDialog {
    private JLabel appointmentLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private JLabel patientLabel;
    private JLabel noteLabel;

    private JLabel appointmentDateLabel;
    private JLabel appointmentTimeLabel;
    private JLabel appointmentPatientLabel;

    private JComboBox<AppointmentEntry> appointmentComboBox;
    private JTextArea noteTextArea;
    private JScrollPane noteScrollPane;

    private Appointment appointment;
    private int professionalID;

    public AddNewDiaryWindow(Diary diary, int professionalID) {
        setTitle("Add New Diary");

        this.appointment = new Appointment();
        this.appointment.getProfessionAppointmentsDoNotHaveDiary(professionalID);
        this.professionalID = professionalID;
        createComponents(null);
        createLayout(diary, null);
        setAppointmentLabel(null);
    }

    public AddNewDiaryWindow(Diary diary, DiaryEntry diaryEntry) {
        setTitle("Edit Diary");

        createComponents(diaryEntry);
        createLayout(diary, diaryEntry);
        setAppointmentLabel(diaryEntry);
    }

    private void createComponents(DiaryEntry diaryEntry) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        appointmentLabel = new JLabel("Appointments: ");
        dateLabel = new JLabel("Date: ");
        timeLabel = new JLabel("Time: ");
        patientLabel = new JLabel("Patient Name:");
        noteLabel = new JLabel("Note: ");

        appointmentDateLabel = new JLabel();
        appointmentTimeLabel = new JLabel();
        appointmentPatientLabel = new JLabel();

        if (diaryEntry == null) {
            MyLinkedList<AppointmentEntry> appointments = appointment.getAppointments();
            DefaultComboBoxModel<AppointmentEntry> appointmentComboBoxModel = new DefaultComboBoxModel<>(
                    appointments.toArray(new AppointmentEntry[0]));
            appointmentComboBox = new JComboBox<>(appointmentComboBoxModel);
            appointmentComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected,
                        boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof AppointmentEntry) {
                        AppointmentEntry appointmentEntry = (AppointmentEntry) value;
                        Patient patient = new Patient();
                        try {
                            patient.getUserById(appointmentEntry.getPatientID());
                            setText(patient.getName() + " " + appointmentEntry.getDate() + " "
                                    + appointmentEntry.getStartTime());
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    return this;
                }
            });
        }

        noteTextArea = new JTextArea();
        noteTextArea.setLineWrap(true);
        noteTextArea.setWrapStyleWord(true);

        noteScrollPane = new JScrollPane(noteTextArea);

        if (diaryEntry != null) {
            noteTextArea.setText(diaryEntry.getNote());
        }
    }

    private void createLayout(Diary diary ,DiaryEntry diaryEntry) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        if (diaryEntry == null) {
            mainPanel.add(createPaddedPanel(appointmentLabel, 20), gbc);
        }

        gbc.gridy = 1;
        mainPanel.add(createPaddedPanel(dateLabel, 20), gbc);

        gbc.gridy = 2;
        mainPanel.add(createPaddedPanel(timeLabel, 20), gbc);

        gbc.gridy = 3;
        mainPanel.add(createPaddedPanel(patientLabel, 20), gbc);

        gbc.gridy = 4;
        mainPanel.add(createPaddedPanel(noteLabel, 20), gbc);

        gbc.gridx = 1;
        if (diaryEntry == null) {
            gbc.gridy = 0;
            mainPanel.add(appointmentComboBox, gbc);
        }

        gbc.gridy = 1;
        mainPanel.add(appointmentDateLabel, gbc);

        gbc.gridy = 2;
        mainPanel.add(appointmentTimeLabel, gbc);

        gbc.gridy = 3;
        mainPanel.add(appointmentPatientLabel, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 2;
        gbc.weightx = 2;
        mainPanel.add(noteScrollPane, gbc);

        JButton addButton = new JButton();
        if (diaryEntry == null) {
            addButton.setText("Add Diary");
        } else {
            addButton.setText("Save");
        }
        JButton cancelButton = new JButton("Cancel");

        // Create the bottom panel and set its layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add buttons to the bottom panel
        bottomPanel.add(addButton);
        bottomPanel.add(cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        if (diaryEntry == null) {
            appointmentComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setAppointmentLabel(diaryEntry);
                }
            });
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (diaryEntry == null) {
                    AppointmentEntry selectedAppointmentEntry = (AppointmentEntry) appointmentComboBox.getSelectedItem();
                    Date date = selectedAppointmentEntry.getDate();
                    Time time = selectedAppointmentEntry.getStartTime();
                    String note = noteTextArea.getText();
                    int patientID = selectedAppointmentEntry.getPatientID();
    
                    DiaryEntry newDiaryEntry = new DiaryEntry(date, time, note, professionalID, patientID);
                    try {
                        diary.addEntry(newDiaryEntry, true);
                        dispose();
                    } catch (DuplicateElementException e1) {
                        JOptionPane.showMessageDialog(AddNewDiaryWindow.this, "Duplicate element.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    diaryEntry.setNote(noteTextArea.getText());
                    diary.editEntry(diaryEntry, true);
                    dispose();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private JPanel createPaddedPanel(JComponent component, int padding) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, padding, 0, 0));
        panel.add(component, BorderLayout.WEST);
        return panel;
    }

    private void setAppointmentLabel(DiaryEntry diaryeEntry) {
        Patient patient = new Patient();
        if (diaryeEntry == null) {
            AppointmentEntry appointmentEntry = (AppointmentEntry) appointmentComboBox.getSelectedItem();
            try {
                patient.getUserById(appointmentEntry.getPatientID());
                appointmentDateLabel.setText(appointmentEntry.getDate().toLocalDate().toString());
                appointmentTimeLabel.setText(appointmentEntry.getStartTime().toLocalTime().toString());
                appointmentPatientLabel.setText(patient.getName());
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else {
            try {
                patient.getUserById(diaryeEntry.getPatientID());
                appointmentDateLabel.setText(diaryeEntry.getDate().toLocalDate().toString());
                appointmentTimeLabel.setText(diaryeEntry.getTime().toLocalTime().toString());
                appointmentPatientLabel.setText(patient.getName());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
