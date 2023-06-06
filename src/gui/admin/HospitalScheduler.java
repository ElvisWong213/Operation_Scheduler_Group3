package gui.admin;

import javax.swing.*;

import gui.ImagePanel;
import gui.user.AppointmentWindow;
import gui.user.ShowUserAppointmentsWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HospitalScheduler {
    private JFrame frame;

    public HospitalScheduler() {
        frame = new JFrame("Hospital Scheduler. Admin mode.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = 800;
        int height = 600;
        frame.setSize(width, height);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        frame.setLocation(x, y);

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

        // Create menu item "Add New Doctor"
        JMenuItem addDoctorMenuItem = new JMenuItem("Add New Doctor");
        addDoctorMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewDoctor();
            }
        });
        doctorsMenu.add(addDoctorMenuItem);

        // Create "Patients" menu
        JMenu patientsMenu = new JMenu("Patients");
        menuBar.add(patientsMenu);

        // Create menu item "Show All Patients"
        JMenuItem showAllPatientsMenuItem = new JMenuItem("Show All Patients");
        showAllPatientsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAllPatients();
            }
        });
        patientsMenu.add(showAllPatientsMenuItem);

        // Create menu item "Add New Patient"
        JMenuItem addPatientMenuItem = new JMenuItem("Add New Patient");
        addPatientMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewPatient();
            }
        });
        patientsMenu.add(addPatientMenuItem);

        // Create "Appointments" menu
        JMenu appointmentsMenu = new JMenu("Appointments");
        menuBar.add(appointmentsMenu);

        // Create menu item "Show All Appointments"
        JMenuItem showAllAppointmentsMenuItem = new JMenuItem("Show All Appointments");
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



        ImagePanel imagePanel = new ImagePanel();
        // Set layout manager for the frame
        frame.setLayout(new BorderLayout());
        // Add the image panel to the center of the frame
        frame.add(imagePanel, BorderLayout.CENTER);


        frame.setVisible(true);
    }

    // Method to open "Show All Doctors" window
    private void showAllDoctors() {
        ShowDoctorWindow showDoctorWindow = new ShowDoctorWindow();
    }

    // Method to open "Add New Doctor" window
    private void addNewDoctor() {
        AddNewDoctorWindow addNewDoctorWindow = new AddNewDoctorWindow();

        addNewDoctorWindow.setModal(true); // Set the dialog as modal
        addNewDoctorWindow.setVisible(true);

        addNewDoctorWindow.dispose();
    }

    // Method to open "Show All Patients" window
    private void showAllPatients() {
        ShowPatientsWindow showPatientsWindow = new ShowPatientsWindow();
    }

    // Method to open "Add New Patient" window
    private void addNewPatient() {
        AddNewPatientWindow addPatientWindow = new AddNewPatientWindow();

        addPatientWindow.setModal(true); // Set the dialog as modal
        addPatientWindow.setVisible(true);

    }

    // Method to open "Show All Appointments" window
    private void showAllAppointments() {
        ShowUserAppointmentsWindow showAppointmentsWindow = new ShowUserAppointmentsWindow();

    }


    // Method to open "Schedule Appointment" window
    private void scheduleAppointment() {
        AppointmentWindow addNewAppointmentWindow = new AppointmentWindow(null);
        addNewAppointmentWindow.setModal(true); // Set the dialog as modal
        addNewAppointmentWindow.setVisible(true);

        addNewAppointmentWindow.dispose();
    }

}
