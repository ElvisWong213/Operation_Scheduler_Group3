package gui.user;

import javax.swing.*;

import gui.ImagePanel;
import user.Patient;
import user.Professional;
import user.UserManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HospitalUserScheduler {
    private JFrame frame;
    private Patient patient;
    private Professional professional;
    private JTextArea activePatientTextArea;

    public HospitalUserScheduler(Patient patient) {
        this.patient = patient;

        initializeWindows();

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Create "Doctors" menu
        JMenu doctorsMenu = new JMenu("Doctors");
        menuBar.add(doctorsMenu);

        // Create menu item "Show All Doctors"
        JMenuItem showAllDoctorsMenuItem = new JMenuItem("Show All Doctors");
        showAllDoctorsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAllDoctors();
            }
        });
        doctorsMenu.add(showAllDoctorsMenuItem);


        // Create "Appointments" menu
        JMenu appointmentsMenu = new JMenu("Appointments");
        menuBar.add(appointmentsMenu);

        // Create menu item "Show All Appointments"
        JMenuItem showAllAppointmentsMenuItem = new JMenuItem("Show My Appointments");
        showAllAppointmentsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAllAppointments();
            }
        });
        appointmentsMenu.add(showAllAppointmentsMenuItem);

        // Create menu item "Schedule Appointment"
        JMenuItem scheduleAppointmentMenuItem = new JMenuItem("Schedule Appointment");
        scheduleAppointmentMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduleAppointment();
            }
        });
        appointmentsMenu.add(scheduleAppointmentMenuItem);

        // Create active patient label
        activePatientTextArea.setText(patient.getFullInfo());
    }

    public HospitalUserScheduler(Professional professional) {
        this.professional = professional;
        initializeWindows();

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Create "Doctors" menu
        JMenu doctorsMenu = new JMenu("Doctors");
        menuBar.add(doctorsMenu);

        // Create menu item "Show All Doctors"
        JMenuItem showAllDoctorsMenuItem = new JMenuItem("Show All Doctors");
        showAllDoctorsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAllDoctors();
            }
        });
        doctorsMenu.add(showAllDoctorsMenuItem);

        
        // Create "Patients" menu
        JMenu patientsMenu = new JMenu("Patients");
        menuBar.add(patientsMenu);

        JMenuItem showAllPatientsMenuItem = new JMenuItem("Show All Patients");
        showAllPatientsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAllPatients();
            }
        });
        patientsMenu.add(showAllPatientsMenuItem);


        // Create "Appointments" menu
        JMenu appointmentsMenu = new JMenu("Appointments");
        menuBar.add(appointmentsMenu);

        // Create menu item "Show All Appointments"
        JMenuItem showAllAppointmentsMenuItem = new JMenuItem("Show My Appointments");
        showAllAppointmentsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAllAppointments();
            }
        });
        appointmentsMenu.add(showAllAppointmentsMenuItem);

        // Create menu item "Schedule Appointment"
        JMenuItem scheduleAppointmentMenuItem = new JMenuItem("Schedule Appointment");
        scheduleAppointmentMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduleAppointment();
            }
        });
        appointmentsMenu.add(scheduleAppointmentMenuItem);

        // Create "Diary" menu
        JMenu diaryMenu = new JMenu("Diary");
        menuBar.add(diaryMenu);

        // Create menu item "Show My Diary"
        JMenuItem showMyDiaryMenuItem = new JMenuItem("Show My Diary");
        showMyDiaryMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowDiaryWindow showDiaryWindow = new ShowDiaryWindow(professional);
            }
        });
        diaryMenu.add(showMyDiaryMenuItem);

        // Create active patient label
        activePatientTextArea.setText(professional.getFullInfo());
    }

    private void initializeWindows() {
        frame = new JFrame("Hospital Scheduler. User mode.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = 800;
        int height = 600;
        frame.setSize(width, height);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        frame.setLocation(x, y);

        activePatientTextArea = new JTextArea();
        activePatientTextArea.setFont(activePatientTextArea.getFont().deriveFont(Font.PLAIN, 24f));
        activePatientTextArea.setForeground(Color.WHITE);
        activePatientTextArea.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        activePatientTextArea.setLineWrap(true); // Enable line wrapping
        activePatientTextArea.setWrapStyleWord(true); // Wrap at word boundaries
        activePatientTextArea.setEditable(false); // Disable editing
        activePatientTextArea.setFocusable(false); // Disable focus
        activePatientTextArea.setHighlighter(null); // Disable text selection
        activePatientTextArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        ImagePanel imagePanel = new ImagePanel();
        // Set layout manager for the frame
        frame.setLayout(new BorderLayout());

        // Add the active patient label to the top left corner of the image panel
        imagePanel.setLayout(null); // Set layout manager to null for absolute positioning
        activePatientTextArea.setBounds(20, 20, 450, 300);
        imagePanel.add(activePatientTextArea);

        // Add the image panel to the center of the frame
        frame.add(imagePanel, BorderLayout.CENTER);

        frame.setVisible(true);

    }

    // Method to open "Show All Doctors" window
    private void showAllDoctors() {
        ShowUserDoctorWindow showDoctorWindow = new ShowUserDoctorWindow(UserManager.getAllProfessionals());
    }

    private void showAllPatients() {
        ShowUserPatientsWindow showUserPatientsWindow = new ShowUserPatientsWindow(UserManager.getAllPatients());
    }

    // Method to open "Show All Appointments" window
    private void showAllAppointments() {
        ShowUserAppointmentsWindow showAppointmentsWindow = null;
        if (patient != null) {
            showAppointmentsWindow = new ShowUserAppointmentsWindow(patient);
        } else if (professional != null) {
            showAppointmentsWindow = new ShowUserAppointmentsWindow(professional);
        }
    }

    // Method to open "Schedule Appointment" window
    private void scheduleAppointment() {
        AppointmentWindow addNewAppointmentWindow = null;
        if (patient != null) {
            addNewAppointmentWindow = new AppointmentWindow(patient, null);
        } else if (professional != null) {
            addNewAppointmentWindow = new AppointmentWindow(professional, null);
        }
        addNewAppointmentWindow.setModal(true); // Set the dialog as modal
        addNewAppointmentWindow.setVisible(true);

        addNewAppointmentWindow.dispose();
    }
}
