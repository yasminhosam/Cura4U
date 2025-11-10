import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Patient  extends User {
    //    static HashMap<Integer, List<com.clinicSystem.model.Reservation>> patientReservations = new HashMap<>();
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
        Reservation reservation = Reservation.createReservation(id, doctor.id, dateTime);

//        if (reservation != null) {
//            patientReservations.putIfAbsent(id, new ArrayList<>());
//            patientReservations.get(id).add(reservation);
//        }

        return reservation;
    }


    public void rateDoctor(Reservation reservation, double score) {
        // Check if this reservation belongs to this patient
        if (reservation.getPatientId() != this.id) {
            System.out.println("Error: You can only rate for your own reservations.");
            return;
        }

        // This encapsulates the 'Rate' object creation
        // The main file no longer needs to know about the Rate class
        Rate newRating = new Rate(reservation); //
        newRating.addingRate(score); //
    }

}