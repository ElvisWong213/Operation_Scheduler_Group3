package gui.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import dataStructure.exception.DuplicateElementException;
import diary.Diary;
import diary.DiaryEntry;
import user.Patient;
import user.Professional;

public class ShowDiaryWindow {
    private JTable diaryTable;
    private DefaultTableModel tableModel;

    private Diary diary;
    private int professionalID;

    public ShowDiaryWindow(Professional professional) {
        this.professionalID = professional.getProfessionalID();
        this.diary = new Diary();
        this.diary.loadFromDatabase(this.professionalID, 0);

        openWindow();
    }

    private void openWindow() {
        JFrame showDiaryFrame = new JFrame("Diaries");
        showDiaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showDiaryFrame.setSize(800, 500);
        showDiaryFrame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        showDiaryFrame.setJMenuBar(menuBar);
        
        JMenu filMenu = new JMenu("File");
        menuBar.add(filMenu);
        
        JMenuItem exportItem = new JMenuItem("Export To File");
        filMenu.add(exportItem);

        JMenuItem loadItem = new JMenuItem("Load Form File");
        filMenu.add(loadItem);

        diaryTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(diaryTable);

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 10, 0));

        addButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        editButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        deleteButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        undoButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        redoButton.setBorder(new EmptyBorder(10, 10, 10, 10));

        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(undoButton);
        bottomPanel.add(redoButton);

        showDiaryFrame.setLayout(new BorderLayout());
        showDiaryFrame.add(scrollPane, BorderLayout.CENTER);
        showDiaryFrame.add(bottomPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Date");
        tableModel.addColumn("Time");
        tableModel.addColumn("Patient Name");
        tableModel.addColumn("Note");

        for (DiaryEntry diaryEntry : diary.getListOfEntries()) {
            Patient patient = new Patient();
            try {
                patient.getUserById(diaryEntry.getPatientID());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Object[] rowData = {
                    diaryEntry.getDate().toString(),
                    diaryEntry.getTime().toString(),
                    patient.getName(),
                    diaryEntry.getNote()
            };
            tableModel.addRow(rowData);
        }

        diaryTable.setModel(tableModel);

        diaryTable.setEnabled(true);
        diaryTable.setCellSelectionEnabled(false);
        diaryTable.setColumnSelectionAllowed(false);
        diaryTable.setRowSelectionAllowed(true);

        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = diaryTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to export.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON Files", "json");
                fileChooser.addChoosableFileFilter(jsonFilter);
                fileChooser.setFileFilter(jsonFilter);
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        diary.writeFile(fileChooser.getSelectedFile().toString());
                        JOptionPane.showMessageDialog(null, "File saved!");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "An error occurred while writing to the file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON Files", "json");
                fileChooser.addChoosableFileFilter(jsonFilter);
                fileChooser.setFileFilter(jsonFilter);
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        diary.readFile(fileChooser.getSelectedFile().toString());
                        initialiseTable();
                        JOptionPane.showMessageDialog(null, "File loaded!");
                    } catch (JSONException | DuplicateElementException | IOException e1) {
                        JOptionPane.showMessageDialog(null, "An error occurred while reading the file.", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNewDiaryWindow addNewDiaryWindow = new AddNewDiaryWindow(diary, professionalID);
                addNewDiaryWindow.setModal(true);
                addNewDiaryWindow.setVisible(true);

                addNewDiaryWindow.dispose();

                initialiseTable();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = diaryTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DiaryEntry diaryEntry = diary.getDataByIndex(selectedRow);
                AddNewDiaryWindow addNewDiaryWindow = new AddNewDiaryWindow(diary, diaryEntry);
                addNewDiaryWindow.setModal(true);
                addNewDiaryWindow.setVisible(true);

                addNewDiaryWindow.dispose();

                initialiseTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = diaryTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this diary?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    diary.deleteEntry(diary.getDataByIndex(selectedRow), true);
                    initialiseTable();
                    JOptionPane.showMessageDialog(null, "Appointment deleted successfully!");
                }
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    diary.undo();
                    initialiseTable();
                } catch (NoSuchElementException e1) {
                    JOptionPane.showMessageDialog(null, "Unable to undo action.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (DuplicateElementException e1) {
                    JOptionPane.showMessageDialog(null, "No more undo action.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    diary.redo();
                    initialiseTable();
                } catch (DuplicateElementException e1) {
                    JOptionPane.showMessageDialog(null, "Unable to redo action.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NoSuchElementException n) {
                    JOptionPane.showMessageDialog(null, "No more redo action.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        showDiaryFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                diary.saveToDatabase();
            }
        });

        showDiaryFrame.setVisible(true);
    }

    private void initialiseTable() {
        tableModel.setRowCount(0);
        for (DiaryEntry diaryEntry : diary.getListOfEntries()) {
            Patient patient = new Patient();
            try {
                patient.getUserById(diaryEntry.getPatientID());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Object[] rowData = {
                    diaryEntry.getDate().toString(),
                    diaryEntry.getTime().toString(),
                    patient.getName(),
                    diaryEntry.getNote()
            };
            tableModel.addRow(rowData);
        }
    }
}
