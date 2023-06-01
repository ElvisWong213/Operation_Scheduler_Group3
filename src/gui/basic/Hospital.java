package gui.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Hospital {
    private List<Doctor> doctors;
    private List<Patient> patients;

    private List<Appointment> appointments;

    boolean isAdmin = false;
    public static final String FILE_PATH = "src/gui/doctors.xml";
    public static final String FILE_PATH_PATIENTS = "src/gui/patients.xml";
    public static final String FILE_PATH_APPOINTMENTS = "src/gui/appointments.xml";


    public Patient ActivePatient = null;

    public Hospital() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        loadDataFromDoctorsFile();
        loadDataFromPatientsFile();
        loadAppointmentsFromFile();
    }

    public void setIsAdmin(boolean b) {
        isAdmin = b;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        saveDataToDoctorsFile();
    }

    public void deleteDoctor(int index) {
        if (index >= 0 && index < doctors.size()) {
            doctors.remove(index);
            saveDataToDoctorsFile();
        }
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> searchDoctorsByLastName(String lastName) {
        List<Doctor> matchingDoctors = new ArrayList<>();

        for (Doctor doctor : doctors) {
            if (doctor.getLastName().equalsIgnoreCase(lastName)) {
                matchingDoctors.add(doctor);
            }
        }

        return matchingDoctors;
    }

    private void saveDataToDoctorsFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("<doctors>\n");
            for (Doctor doctor : doctors) {
                writer.write("  <doctor>\n");
                writer.write("    <firstName>" + doctor.getFirstName() + "</firstName>\n");
                writer.write("    <lastName>" + doctor.getLastName() + "</lastName>\n");
                writer.write("    <middleName>" + doctor.getMiddleName() + "</middleName>\n");
                writer.write("    <specialization>" + doctor.getSpecialization() + "</specialization>\n");
                writer.write("  </doctor>\n");
            }
            writer.write("</doctors>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromDoctorsFile()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            StringBuilder xmlBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                xmlBuilder.append(line);
            }
            String xml = xmlBuilder.toString();

            // Извлечь данные из XML
            int startIndex = xml.indexOf("<doctor>");
            while (startIndex != -1) {
                int endIndex = xml.indexOf("</doctor>", startIndex);
                String doctorXml = xml.substring(startIndex, endIndex + "</doctor>".length());

                String firstName = extractValueFromXml(doctorXml, "firstName");
                String lastName = extractValueFromXml(doctorXml, "lastName");
                String middleName = extractValueFromXml(doctorXml, "middleName");
                String specialization = extractValueFromXml(doctorXml, "specialization");

                Doctor doctor = new Doctor(firstName, lastName, middleName, specialization);
                doctors.add(doctor);

                startIndex = xml.indexOf("<doctor>", endIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractValueFromXml(String xml, String tag) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        int startIndex = xml.indexOf(startTag) + startTag.length();
        int endIndex = xml.indexOf(endTag);
        return xml.substring(startIndex, endIndex);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        savePatientsToFile();
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
        savePatientsToFile();
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    private void savePatientsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PATIENTS))) {
            writer.write("<patients>\n");
            for (Patient patient : patients) {
                writer.write("  <patient>\n");
                writer.write("    <firstName>" + patient.getFirstName() + "</firstName>\n");
                writer.write("    <lastName>" + patient.getLastName() + "</lastName>\n");
                writer.write("    <age>" + patient.getAge() + "</age>\n");
                writer.write("    <weight>" + patient.getWeight() + "</weight>\n");
                writer.write("    <height>" + patient.getHeight() + "</height>\n");
                writer.write("    <phone>" + patient.getPhone() + "</phone>\n");
                writer.write("    <address>" + patient.getAddress() + "</address>\n");
                writer.write("    <notes>" + patient.getNotes() + "</notes>\n");
                writer.write("    <login>" + patient.getLogin() + "</login>\n");
                writer.write("    <password>" + patient.getPassword() + "</password>\n");
                writer.write("  </patient>\n");
            }
            writer.write("</patients>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromPatientsFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PATIENTS))) {
            String line;
            StringBuilder xmlBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                xmlBuilder.append(line);
            }
            String xml = xmlBuilder.toString();

            int startIndex = xml.indexOf("<patient>");
            while (startIndex != -1) {
                int endIndex = xml.indexOf("</patient>", startIndex);
                String patientXml = xml.substring(startIndex, endIndex + "</patient>".length());

                String firstName = extractValueFromXml(patientXml, "firstName");
                String lastName = extractValueFromXml(patientXml, "lastName");
                int age = Integer.parseInt(extractValueFromXml(patientXml, "age"));
                double weight = Double.parseDouble(extractValueFromXml(patientXml, "weight"));
                double height = Double.parseDouble(extractValueFromXml(patientXml, "height"));
                String phone = extractValueFromXml(patientXml, "phone");
                String address = extractValueFromXml(patientXml, "address");
                String notes = extractValueFromXml(patientXml, "notes");
                String login = extractValueFromXml(patientXml, "login");
                String password = extractValueFromXml(patientXml, "password");

                Patient patient = new Patient(firstName, lastName, age, weight, height, phone, address, notes, login, password);
                patients.add(patient);

                startIndex = xml.indexOf("<patient>", endIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean authenticatePatient(String username, String password) {


        if (password.equals("admin") && username.equals("admin")) {
            setIsAdmin(true);
            return true; // Authentication successful
        } else {
            setIsAdmin(false);
        }

        for (Patient patient : patients) {
            if (patient.getLogin().equals(username) && patient.getPassword().equals(password)) {
                ActivePatient = patient;
                return true; // Authentication successful
            }
        }
        return false; // Authentication failed
    }



    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        saveAppointmentsToFile();
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        saveAppointmentsToFile();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public Doctor getDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getFullName().equals(name)) {
                return doctor;
            }
        }
        return null;
    }


    public void saveAppointmentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_APPOINTMENTS))) {
            writer.write("<appointments>\n");
            for (Appointment appointment : appointments) {
                writer.write("  <appointment>\n");

                writer.write("    <date>" + appointment.getDate() + "</date>\n");
                writer.write("    <description>" + appointment.getDescription() + "</description>\n");
                writer.write("    <patient>\n");
                writer.write("      <firstName>" + appointment.getPatient().getFirstName() + "</firstName>\n");
                writer.write("      <lastName>" + appointment.getPatient().getLastName() + "</lastName>\n");
                writer.write("      <age>" + appointment.getPatient().getAge() + "</age>\n");
                writer.write("      <phone>" + appointment.getPatient().getPhone() + "</phone>\n");
                writer.write("    </patient>\n");

                writer.write("    <doctors>\n");
                for (String doctor : appointment.getDoctors()) {
                    writer.write("      <doctor>\n");
                    writer.write("        <name>" + doctor + "</name>\n");
                    writer.write("      </doctor>\n");
                }
                writer.write("    </doctors>\n");
                writer.write("    <notes>" + appointment.getNotes() + "</notes>\n");
                writer.write("  </appointment>\n");
            }
            writer.write("</appointments>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAppointmentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_APPOINTMENTS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<appointment>")) {
                    Appointment appointment = new Appointment();
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("<date>")) {
                            String dateString = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            try {
                                Date date = dateFormat.parse(dateString);
                                appointment.setYear(date.getYear() + 1900);
                                appointment.setMonth(date.getMonth() + 1);
                                appointment.setDay(date.getDate());
                                appointment.setHour(date.getHours());
                                appointment.setMinute(date.getMinutes());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if (line.contains("<description>")) {
                            String description = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
                            appointment.setDescription(description);
                        } else if (line.contains("<patient>")) {
                            Patient patient = new Patient();
                            while ((line = reader.readLine()) != null) {
                                if (line.contains("<firstName>")) {
                                    String firstName = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
                                    patient.setFirstName(firstName);
                                } else if (line.contains("<lastName>")) {
                                    String lastName = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
                                    patient.setLastName(lastName);
                                } else if (line.contains("<age>")) {
                                    int age = Integer.parseInt(line.substring(line.indexOf(">") + 1, line.lastIndexOf("<")));
                                    patient.setAge(age);
                                } else if (line.contains("<phone>")) {
                                    String phone = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
                                    patient.setPhone(phone);
                                } else if (line.contains("</patient>")) {
                                    appointment.setPatient(patient);
                                    break;
                                }
                            }
                        } else if (line.contains("<doctors>")) {
                            List<String> doctors = new ArrayList<>();
                            while ((line = reader.readLine()) != null) {
                                if (line.contains("<name>")) {
                                    String doctorName = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
                                    doctors.add(doctorName);
                                } else if (line.contains("</doctors>")) {
                                    appointment.setDoctors(doctors);
                                    break;
                                }
                            }
                        } else if (line.contains("<notes>")) {
                            String notes = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
                            appointment.setNotes(notes);
                        } else if (line.contains("</appointment>")) {
                            appointments.add(appointment);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Patient getActivePatient() {
        return ActivePatient;
    }
}
