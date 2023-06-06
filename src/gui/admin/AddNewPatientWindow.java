package gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import type.Gender;
import dataStructure.MyLinkedList;
import user.Patient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

public class AddNewPatientWindow extends JDialog {
    private JLabel nameLabel;
    private JLabel genderLabel;
    private JLabel dateOfBirthLabel;
    private JLabel phoneLabel;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;

    private JTextField nameTextField;
    private JComboBox<Gender> genderComboBox;
    private JTextField phoneTextField;
    private JTextField addressTextField;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;

    private JButton addButton;
    private JButton cancelButton;

    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;

    private Patient patient;

    public AddNewPatientWindow() {
        setTitle("Add New Patient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        createComponents();
        createLayout();
    }

    public AddNewPatientWindow(Patient patient) {
        this.patient = patient;
        setTitle("Edit Patient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        createComponents();
        createLayout();
    }

    private void createComponents() {
        LocalDate now = LocalDate.now();
        this.selectedDay = now.getDayOfMonth();
        this.selectedMonth = now.getMonthValue();
        this.selectedYear = now.getYear();

        nameLabel = new JLabel("Name:");
        genderLabel = new JLabel("Gender:");
        dateOfBirthLabel = new JLabel("Date Of Birth:");
        phoneLabel = new JLabel("Phone:");
        addressLabel = new JLabel("Address:");
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");

        nameTextField = new JTextField();
        genderComboBox = new JComboBox<>(Gender.values());
        phoneTextField = new JTextField();
        addressTextField = new JTextField();
        emailTextField = new JTextField();
        passwordTextField = new JPasswordField();

        if (patient != null) {
            nameTextField.setText(patient.getName());
            genderComboBox.setSelectedItem(patient.getGender());
            phoneTextField.setText(patient.getPhoneNumber());
            addressTextField.setText(patient.getAddress());
            emailTextField.setText(patient.getEmail());
            passwordTextField.setText(patient.getPassword());
        }

        // Set preferred height for text fields based on font size
        Font textFieldFont = nameTextField.getFont();
        int preferredHeight = textFieldFont.getSize() + 8; // 8 is an arbitrary value for padding
        Dimension textFieldDimension = new Dimension(nameTextField.getPreferredSize().width, preferredHeight);
        nameTextField.setPreferredSize(textFieldDimension);
        genderComboBox.setPreferredSize(textFieldDimension);
        phoneTextField.setPreferredSize(textFieldDimension);
        addressTextField.setPreferredSize(textFieldDimension);
        emailTextField.setPreferredSize(textFieldDimension);
        passwordTextField.setPreferredSize(textFieldDimension);

        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
    }

    private void createLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 10, 10));

        mainPanel.add(createPaddedPanel(emailLabel, 10));
        mainPanel.add(emailTextField);
        mainPanel.add(createPaddedPanel(passwordLabel, 10));
        mainPanel.add(passwordTextField);
        mainPanel.add(createPaddedPanel(nameLabel, 10));
        mainPanel.add(nameTextField);
        mainPanel.add(createPaddedPanel(genderLabel, 10));
        mainPanel.add(genderComboBox);
        mainPanel.add(createPaddedPanel(dateOfBirthLabel, 10));
        mainPanel.add(datePicker());
        mainPanel.add(createPaddedPanel(phoneLabel, 10));
        mainPanel.add(phoneTextField);
        mainPanel.add(createPaddedPanel(addressLabel, 10));
        mainPanel.add(addressTextField);

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
                Gender gender = (Gender) genderComboBox.getSelectedItem();
                LocalDate dateOfBirth = LocalDate.of(selectedYear, selectedMonth, selectedDay);
                String phone = phoneTextField.getText();
                String address = addressTextField.getText();
                String email = emailTextField.getText();
                String password = new String(passwordTextField.getPassword());

                if (isFieldsNull()) {
                    JOptionPane.showMessageDialog(mainPanel, "Please fill in all the fields");
                } else {
                    if (patient != null) {
                        patient.setName(name);
                        patient.setGender(gender);
                        patient.setDateOfBirth(Date.valueOf(dateOfBirth));
                        patient.setPhoneNumber(phone);
                        patient.setAddress(address);
                        patient.setEmail(email);
                        patient.setPassword(password);

                        try {
                            Integer.valueOf(phone);
                            patient.editUser();
                            setVisible(false);
                            dispose();
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(AddNewPatientWindow.this, "Unable to save!");
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(mainPanel, "Invalid phone number");
                        }
                    }
                    try {
                        Integer.valueOf(phone);
                        Patient newPatient = new Patient(email, password, name, gender, Date.valueOf(dateOfBirth), phone, address);
                        newPatient.addUser();
                        setVisible(false);
                        JOptionPane.showMessageDialog(AddNewPatientWindow.this, "Patient added successfully!");
                        dispose();
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(mainPanel, "Invalid phone number");
                    } catch (SQLException sql) {
                        JOptionPane.showMessageDialog(mainPanel, "Email already exist");
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

    private JPanel datePicker() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        LocalDate now = LocalDate.now();

        JComboBox<Integer> dayComboBox = new JComboBox<>(getDayList(now.getMonthValue(), now.getYear()));
        JComboBox<Integer> monthComboBox = new JComboBox<>(getMonthList());
        JComboBox<Integer> yearComboBox = new JComboBox<>(getYearList(now.getYear()));

        if (patient != null) {
            LocalDate date = patient.getDateOfBirth().toLocalDate();
            dayComboBox.setSelectedItem(date.getDayOfMonth());
            monthComboBox.setSelectedItem(date.getMonthValue());
            yearComboBox.setSelectedItem(date.getYear());
        }

        dayComboBox.setSelectedItem(selectedDay);
        monthComboBox.setSelectedItem(selectedMonth);
        yearComboBox.setSelectedItem(selectedYear);

        gbc.gridx = 0;
        panel.add(dayComboBox, gbc);

        gbc.gridx = 1;
        panel.add(monthComboBox, gbc);

        gbc.gridx = 2;
        panel.add(yearComboBox, gbc);

        dayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dayComboBox.getSelectedItem() != null) {
                    selectedDay = (int) dayComboBox.getSelectedItem();
                }
                selectedMonth = (int) monthComboBox.getSelectedItem();
                selectedYear = (int) yearComboBox.getSelectedItem();
            }
        });
        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDay = (int) dayComboBox.getSelectedItem();
                int bufferDay = selectedDay;
                selectedMonth = (int) monthComboBox.getSelectedItem();
                selectedYear = (int) yearComboBox.getSelectedItem();                
                dayComboBox.removeAllItems();
                Integer[] dayList = getDayList((int) monthComboBox.getSelectedItem(), (int) yearComboBox.getSelectedItem());
                for (Integer day : dayList) {
                    dayComboBox.addItem(day);
                }
                if (bufferDay > dayList.length) {
                    bufferDay = dayList.length;
                }
                dayComboBox.setSelectedItem(bufferDay);
            }
        });
        yearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDay = (int) dayComboBox.getSelectedItem();
                int bufferDay = selectedDay;
                selectedMonth = (int) monthComboBox.getSelectedItem();
                selectedYear = (int) yearComboBox.getSelectedItem();                
                dayComboBox.removeAllItems();
                Integer[] dayList = getDayList((int) monthComboBox.getSelectedItem(), (int) yearComboBox.getSelectedItem());
                for (Integer day : dayList) {
                    dayComboBox.addItem(day);
                }
                if (bufferDay > dayList.length) {
                    bufferDay = dayList.length;
                }
                dayComboBox.setSelectedItem(bufferDay);
            }
        });

        return panel;
    }

    private Integer[] getDayList(int month, int year) {
        LocalDate now = LocalDate.now();
        int[] numberOfDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int maxDay = numberOfDay[month - 1];
        if (year % 4 == 0 && month == 2) {
            maxDay += 1;
        }
        MyLinkedList<Integer> dayList = new MyLinkedList<>();
        for (int i = 0; i < maxDay; i++) {
            if (selectedYear == now.getYear() && selectedMonth == now.getMonthValue() && i == now.getDayOfMonth()) {
                break;
            }
            dayList.add(i + 1);
        }
        return dayList.toArray(new Integer[0]);
    }

    private Integer[] getMonthList() {
        LocalDate now = LocalDate.now();
        Integer[] month = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        if (selectedYear == now.getYear()) {
            month = Arrays.copyOf(month, now.getMonthValue());
        }
        return month;
    }
    
    private Integer[] getYearList(int year) {
        int size = 100;
        Integer[] yearList = new Integer[size];
        for (int i = 0; i < size; i++) {
            yearList[i] = year - i;
        }
        return yearList;
    }

    private boolean isFieldsNull() {
        if (nameTextField.getText().isBlank() || nameTextField.getText().isEmpty()) {
            return true;
        }
        if (phoneTextField.getText().isBlank() || phoneTextField.getText().isEmpty()) {
            return true;
        }
        if (addressTextField.getText().isBlank() || addressTextField.getText().isEmpty()) {
            return true;
        }
        if (emailTextField.getText().isBlank() || emailLabel.getText().isEmpty()) {
            return true;
        }
        if (passwordTextField.getPassword().length == 0) {
            return true;
        }
        return false;
    }
}
