package appointment;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import type.TreatmentType;
import user.Patient;
import user.Professional;

public class Appointment {
    private Professional professional;
    private ArrayList<AppointmentEntry> appointments;

    // public ArrayList<AppointmentEntry> searchAppointments(ArrayList<Professional> professionals, Date startDate, Date endDate) {

    // }

    public void bookAppointment(Date date, Time startTime, Time endTime, TreatmentType treatmentType, Professional professional, Patient patient) {

    }

    public void undoLastAction() {

    }
    
}
