import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rate {
    // should be static
        private static HashMap<Integer, List<Double>> scores=new HashMap<>(); // doctorId -> list of ratings
        private Reservation reservation;

        public Rate(Reservation reservation) {
            this.reservation = reservation;
        }

        public void rateDoctor(double score) {
            if (reservation == null) {
                System.out.println("❌ Error: No reservation found for this rating.");
                return;
            }

            if (score < 0 || score > 5) {
                System.out.println(" Invalid score! Please enter a value between 0 and 5.");
                return;
            }

            int doctorId = reservation.getDoctorId();
            scores.putIfAbsent(doctorId, new ArrayList<>());
            scores.get(doctorId).add(score);

            System.out.println(" Added rating " + score + " for Doctor " + reservation.getDoctorId());
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
            System.out.println("\nRatings for Doctor ID: " + doctorId);

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
//            System.out.println("\n Doctor Ratings:");
//            for (Map.Entry<Integer, List<Double>> entry : scores.entrySet()) {
//                int doctorId = entry.getKey();
//                //List<Double> doctorRatings = entry.getValue();
////
////                double sum = 0;
////                for (double r : doctorRatings) sum += r;
////                double avg = sum / doctorRatings.size();
//
//                System.out.println("Doctor ID: " + doctorId +
//                        " | Ratings: " + doctorRatings +
//                        " | Avg: " + String.format("%.2f", avg));
//            }
//
//            System.out.println("\n Overall Avg Rating: " + String.format("%.2f", getAvgRateForDoctor()));
        }
    }

