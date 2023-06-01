package gui.basic;

public class Patient {
    private String firstName;
    private String lastName;
    private int age;
    private double weight;
    private double height;
    private String phone;
    private String address;
    private String notes;
    private String login;
    private String password;

    public Patient()
    {

    }

    public Patient(String firstName, String lastName, int age, double weight, double height, String phone, String address, String notes, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.phone = phone;
        this.address = address;
        this.notes = notes;
        this.login = login;
        this.password = password;
    }

    // Getters and setters for each field
    // ...

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPatientFullInfo() {
        String fullInfo = "";

        String firstName = getFirstName();
        String lastName = getLastName();
        int age = getAge();
        String phone = getPhone();
        String address = getAddress();

        fullInfo = "Patient Information: ";
        fullInfo += "Name: " + firstName + " " + lastName + "; ";
        fullInfo += "Age: " + age + "; ";
        fullInfo += "Phone: " + phone + "; ";
        fullInfo += "Address: " + address + "; ";

        return fullInfo;
    }

    public String getPatientInfo() {
        String fullInfo = "";

        String firstName = getFirstName();
        String lastName = getLastName();
        int age = getAge();
        String phone = getPhone();
        String address = getAddress();


        fullInfo += firstName + " " + lastName + "; ";
        fullInfo += "Age: " + age ;


        return fullInfo;
    }
}
