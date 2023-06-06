package gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import backend.type.Profession;
import backend.user.Professional;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddNewDoctorWindow extends JDialog  {
    private JLabel nameLabel;
    private JLabel workLocationLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel specializationLabel;

    private JTextField nameTextField;
    private JTextField workLocationTextField;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JComboBox<Profession> specializationComboBox;

    private Professional professional;

    public AddNewDoctorWindow() {
        setTitle("Add New Doctor");

        createComponents();
        createLayout();
    }

    public AddNewDoctorWindow(Professional professional) {
        this.professional = professional;

        setTitle("Edit Doctor");

        createComponents();
        createLayout();
    }

    private void createComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        nameLabel = new JLabel("Name:");
        workLocationLabel = new JLabel("Work Location:");
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        specializationLabel = new JLabel("Specialization:");

        nameTextField = new JTextField();
        workLocationTextField = new JTextField();
        emailTextField = new JTextField();
        passwordField = new JPasswordField();

        // Set preferred height for text fields based on font size
        Font textFieldFont = nameTextField.getFont();
        int preferredHeight = textFieldFont.getSize() + 8; // 8 is an arbitrary value for padding
        Dimension textFieldDimension = new Dimension(nameTextField.getPreferredSize().width, preferredHeight);
        nameTextField.setPreferredSize(textFieldDimension);
        workLocationTextField.setPreferredSize(textFieldDimension);
        emailTextField.setPreferredSize(textFieldDimension);
        passwordField.setPreferredSize(textFieldDimension);

        Profession[] specializations = Profession.values();
        specializationComboBox = new JComboBox<>(specializations);

        if (professional != null) {
            nameTextField.setText(professional.getName());
            workLocationTextField.setText(professional.getWorkLocation());
            emailTextField.setText(professional.getEmail());
            passwordField.setText(professional.getPassword());
            specializationComboBox.setSelectedItem(professional.getProfession());
        }
    }

    private void createLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10));

        mainPanel.add(createPaddedPanel(emailLabel, 20));
        mainPanel.add(emailTextField);
        mainPanel.add(createPaddedPanel(passwordLabel, 20));
        mainPanel.add(passwordField);
        mainPanel.add(createPaddedPanel(nameLabel, 20));
        mainPanel.add(nameTextField);
        mainPanel.add(createPaddedPanel(specializationLabel, 20));
        mainPanel.add(specializationComboBox);
        mainPanel.add(createPaddedPanel(workLocationLabel, 20));
        mainPanel.add(workLocationTextField);

        JButton addButton = new JButton();
        if (professional != null) {
            addButton.setText("Save");
        } else {
            addButton.setText("Add");
        }
        JButton cancelButton = new JButton("Cancel");

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
                String name = nameTextField.getText();
                String workLocation = workLocationTextField.getText();
                String email = emailTextField.getText();
                Profession specialization = (Profession) specializationComboBox.getSelectedItem();
                String password = new String(passwordField.getPassword());

                if (isFieldNull()) {
                    JOptionPane.showMessageDialog(AddNewDoctorWindow.this, "Please fill in all the fields");
                } else {
                    if (professional != null) {
                        professional.setName(name);
                        professional.setWorkLocation(workLocation);
                        professional.setEmail(email);
                        professional.setProfession(specialization);
                        professional.setPassword(password);
                        try {
                            professional.editUser();
                            JOptionPane.showMessageDialog(AddNewDoctorWindow.this, "Doctor data edited successfully!");
                            setVisible(false);
                            dispose();
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(AddNewDoctorWindow.this, "Unable to save!");
                        }

                    } else {
                        Professional newProfessional = new Professional(email, password, name, specialization, workLocation);
                        try {
                            newProfessional.addUser();
                            JOptionPane.showMessageDialog(AddNewDoctorWindow.this, "Doctor added successfully!");
                            setVisible(false);
                            dispose();
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(AddNewDoctorWindow.this, "Email already exist");
                        }
                    }
                }


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

    private boolean isFieldNull() {
        String name = nameTextField.getText();
        String workLocation = workLocationTextField.getText();
        String email = emailTextField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || name.isBlank()) {
            return true;
        }
        if (workLocation.isEmpty() || workLocation.isBlank()) {
            return true;
        }
        if (email.isEmpty() || email.isBlank()) {
            return true;
        }
        if (password.isEmpty() || password.isBlank()) {
            return true;
        }
        return false;
        
    }
}
