package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HospitalUserScheduler {
    private JFrame frame;
    private Hospital hospital;
    private JTextArea activePatientTextArea;

    public HospitalUserScheduler(Hospital hospital) {
        this.hospital = hospital;

        frame = new JFrame("Hospital Scheduler. User mode.");
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


        ImagePanel imagePanel = new ImagePanel("src/gui/bcg.jpg");
        // Set layout manager for the frame
        frame.setLayout(new BorderLayout());

        // Create active patient label
        activePatientTextArea = new JTextArea();
        activePatientTextArea.setText(hospital.ActivePatient.getPatientFullInfo());
        activePatientTextArea.setFont(activePatientTextArea.getFont().deriveFont(Font.PLAIN, 24f));
        activePatientTextArea.setForeground(Color.WHITE);
        activePatientTextArea.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        activePatientTextArea.setLineWrap(true); // Enable line wrapping
        activePatientTextArea.setWrapStyleWord(true); // Wrap at word boundaries
        activePatientTextArea.setEditable(false); // Disable editing
        activePatientTextArea.setFocusable(false); // Disable focus
        activePatientTextArea.setHighlighter(null); // Disable text selection
        activePatientTextArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        ShowUserDoctorWindow showDoctorWindow = new ShowUserDoctorWindow(hospital);
        showDoctorWindow.openWindow();
    }

    // Method to open "Show All Appointments" window
    private void showAllAppointments() {
        ShowUserAppointmentsWindow showAppointmentsWindow = new ShowUserAppointmentsWindow(hospital);
    }

    // Method to open "Schedule Appointment" window
    private void scheduleAppointment() {
        AppointmentWindow addNewAppointmentWindow = new AppointmentWindow(hospital);
        addNewAppointmentWindow.setModal(true); // Set the dialog as modal
        addNewAppointmentWindow.setVisible(true);

        addNewAppointmentWindow.dispose();
    }
}
