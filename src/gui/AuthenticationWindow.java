package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AuthenticationWindow {
    private JFrame authFrame;
    private JTextField usernameTextField;
    private JPasswordField passwordField;

    public AuthenticationWindow() {
        openWindow();
    }

    public void openWindow() {
        authFrame = new JFrame("Authentication");
        authFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        authFrame.setSize(300, 180);
        authFrame.setLocationRelativeTo(null);

        // Create labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        // Create text fields
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Create the main panel and set its layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create and add components to the main panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.add(usernameLabel);
        formPanel.add(usernameTextField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Create login button
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        // Create the button panel and set its layout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(loginButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set layout for the frame
        authFrame.setLayout(new BorderLayout());

        // Add main panel to the frame
        authFrame.add(mainPanel, BorderLayout.CENTER);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());
                // Perform authentication logic here

                if (username.equals("") && password.equals("")) {
                    openMainWindow();
                    authFrame.dispose(); // Close the authentication window
                } else {
                    JOptionPane.showMessageDialog(authFrame, "Invalid username or password");
                }
            }
        });

        // Add action listener to the cancel button
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        authFrame.setVisible(true);
    }

    private void openMainWindow() {
        HospitalScheduler mainWindow = new HospitalScheduler();
    }
}
