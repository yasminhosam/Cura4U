import java.util.ArrayList;
import java.util.Date;

public class Reservation {

    private static int ctrReservation = 0;
    private int id;
    private int patientId;
    private int doctorId;
    private Date dateTime;
    private String status;

    public static ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    public Reservation(int patientId, int doctorId, Date dateTime) {
        ctrReservation++;
        this.id = ctrReservation;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = "Confirmed";//by default
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

    public Date getDateTime() {
        return dateTime;
    }

    public String getStatus() { return status; }
    //setter
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {this.status = status;}

    public void cancelReservation() {
        this.status = "Cancelled";
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

    public static boolean checkAvailability(int doctorId,Date dateTime) {
        for(Reservation r: reservations){
            if(r.doctorId == doctorId && r.dateTime.equals(dateTime) && r.status.equals(status)){
                return false;
            }
        }
        return true;
    }

    public static Reservation createReservation(int patientId, int doctorId,Date dateTime) {
        if(checkAvailability(doctorId,dateTime)){
            Reservation r = new Reservation(patientId,doctorId,dateTime);
            reservations.add(r);
            System.out.println("Reservation created successfully");
            return r;
        }
        else {
            System.out.println("Reservation creation failed");
            return null;
        }
    }


}