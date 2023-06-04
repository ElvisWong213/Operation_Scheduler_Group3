package gui;

import javax.swing.*;

import gui.admin.AddNewPatientWindow;
import gui.admin.HospitalScheduler;
import gui.user.HospitalUserScheduler;
import user.Professional;
import user.Patient;

import java.awt.*;
import java.awt.event.*;

public class AuthenticationWindow {
    private JFrame authFrame;
    private JTextField usernameTextField;
    private JPasswordField passwordField;

    private Patient patient;
    private Professional professional;
    private boolean isAdmin;

    public AuthenticationWindow() {
        this.professional = new Professional();
        this.patient = new Patient();
        this.isAdmin = false;
        openWindow();
    }

    public void openWindow() {
        authFrame = new JFrame("Authentication");
        authFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        authFrame.setSize(300, 210);
        authFrame.setLocationRelativeTo(null);

        // Create labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        // Create text fields
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        //-------

        // Create the main panel and set its layout
        ImagePanel mainPanel = new ImagePanel("src/gui/bcg.jpg");
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        // Create and add components to the main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 10));

        formPanel.setBackground(Color.WHITE);

        formPanel.add(usernameLabel);
        formPanel.add(usernameTextField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);



        // Create login button
        JButton loginButton = new JButton("Login");
        JButton newPatientButton = new JButton("Register New User");
        JButton cancelButton = new JButton("Cancel");

        topPanel.add(newPatientButton, BorderLayout.NORTH);
        topPanel.add(Box.createRigidArea(new Dimension(1, 30)), BorderLayout.CENTER);
        topPanel.add(formPanel, BorderLayout.SOUTH);


        topPanel.setBackground(Color.WHITE);

        // Set layout for the frame
        authFrame.setLayout(new BorderLayout());

        // Add panels to the main panel
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create and add bottom button panel
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomButtonPanel.add(cancelButton);
        bottomButtonPanel.add(loginButton);

        bottomButtonPanel.setBackground(Color.WHITE);

        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        // Set the size of the register button
        newPatientButton.setPreferredSize(new Dimension(mainPanel.getWidth() - 40, 26));

        // Create invisible spacing panel
        JPanel spacingPanel = new JPanel();
        spacingPanel.setPreferredSize(new Dimension(1, 20));
        mainPanel.add(spacingPanel, BorderLayout.EAST);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());

                boolean isAuthenticated = (professional.performLogin(username, password) || patient.performLogin(username, password));

                if (username.equals("admin") && password.equals("admin")) {
                    isAuthenticated = true;
                    isAdmin = true;
                }

                if (isAuthenticated) {
                    openMainWindow();
                    authFrame.dispose(); // Close the authentication window
                } else {
                    JOptionPane.showMessageDialog(authFrame, "Invalid username or password");
                }
            }
        });

        // Add action listener to the new patient button
        newPatientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewPatientWindow();
            }
        });

        // Add action listener to the cancel button
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        authFrame.add(mainPanel, BorderLayout.CENTER);
        authFrame.setVisible(true);
    }

    private void openMainWindow() {
        if (isAdmin)
        {
            HospitalScheduler mainWindow = new HospitalScheduler();
        }
        else
        {
            if (professional.getLoginState()) {

            } else if (patient.getLoginState()) {
                HospitalUserScheduler mainWindow = new HospitalUserScheduler(patient);
            }
        }
    }

    private void openNewPatientWindow() {
        AddNewPatientWindow newPatientWindow = new AddNewPatientWindow();
        newPatientWindow.setVisible(true);
    }
}
