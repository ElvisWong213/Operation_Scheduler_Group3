package appointment;
import java.time.LocalDate;
import java.time.LocalTime;

import type.TreatmentType;

public class AppointmentEntry {
    private int id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private TreatmentType treatmentType;
}
