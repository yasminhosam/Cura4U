import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation {

    private static int ctrReservation = 0;
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDateTime dateTime;
    private String status;
    private static final String STATUS_CONFIRMED = "Confirmed";
    private static final String STATUS_CANCELLED = "Cancelled";

    // Encapsulated: Changed from public to private
    private static List<Reservation> reservations = new ArrayList<Reservation>();

    public Reservation(int patientId, int doctorId, LocalDateTime dateTime) {
        ctrReservation++;
        this.id = ctrReservation;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = STATUS_CONFIRMED;//by default
    }
    //getters
    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() { return status; }
    //setter
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {this.status = status;}
    public static List<Reservation> getAllReservations() {
        return reservations;
    }
    public void cancelReservation() {
        this.status = STATUS_CANCELLED;
    }

    //toString()
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", dateTime=" + dateTime +
                '}';
    }

    public static boolean checkAvailability(int doctorId,LocalDateTime dateTime) {
        // 1. Find the doctor
        Doctor doctor = Doctor.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Error: Doctor not found.");
            return false;
        }

        // 2. Check if the slot is in the doctor's schedule
        boolean inSchedule = doctor.getSchedule().getAvailableSlots().contains(dateTime);
        if (!inSchedule) {
            System.out.println("Slot not available in doctor's schedule.");
            return false;
        }
        for(Reservation r: reservations){
            if(r.doctorId == doctorId && r.dateTime.equals(dateTime) && r.status.equals(STATUS_CONFIRMED)){
                return false;
            }
        }
        return true;
    }

    public static Reservation createReservation(int patientId, int doctorId,LocalDateTime dateTime) {
        if(checkAvailability(doctorId,dateTime)){
            Reservation r = new Reservation(patientId,doctorId,dateTime);
            reservations.add(r);

            // CRITICAL: Remove the slot from the doctor's schedule
            Doctor doctor = Doctor.getDoctorById(doctorId);
            if (doctor != null) {
                doctor.getSchedule().bookSlot(dateTime);
            }
            System.out.println("Reservation created successfully");
            return r;
        }
        else {
            System.out.println("Reservation creation failed");
            return null;
        }
    }





}