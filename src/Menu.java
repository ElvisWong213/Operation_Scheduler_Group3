import java.sql.SQLException;
import java.util.Scanner;

import user.Patient;
import user.Professional;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private Patient patient;
    private Professional professional;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.run();
    }

    public Menu() {
        this.patient = new Patient();
        this.professional = new Professional();
    }

    public void run() {
        if (login()) {
            System.out.println("Login success");
            if (professional.getLoginState()) {
                professionalsMenu();
            } else if (patient.getLoginState()) {
                patientMenu();
            }
        } else {
            System.out.println("Login fail");
        }
    }

    private boolean login() {
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
        
        // if login sucess -> return true
        // if fail -> truen false
        if (professional.performLogin(email, password) || patient.performLogin(email, password)) {
            return true;
        }
        return false;

    }

    // TODO: accept user input
    private void professionalsMenu() {
        System.out.println("");
        System.out.println("---------------------------------------------------");
        System.out.println("User info:");
        System.out.println("Name: " + professional.getName());
        System.out.println("---------------------------------------------------");

        System.out.println("1. View appointment");
        System.out.println("2. Edit appointment");
        System.out.println("3. Remove appointment");
        System.out.println("4. Search patient");
    }

    // TODO: Show one week appointment
    private void viewAppointment() {

    }

    // TODO: edit appointment
    private void editAppointment() {

    }

    // TODO: search patitent
    private void searchPatient() {

    }

    private void patientMenu() {
        System.out.println("");
        System.out.println("---------------------------------------------------");
        System.out.println("User info:");
        System.out.println("Name: " + patient.getName());
        System.out.println("---------------------------------------------------");
        System.out.println("1. View appointment");

    }
}
