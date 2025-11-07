import java.time.LocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Clinic Reservation System Test ---");

        // --- 1. User & Doctor/Patient Setup ---
        System.out.println("\n--- 1. Signing up users ---");
        Doctor drSmith = new Doctor("Dr. Smith", "smith@clinic.com", 12345, "pass_doc1");
        Doctor drJones = new Doctor("Dr. Jones", "jones@clinic.com", 67890, "pass_doc2");
        Patient alice = new Patient("Alice", "alice@mail.com", 11111, "pass_patient1");
        Patient bob = new Patient("Bob", "bob@mail.com", 22222, "pass_patient2");

        User.signUp(drSmith);
        User.signUp(drJones);
        User.signUp(alice);
        User.signUp(bob);

        // Test duplicate sign-up
        User.signUp(new Patient("Alice", "alice@mail.com", 333, "pass"));

        // Add clinic info
        drSmith.addClinicInfo("123 Main St", 150.00, "Cardiology", "Heart specialist");
        drJones.addClinicInfo("456 Oak Ave", 100.00, "General", "General practitioner");

        // --- 2. Login & Logout Test ---
        System.out.println("\n--- 2. Login/Logout Test ---");
        User.login("Alice", "badpass"); // Fail
        User.login("Alice", "pass_patient1"); // Success
        User.logout(); // Success
        User.logout(); // Fail (no one logged in)

        // --- 3. Doctor Schedule Setup ---
        System.out.println("\n--- 3. Doctor Schedule Setup ---");

        // Create some time slots
        LocalDateTime slot1 = LocalDateTime.of(2025, 11, 10, 9, 0);  // Nov 10, 9:00 AM
        LocalDateTime slot2 = LocalDateTime.of(2025, 11, 10, 10, 0); // Nov 10, 10:00 AM
        LocalDateTime slot3 = LocalDateTime.of(2025, 11, 11, 9, 0);  // Nov 11, 9:00 AM

        System.out.println("Dr. Smith's Schedule:");
        drSmith.getSchedule().addSlot(slot1);
        drSmith.getSchedule().addSlot(slot2);
        drSmith.getSchedule().displaySlots();

        System.out.println("\nDr. Jones's Schedule:");
        drJones.getSchedule().addSlot(slot1); // Same time, different doctor (tests non-static)
        drJones.getSchedule().addSlot(slot3);
        drJones.getSchedule().displaySlots();

        // --- 4. Patient Booking Scenario ---
        System.out.println("\n--- 4. Patient Booking Scenario ---");
        System.out.println("Alice views 'General' doctors:");
        alice.viewDoctorsByDepartment("General").forEach(doc -> System.out.println("- " + doc.getName()));

        System.out.println("\nBooking appointments...");
        // Alice books Dr. Smith
        Reservation res1 = alice.bookAppointment(drSmith, slot1);

        // Bob books Dr. Smith
        Reservation res2 = bob.bookAppointment(drSmith, slot2);

        // Alice tries to book the same slot (res1)
        System.out.println("\nAlice tries to double-book Dr. Smith at 9:00 AM...");
        Reservation res3 = alice.bookAppointment(drSmith, slot1); // Should fail

        // Bob books Dr. Jones at the same time Alice booked Dr. Smith
        System.out.println("\nBob books Dr. Jones at 9:00 AM (should succeed)...");
        Reservation res4 = bob.bookAppointment(drJones, slot1); // Should succeed

        // --- 5. Check Schedule & Reservation Status ---
        System.out.println("\n--- 5. Checking Schedules After Booking ---");
        System.out.println("Dr. Smith's Schedule (should show 2 booked slots):");
        drSmith.getSchedule().displaySlots();

        System.out.println("\nDr. Jones's Schedule (should show 1 booked slot):");
        drJones.getSchedule().displaySlots();

        System.out.println("\n--- Checking User Reservations ---");
        System.out.println("Alice's Reservations:");
        alice.viewReservations();

        System.out.println("\nBob's Reservations:");
        bob.viewReservations();

        System.out.println("\nDr. Smith's Reservations:");
        drSmith.viewReservations();

        // --- 6. Test Cancellation ---
        System.out.println("\n--- 6. Testing Cancellation ---");
        System.out.println("Alice cancels her appointment (res1)...");
        if (res1 != null) {
            res1.cancelReservation();
        }

        System.out.println("Dr. Smith's Schedule (slot1 should be 'Available' again):");
        drSmith.getSchedule().displaySlots();

        System.out.println("\nAlice's Reservations (should show 'Cancelled'):");
        // We'd have to update toString() or viewReservations() to show status,
        // but the schedule logic is the main proof.
        alice.viewReservations();

        // --- 7. Test Rating System ---
        System.out.println("\n--- 7. Testing Rating System ---");
        // Bob rates Dr. Smith (res2)
        Rate rate1 = new Rate(res2);
        rate1.rateDoctor(5.0);

        // Bob rates Dr. Jones (res4)
        Rate rate2 = new Rate(res4);
        rate2.rateDoctor(3.0);

        // Alice rates Dr. Smith (res1, even though cancelled)
        Rate rate3 = new Rate(res1);
        rate3.rateDoctor(4.0);

        System.out.println("\n--- Testing Instance viewRates (Alice's rating for Dr. Smith) ---");
        rate3.viewRates();
//
//        System.out.println("\n--- Testing Static viewAllDoctorRates (Admin View) ---");
//        // Add the static viewAllDoctorRates to your Rate.java to run this
//        // Rate.viewAllDoctorRates();
//        // Example of calling the static helper:
//        System.out.println("Dr. Smith's avg: " + Rate.getAvgRateForDoctor(drSmith.getId()));
//        System.out.println("Dr. Jones's avg: " + Rate.getAvgRateForDoctor(drJones.getId()));
//
        System.out.println("\n--- Test Complete ---");
    }
}