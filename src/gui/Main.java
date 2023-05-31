package gui;

import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            Hospital hospital = new Hospital();
            public void run() {
                new AuthenticationWindow(hospital);
            }
        });
    }
}