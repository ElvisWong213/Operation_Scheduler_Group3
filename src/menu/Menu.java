package menu;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import appointment.Appointment;
import appointment.AppointmentEntry;
import type.TreatmentType;
import user.Patient;
import user.Professional;
import user.UserManager;
import user.User;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private Patient patient;
    private Professional professional;
    private Appointment appointment;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.run();
    }

    public Menu() {
        this.patient = new Patient();
        this.professional = new Professional();
        this.appointment = new Appointment();
    }

    public void run() {
        while (true) {
            if (!login()) {
                break;
            }
            if (professional.getLoginState()) {
                professionalsMenu();
            } else if (patient.getLoginState()) {
                patientMenu();
            }
        }
    }

    private void clear() {
        professional.clear();
        patient.clear();
    }

    private boolean login() {
        while (true) {
            System.out.println("Enter your email: (Enter Q to exit)");
            String email = scanner.nextLine();
            if (email.equals("q") || email.equals("Q")) {
                return false;
            }
            System.out.println("Enter Password: ");
            String password = scanner.nextLine();
        
            if (professional.performLogin(email, password) || patient.performLogin(email, password)) {
                System.out.println("Login success");
                return true;
            } else {
                System.out.println("Incorrect email or password");
            }
        }

    }

    // TODO: accept user input
    private void professionalsMenu() {
        System.out.println("");
        System.out.println("---------------------------------------------------");
        System.out.println("Professional");
        System.out.println("Name: " + professional.getName());
        System.out.println("---------------------------------------------------");
        while (true) {
            System.out.println("");
            System.out.println("1. View appointment");
            System.out.println("2. Book appointment");
            System.out.println("3. Edit appointment");
            System.out.println("4. Cancel appointment");
            System.out.println("5. Search patient");
            System.out.println("Q: logout");
    
            String choose = scanner.nextLine();
            switch (choose) {
                case "1":
                while (true) {
                    String myChoose = viewAppointment();
                    if (myChoose.equals("q") || myChoose.equals("Q")) {
                        break;
                    }
                }
                break;
                case "2":
                bookAppointment();
                break;
                case "3":
                editAppointment();
                break;
                case "4":
                cancelAppointment();
                break;
                case "Q":
                case "q":
                clear();
                return;
                default:
                break;
            }

        }
    }

    // Show one appointments in one week
    private String viewAppointment() {
        LocalDate startDate = LocalDate.now();
        while (true) {
            System.out.println("");
            System.out.println("From :" + startDate + " to " + startDate.plusDays(7));
            appointment.searchAppointmentsInWeek(startDate, professional.getProfessionalID(), patient.getPatientID());
            appointment.printAllAppointments();

            System.out.println("");
            System.out.println("N: Next week");
            System.out.println("P: Previous week");
            System.out.println("Q: Exit");
            String choose = scanner.nextLine();
            switch (choose) {
                case "N":
                case "n":
                startDate = startDate.plusDays(7);
                break;
                case "P":
                case "p":
                startDate = startDate.plusDays(-7);
                break;
                case "Q":
                case "q":
                return choose;
                default:
                return choose;
            }
        }
    }

    private void bookAppointment() {
        ArrayList<Professional> professionals = UserManager.getAllProfessionals();
        ArrayList<Patient> patients = UserManager.getAllPatients();

        while (true) {
            // * Get treatment type
            TreatmentType treatmentType = chooseTreatmentType();
            int patientID = 0;
            int professionalID = 0;
            if (professional.getLoginState()) {
                // * Get patient and professional id
                professionalID = professional.getProfessionalID();
                patientID = chooseUser(patients);
                try {
                    patient.getUserById(patientID);
                } catch (SQLException e) {
                    System.out.println("No such user");
                    continue;
                }
            } else if (patient.getLoginState()) {
                // * Get patient and professional id
                patientID = patient.getPatientID();
                professionalID = chooseUser(professionals);
                try {
                    professional.getUserById(professionalID);
                } catch (SQLException e) {
                    System.out.println("No such user");
                    continue;
                }
            }
            LocalDate date = null;
            LocalTime startTime = null;
            // * Get date
            date = chooseDate(patient, professional);
            // * Get time
            startTime = chooseTime(date, patient, professional);
            AppointmentEntry ae = new AppointmentEntry(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(startTime.plusHours(1)), treatmentType, professionalID, patientID);
            if (confirmBooking(ae)) {
                appointment.bookAppointment(ae);
                System.out.println("Booking successful");
            } else {
                System.out.println("Booking canceled");
            }
            break;
        }
    }

    private TreatmentType chooseTreatmentType() {
        TreatmentType[] allType = TreatmentType.values();
        while (true) {
            System.out.println("Please choose the treatment type");
            for (int i = 0; i < allType.length; i++) {
                System.out.println(i + 1 + ". " + allType[i]);
            }
            String choose = scanner.nextLine();
            int index = -1;
            try {
                index = Integer.parseInt(choose);
                index--;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid type");
                continue;
            }
            if (index < 0 || index > allType.length) {
                System.out.println("Please enter a valid type");
                continue;
            }
            return allType[index];

        }
    }

    private int chooseUser(ArrayList<? extends User> users) {
        String userType = "";
        if (users.get(0) instanceof Professional) {
            userType = "professional";
        } else {
            userType = "patient";
        }
        while (true) {
            System.out.println("");
            System.out.println("Please choose a " + userType);
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + 1 + ". " + users.get(i).getName());
            }
            int choose = 0;
            try {
                choose = Integer.parseInt(scanner.nextLine());
                choose--;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }
            if (choose < 0 || choose > users.size() - 1) {
                System.out.println("Please enter a valid number");
                continue;
            }
            if (users.get(0) instanceof Professional) {
                Professional user = (Professional) users.get(choose - 1);
                return user.getProfessionalID();
            } else {
                Patient user = (Patient) users.get(choose);
                return user.getPatientID();
            }
        }
    }

    private LocalDate chooseDate(User user1, User user2) {
        while (true) {
            System.out.println("Enter the date: (yyyy-MM-dd)");
            LocalDate date = null;
            try {
                date = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date");
                continue;
            }
            if (date.isBefore(LocalDate.now())) {
                System.out.println("Cannot select past dates");
                continue;
            }
            if (UserManager.availableTime(date, user1).isEmpty() || UserManager.availableTime(date, user2).isEmpty()) {
                System.out.println("Full booking");
                continue;
            }
            return date;
        }
    }

    private LocalTime chooseTime(LocalDate date, User user1, User user2) {
        Set<LocalTime> user1AvailableTime = UserManager.availableTime(date, user1);
        Set<LocalTime> user2AvailableTime = UserManager.availableTime(date, user2);
        Set<LocalTime> availableTime = new TreeSet<>(user1AvailableTime);
        availableTime.retainAll(user2AvailableTime);
        ArrayList<LocalTime> availableTimeArray = new ArrayList<>(availableTime);
        
        while (true) {
            for (int i = 0; i < availableTime.size(); i++) {
                System.out.println(i + 1 + ". " + availableTimeArray.get(i) + " - " + availableTimeArray.get(i).plusHours(1));
            }
            int choose = 0;
            try {
                choose = Integer.parseInt(scanner.nextLine());
                choose--;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }
            if (choose < 0 || choose > availableTime.size() - 1) {
                continue;
            }
            return availableTimeArray.get(choose);
        }
    }

    private boolean confirmBooking(AppointmentEntry ae) {
        ae.print();
        while (true) {
            System.out.println("Confirm booking? Y / N");
            String choose = scanner.nextLine();
            switch (choose) {
                case "Y":
                case "y":
                return true;
                case "N":
                case "n":
                return false;
                default:
                System.out.println("Please enter Y or N");
                break;
            }
        }
    }


    // TODO: edit appointment
    private void editAppointment() {
        while (true) {
            String choose = viewAppointment();
            if (choose.equals("q") || choose.equals("Q")) {
                break;
            }
            int chooseAppointment = 0;
            try {
                chooseAppointment = Integer.parseInt(choose);
                chooseAppointment--;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                continue;
            }
            if (chooseAppointment < 0 || chooseAppointment > appointment.getAppointments().size() - 1) {
                System.out.println("Please enter a valid number");
                continue;
            }
            AppointmentEntry ae = appointment.getAppointments().get(chooseAppointment);
            editElementInAppointment(ae);
        }
    }

    private void editElementInAppointment(AppointmentEntry ae) {
        ArrayList<Professional> professionals = UserManager.getAllProfessionals();

        while (true) {
            ae.print();
            System.out.println("");
            System.out.println("1. Staff");
            System.out.println("2. Date and Time");
            System.out.println("3. Treatment type");
            System.out.println("S. Save");
            System.out.println("Q. Exit");
            String choose = scanner.nextLine();
    
            switch (choose) {
                case "1":
                int professionalID = chooseUser(professionals);
                ae.setProfessionalID(professionalID);
                case "2":
                LocalDate date = chooseDate(professional, patient);
                ae.setDate(Date.valueOf(date));
                LocalTime startTime = chooseTime(date, professional, patient);
                ae.setStartTime(Time.valueOf(startTime));
                ae.setEndTime(Time.valueOf(startTime.plusHours(1)));
                break;
                case "3":
                TreatmentType treatmentType = chooseTreatmentType();
                ae.setTreatmentType(treatmentType);
                break;
                case "S":
                case "s":
                appointment.editAppointment(ae);
                System.out.println("change saved");
                return;
                case "Q":
                case "q":
                return;
                default:
                System.out.println("Please enter a valid value");
                break;
            }
        }
    }

    // TODO: cencel appointment
    private void cancelAppointment() {
        while (true) {
            String choose = viewAppointment();
            if (choose.equals("q") || choose.equals("Q")) {
                break;
            }
            int chooseAppointment = 0;
            try {
                chooseAppointment = Integer.parseInt(choose);
                chooseAppointment--;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                continue;
            }
            if (chooseAppointment < 0 || chooseAppointment > appointment.getAppointments().size() - 1) {
                System.out.println("Please enter a valid number");
                continue;
            }
            AppointmentEntry ae = appointment.getAppointments().get(chooseAppointment);
            System.out.println("");
            System.out.println("Do you want to remove this appointment? Y / N");
            ae.print();
            String confirm = scanner.nextLine();
            switch (confirm) {
                case "Y":
                case "y":
                appointment.removeAppointment(ae);
                System.out.println("Appointment removed");
                break;
                case "N":
                case "n":
                System.out.println("Operation cancel");
                break;
            }
        }
    }

    // TODO: search patitent
    private void searchPatient() {
        
    }

    private void patientMenu() {
        System.out.println("");
        System.out.println("---------------------------------------------------");
        System.out.println("Patient");
        System.out.println("User info:");
        System.out.println("Name: " + patient.getName());
        System.out.println("---------------------------------------------------");
        System.out.println("1. View appointment");

    }
}
