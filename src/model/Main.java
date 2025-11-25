package model;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Helper to create some specific times for testing
        LocalDateTime t1 = LocalDateTime.of(2025, 11, 20, 10, 0); // Nov 20, 10:00 AM
        LocalDateTime t2 = LocalDateTime.of(2025, 11, 20, 11, 0); // Nov 20, 11:00 AM
        LocalDateTime t3 = LocalDateTime.of(2025, 11, 20, 12, 0); // Nov 20, 12:00 PM
        LocalDateTime t4_invalid = LocalDateTime.of(2025, 11, 20, 14, 0); // Nov 20, 2:00 PM

        System.out.println("--- 1. SYSTEM SETUP: SIGNING UP USERS ---");

        // Sign up Doctors
        Doctor drHouse = new Doctor("Dr. House", "house@example.com", "12345", "pass123"); //
        Doctor drWilson = new Doctor("Dr. Wilson", "wilson@example.com", "54321", "pass456"); //
        User.signUp(drHouse); //
        User.signUp(drWilson); //

        // Sign up Patients
        Patient alice = new Patient("Alice", "alice@example.com", "98765", "alice_pass"); //
        Patient bob = new Patient("Bob", "bob@example.com", "67890", "bob_pass"); //
        User.signUp(alice); //
        User.signUp(bob); //


        System.out.println("\n\n--- 2. DOCTOR WORKFLOW: SETUP SCHEDULE ---");

        // Dr. House logs in to set up his profile
        User.login("Dr. House", "pass123"); //
        drHouse.addClinicInfo("123 Main St", 150.0, "Diagnostics", "Specializes in rare diseases"); //

        System.out.println("\n[Dr. House] Adding slots...");
        drHouse.getSchedule().addSlot(t1); //
        drHouse.getSchedule().addSlot(t2); //
        drHouse.getSchedule().addSlot(t3); //
        drHouse.getSchedule().displaySlots(); //
        User.logout(); //

        // Dr. Wilson logs in to set up his profile
        User.login("Dr. Wilson", "pass456"); //
        drWilson.addClinicInfo("456 Oak Ave", 100.0, "Oncology", "Cancer specialist"); //

        System.out.println("\n[Dr. Wilson] Adding slots...");
        drWilson.getSchedule().addSlot(t1); //
        drWilson.getSchedule().addSlot(t2); //
        drWilson.getSchedule().displaySlots(); //
        User.logout(); //


        System.out.println("\n\n--- 3. PATIENT WORKFLOW: BOOKING (ALICE) ---");
        User.login("Alice", "alice_pass"); //

        System.out.println("\n[Alice] Searching for 'Diagnostics' doctors...");
        List<Doctor> diagnosticsDocs = alice.viewDoctorsByDepartment("Diagnostics"); //
        diagnosticsDocs.forEach(d -> System.out.println(" - Found: " + d.getName()));

        // Test Case 1: Successful Booking
        System.out.println("\n[Alice] Booking Dr. House at 10:00 AM...");
        Reservation resAlice1 = alice.bookAppointment(drHouse, t1); //

        // Test Case 2: Successful Booking (Different Doctor, Same Time)
        System.out.println("\n[Alice] Booking Dr. Wilson at 10:00 AM...");
        Reservation resAlice2 = alice.bookAppointment(drWilson, t1); //

        System.out.println("\n[Alice] Viewing her reservations:");
        alice.viewReservations(); // Tests the User.viewReservations() method
        User.logout(); //

        System.out.println("\n\n--- 4. PATIENT WORKFLOW: BOOKING (BOB) ---");
        User.login("Bob", "bob_pass"); //

        // Test Case 3: Failed Booking (Slot taken by Alice)
        System.out.println("\n[Bob] Attempting to book Dr. House at 10:00 AM (taken by Alice)...");
        bob.bookAppointment(drHouse, t1); //

        // Test Case 4: Successful Booking
        System.out.println("\n[Bob] Booking Dr. House at 11:00 AM...");
        Reservation resBob1 = bob.bookAppointment(drHouse, t2); //

        System.out.println("\n[Bob] Viewing his reservations:");
        bob.viewReservations(); //
        User.logout(); //


        System.out.println("\n\n--- 5. VERIFICATION: CHECK DOCTOR SCHEDULE ---");
        User.login("Dr. House", "pass123"); //
        System.out.println("\n[Dr. House] Viewing his final schedule:");
        drHouse.getSchedule().displaySlots(); // 10:00 and 11:00 should be "Not Available"

        System.out.println("\n[Dr. House] Viewing his reservations (using fixed User.viewReservations)...");
        drHouse.viewReservations(); // This should now show Alice's and Bob's reservations
        User.logout(); //


        System.out.println("\n\n--- 6. CANCELLATION WORKFLOW (ALICE) ---");
        User.login("Alice", "alice_pass"); //
        System.out.println("\n[Alice] Cancelling appointment with Dr. House at 10:00 AM...");
        if (resAlice1 != null) {
            alice.cancelReservation(resAlice1); // Uses the method from User.java
        }
        User.logout(); //


        System.out.println("\n\n--- 7. VERIFICATION: POST-CANCELLATION ---");

        // Bob can now book the cancelled slot
        User.login("Bob", "bob_pass"); //
        System.out.println("\n[Bob] Attempting to book Dr. House at 10:00 AM (now available)...");
        Reservation resBob2 = bob.bookAppointment(drHouse, t1); //
        if (resBob2 != null) {
            System.out.println("Booking successful!");
        }
        User.logout(); //


        System.out.println("\n\n--- 8. RATING WORKFLOW (TESTING UPDATE LOGIC) ---");

        // Bob rates his reservations for Dr. House
        if (resBob1 != null) { // 11:00 slot
            System.out.println("\n[Bob] Giving first-time rating for reservation " + resBob1.getId());
            bob.rateDoctor(resBob1, 5.0); // Should be a NEW rating
        }
        if (resBob2 != null) { // 10:00 slot
            System.out.println("\n[Bob] Giving first-time rating for reservation " + resBob2.getId());
            bob.rateDoctor(resBob2, 4.0); // Should be a NEW rating
        }

        // Alice rates Dr. Wilson
        if (resAlice2 != null) {
            System.out.println("\n[Alice] Giving first-time rating for reservation " + resAlice2.getId());
            alice.rateDoctor(resAlice2, 4.0); //
        }

        System.out.println("\n--- [TEST] Bob updates his first rating ---");
        if (resBob1 != null) { // 11:00 slot
            System.out.println("[Bob] Updating rating for reservation " + resBob1.getId() + "...");
            bob.rateDoctor(resBob1, 2.0); // Should UPDATE the 5.0 to 2.0
        }


        System.out.println("\n\n--- 9. VERIFICATION: CHECK FINAL RATINGS ---");

        // Dr. House's ratings should be [4.0, 2.0] (the 5.0 was removed and replaced by 2.0)
        // Average = (4.0 + 2.0) / 2 = 3.0
        double houseAvg = Rate.getAvgRateForDoctor(drHouse.getId()); //

        // Dr. Wilson's ratings should be [4.0]
        // Average = 4.0 / 1 = 4.0
        double wilsonAvg = Rate.getAvgRateForDoctor(drWilson.getId()); //

        System.out.println("Dr. House Average (should be 3.0): " + houseAvg);
        System.out.println("Dr. Wilson Average (should be 4.0): " + wilsonAvg);

        // Test the instance-based viewRates()
        System.out.println("\n[Test] Viewing rates from Bob's first reservation object:");
        if (resBob1 != null) {
            Rate testRateView = new Rate(resBob1); //
            testRateView.viewRates(); // Should show ONLY Dr. House's ratings [4.0, 2.0]
        }
    }
}