package gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.basic.Hospital;
import gui.basic.Patient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewPatientWindow extends JDialog {
    private Hospital hospital;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel ageLabel;
    private JLabel weightLabel;
    private JLabel heightLabel;
    private JLabel phoneLabel;
    private JLabel addressLabel;
    private JLabel notesLabel;
    private JLabel loginLabel;
    private JLabel passwordLabel;

    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField ageTextField;
    private JTextField weightTextField;
    private JTextField heightTextField;
    private JTextField phoneTextField;
    private JTextField addressTextField;
    private JTextArea notesTextArea;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;

    private JButton addButton;
    private JButton cancelButton;

    private Patient newPatient;

    public AddNewPatientWindow(Hospital hospital) {
        this.hospital = hospital;
        setTitle("Add New Patient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        createComponents();
        createLayout();

        newPatient = null;
    }

    private void createComponents() {
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        ageLabel = new JLabel("Age:");
        weightLabel = new JLabel("Weight:");
        heightLabel = new JLabel("Height:");
        phoneLabel = new JLabel("Phone:");
        addressLabel = new JLabel("Address:");
        notesLabel = new JLabel("Notes:");
        loginLabel = new JLabel("Login:");
        passwordLabel = new JLabel("Password:");

        firstNameTextField = new JTextField();
        lastNameTextField = new JTextField();
        ageTextField = new JTextField();
        weightTextField = new JTextField();
        heightTextField = new JTextField();
        phoneTextField = new JTextField();
        addressTextField = new JTextField();
        notesTextArea = new JTextArea();
        loginTextField = new JTextField();
        passwordTextField = new JPasswordField();

        // Set preferred height for text fields based on font size
        Font textFieldFont = firstNameTextField.getFont();
        int preferredHeight = textFieldFont.getSize() + 8; // 8 is an arbitrary value for padding
        Dimension textFieldDimension = new Dimension(firstNameTextField.getPreferredSize().width, preferredHeight);
        firstNameTextField.setPreferredSize(textFieldDimension);
        lastNameTextField.setPreferredSize(textFieldDimension);
        ageTextField.setPreferredSize(textFieldDimension);
        weightTextField.setPreferredSize(textFieldDimension);
        heightTextField.setPreferredSize(textFieldDimension);
        phoneTextField.setPreferredSize(textFieldDimension);
        addressTextField.setPreferredSize(textFieldDimension);
        notesTextArea.setLineWrap(true);
        loginTextField.setPreferredSize(textFieldDimension);
        passwordTextField.setPreferredSize(textFieldDimension);

        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
    }

    private void createLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10));

        mainPanel.add(createPaddedPanel(firstNameLabel, 20));
        mainPanel.add(firstNameTextField);
        mainPanel.add(createPaddedPanel(lastNameLabel, 20));
        mainPanel.add(lastNameTextField);
        mainPanel.add(createPaddedPanel(ageLabel, 20));
        mainPanel.add(ageTextField);
        mainPanel.add(createPaddedPanel(weightLabel, 20));
        mainPanel.add(weightTextField);
        mainPanel.add(createPaddedPanel(heightLabel, 20));
        mainPanel.add(heightTextField);
        mainPanel.add(createPaddedPanel(phoneLabel, 20));
        mainPanel.add(phoneTextField);
        mainPanel.add(createPaddedPanel(addressLabel, 20));
        mainPanel.add(addressTextField);
        mainPanel.add(createPaddedPanel(notesLabel, 20));
        mainPanel.add(notesTextArea);
        mainPanel.add(createPaddedPanel(loginLabel, 20));
        mainPanel.add(loginTextField);
        mainPanel.add(createPaddedPanel(passwordLabel, 20));
        mainPanel.add(passwordTextField);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(addButton);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                int age = Integer.parseInt(ageTextField.getText());
                double weight = Double.parseDouble(weightTextField.getText());
                double height = Double.parseDouble(heightTextField.getText());
                String phone = phoneTextField.getText();
                String address = addressTextField.getText();
                String notes = notesTextArea.getText();
                String login = loginTextField.getText();
                String password = new String(passwordTextField.getPassword());

                newPatient = new Patient(firstName, lastName, age, weight, height, phone, address, notes, login, password);
                hospital.addPatient(newPatient);

                JOptionPane.showMessageDialog(AddNewPatientWindow.this, "Patient added successfully!");

                clearFields();
                setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
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

    private void clearFields() {
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        ageTextField.setText("");
        weightTextField.setText("");
        heightTextField.setText("");
        phoneTextField.setText("");
        addressTextField.setText("");
        notesTextArea.setText("");
        loginTextField.setText("");
        passwordTextField.setText("");
    }

    public Patient getNewPatient() {
        return newPatient;
    }
}
