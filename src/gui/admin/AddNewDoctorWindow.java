package gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.basic.Doctor;
import gui.basic.Hospital;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewDoctorWindow extends JDialog  {
    private Hospital hospital;

    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel middleNameLabel;
    private JLabel specializationLabel;

    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField middleNameTextField;
    private JComboBox<String> specializationComboBox;
    Doctor newDoctor;

    public AddNewDoctorWindow(Hospital hospital) {
        this.hospital = hospital;
        setTitle("Add New Doctor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        createComponents();
        createLayout();

        newDoctor = null;
    }

    private void createComponents() {
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        middleNameLabel = new JLabel("Middle Name:");
        specializationLabel = new JLabel("Specialization:");

        firstNameTextField = new JTextField();
        lastNameTextField = new JTextField();
        middleNameTextField = new JTextField();

        // Set preferred height for text fields based on font size
        Font textFieldFont = firstNameTextField.getFont();
        int preferredHeight = textFieldFont.getSize() + 8; // 8 is an arbitrary value for padding
        Dimension textFieldDimension = new Dimension(firstNameTextField.getPreferredSize().width, preferredHeight);
        firstNameTextField.setPreferredSize(textFieldDimension);
        lastNameTextField.setPreferredSize(textFieldDimension);
        middleNameTextField.setPreferredSize(textFieldDimension);

        String[] specializations = {"Cardiology", "Dermatology", "Endocrinology", "Gastroenterology", "Neurology",
                "Ophthalmology", "Orthopedics", "Pediatrics", "Psychiatry", "Urology"};
        specializationComboBox = new JComboBox<>(specializations);
    }

    private void createLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10));

        mainPanel.add(createPaddedPanel(firstNameLabel, 20));
        mainPanel.add(firstNameTextField);
        mainPanel.add(createPaddedPanel(lastNameLabel, 20));
        mainPanel.add(lastNameTextField);
        mainPanel.add(createPaddedPanel(middleNameLabel, 20));
        mainPanel.add(middleNameTextField);
        mainPanel.add(createPaddedPanel(specializationLabel, 20));
        mainPanel.add(specializationComboBox);

        JButton addButton = new JButton("Add");
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
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String middleName = middleNameTextField.getText();
                String specialization = (String) specializationComboBox.getSelectedItem();

                newDoctor = new Doctor(firstName, lastName, middleName, specialization);
                hospital.addDoctor(newDoctor);

                JOptionPane.showMessageDialog(AddNewDoctorWindow.this, "Doctor added successfully!");

                firstNameTextField.setText("");
                lastNameTextField.setText("");
                middleNameTextField.setText("");
                specializationComboBox.setSelectedIndex(0);

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


    public Doctor getNewDoctor() {
        return  newDoctor;
    }
}
