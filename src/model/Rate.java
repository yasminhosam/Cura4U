package model;

import model.Doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rate {
    // should be static
    private static HashMap<Integer, List<Double>> scores=new HashMap<>(); // doctorId -> list of ratings
    private Reservation reservation;

    public Rate(Reservation reservation) {
        this.reservation = reservation;
    }

    public void addingRate(double score) {
        if (reservation == null) {
            System.out.println("❌ Error: No reservation found for this rating.");
            return;
        }

        if (score < 0 || score > 5) {
            System.out.println(" Invalid score! Please enter a value between 0 and 5.");
            return;
        }

        int doctorId = reservation.getDoctorId();
        // This is the new logic:
        Double oldScore = reservation.getCurrentRating(); // Get the old score (null if new)

        scores.putIfAbsent(doctorId, new ArrayList<>());
        List<Double> doctorRatings = scores.get(doctorId);

        if (oldScore != null) {

            // Remove the old score from the list
            doctorRatings.remove(oldScore);
            // Add the new score to the list
            doctorRatings.add(score);
            // Update the reservation's saved rating
            reservation.setCurrentRating(score);
            System.out.println(" Rating for reservation " + reservation.getId() + " UPDATED from " + oldScore + " to " + score);

        } else {
            // --- THIS IS A NEW RATING ---
            // 1. Add the new score
            doctorRatings.add(score);
            // 2. Save the rating to the reservation
            reservation.setCurrentRating(score);
            System.out.println(" Added rating " + score + " for model.Doctor " + reservation.getDoctorId());
        }
    }

//        public double getAvgRate() {
//            double total = 0;
//            int count = 0;
//
//            for (List<Double> rateList : scores.values()) {
//                for (double r : rateList) {
//                    total += r;
//                    count++;
//                }
//            }
//
//            return count == 0 ? 0.0 : total / count;
//        }


    public static double getAvgRateForDoctor(int doctorId) {
        //  Checks if this doctor has any ratings at all
        if (!scores.containsKey(doctorId)) {
            return 0.0;
        }
        //  Gets the list of ratings for ONLY this one doctor
        List<Double> doctorRatings = scores.get(doctorId);
        if (doctorRatings.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        //  Loops through and sums ONLY that doctor's ratings
        for (double r : doctorRatings) sum += r;

        //  Divides by that doctor's personal rating count
        return sum / doctorRatings.size();
    }


    public void viewRates() {
        // Get the specific doctor ID from the instance's reservation
        int doctorId = this.reservation.getDoctorId();
        Doctor doctor= Doctor.getDoctorById(doctorId);
        System.out.println("\nRatings for model.Doctor ID: " + doctor.getName());

        // Check if this specific doctor has any ratings
        if (!scores.containsKey(doctorId) || scores.get(doctorId).isEmpty()) {
            System.out.println(" No ratings yet for this doctor.");
            return;
        }

        // Get this doctor's specific ratings and average
        List<Double> doctorRatings = scores.get(doctorId);
        double avg = getAvgRateForDoctor(doctorId); // Use the static helper

        //Print only this doctor's details
        System.out.println("Ratings: " + doctorRatings);
        System.out.println("Average: " + String.format("%.2f", avg));





//            if (scores.isEmpty()) {
//                System.out.println(" No ratings yet.");
//                return;
//            }
//
//            System.out.println("\n com.clinicSystem.model.model.Doctor Ratings:");
//            for (Map.Entry<Integer, List<Double>> entry : scores.entrySet()) {
//                int doctorId = entry.getKey();
//                //List<Double> doctorRatings = entry.getValue();
////
////                double sum = 0;
////                for (double r : doctorRatings) sum += r;
////                double avg = sum / doctorRatings.size();
//
//                System.out.println("com.clinicSystem.model.model.Doctor ID: " + doctorId +
//                        " | Ratings: " + doctorRatings +
//                        " | Avg: " + String.format("%.2f", avg));
//            }
//
//            System.out.println("\n Overall Avg Rating: " + String.format("%.2f", getAvgRateForDoctor()));
    }
}