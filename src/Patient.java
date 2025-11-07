import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Patient  extends User{

    public Patient(String name, String email, int phone, String password ) {
        super(name, email, phone, password, "patient");

    }

    public List<Reservation> getReservations() {
        // Filters the GLOBAL list instead of using a redundant local one
        return Reservation.getAllReservations().stream()
                .filter(r -> r.getPatientId() == this.id)
                .collect(Collectors.toList());
    }

    public List<Doctor> viewDoctorsByDepartment(String department) {
        List<Doctor> result = new ArrayList<>();
        for (Doctor d : Doctor.getAllDoctors()) {
            if (d.getDepartment().equalsIgnoreCase(department)) {
                result.add(d);
            }
        }
        return result;
    }

    public Reservation bookAppointment(Doctor doctor, LocalDateTime dateTime) {
       return Reservation.createReservation(id,doctor.id,dateTime);

    }


}