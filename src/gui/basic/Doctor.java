package gui.basic;

public class Doctor {
    private String firstName;
    private String lastName;
    private String middleName;
    private String specialization;

    public Doctor(String firstName, String lastName, String middleName, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.specialization = specialization;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return lastName + ", " + firstName + " " + middleName + " " + specialization;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
