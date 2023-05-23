package gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;



public class HospitalScheduler
{
    private JFrame frame;
    // Остальной код...

    public HospitalScheduler()
    {
        frame = new JFrame("Hospital Scheduler");
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


        frame.setVisible(true);
    }

    // Method to open "Show All Doctors" window
    private void showAllDoctors() {
        ShowDoctorWindow addDoctorWindow = new ShowDoctorWindow();
        addDoctorWindow.openWindow();
    }

    // Method to open "Add New Doctor" window
    // Method to open "Add New Doctor" window
    private void addNewDoctor() {
        AddNewDoctorWindow addNewDoctorWindow = new AddNewDoctorWindow();
        addNewDoctorWindow.openWindow();
    }



    // Method to open "Show All Patients" window
    private void showAllPatients() {
        ShowPatientsWindow showPatientsWindow = new ShowPatientsWindow();
        showPatientsWindow.openWindow();
    }

    // Method to open "Add New Patient" window
    private void addNewPatient() {

        AddNewPatientWindow addPatientWindow = new AddNewPatientWindow();
        addPatientWindow.openWindow();

            }


    // Method to open "Show All Appointments" window
    private void showAllAppointments() {
        ShowAppointmentsWindow showAppointmentsWindow = new ShowAppointmentsWindow();
        showAppointmentsWindow.openWindow();
    }

    // Method to open "Schedule Appointment" window
    private void scheduleAppointment() {
        AppointmentWindow appointmentWindow = new AppointmentWindow("John Doe");
        //appointmentWindow.openWindow();
    }

}








