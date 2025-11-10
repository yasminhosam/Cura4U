
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private static ArrayList<User> users = new ArrayList<>();
    protected String name, email, password, role;
    protected int id;
    protected int phone;
    private static int cntUsers = 0;
    private static User currentUser=null;

   // public static Map<Integer, List<Reservation>> reservations = new HashMap<>();
    Reservation reservation;
    public User(String name, String email, int phone, String password, String role) {
        cntUsers++;
        this.id = cntUsers;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }


    public static void signUp(User user) {
        boolean exists = users.stream()
                .anyMatch(u -> u.email.equals(user.email) || u.name.equals(user.name));
        if(exists){
            System.out.println("User with this name or email already exists!");
            return;
        }

        users.add(user);
        System.out.println("Signed up successfully: "+user.name);

        if (user.getRole().equals("doctor")) {
            Doctor.addDoctor((Doctor) user);
        }
    }

    public static void login(String name, String password) {
        User user = users.stream()
                .filter(u ->u.name.equals(name) && u.password.equals(password))
                .findFirst()
                .orElse(null);

        if(user !=null){
            currentUser=user;
            System.out.println("Login successful welcome, "+user.name);
        }
        else
            System.out.println("Invalid username or password");
    }

    public static void logout(){
//        com.clinicSystem.model.User user=users.stream()
//                .filter(u->u.id == this.id)
//                .findFirst()
//                .orElse(null);
        if(currentUser !=null){
            System.out.println(currentUser.getName() + " logged out successfully.");
            currentUser=null;
        }
        else
            System.out.println("User not found!");
    }
    public static User getUserById(int id){
        User user=users.stream()
                .filter(u->u.id==id)
                .findFirst()
                .orElse(null);
        return user;
    }
    public void viewReservations() {

        List<Reservation> userReservations=Reservation.getAllReservations().stream()
                .filter(r-> r.getDoctorId()==this.id || r.getPatientId()==this.id)
                .toList();
//        for (Map.Entry<Integer, List<Reservation>> entry : reservations.entrySet()) {
//            List<Reservation> userReservations = entry.getValue();
            if (userReservations.isEmpty())
                System.out.println("No reservations found for you");
            else {
                System.out.println("Reservation for " + this.name + ":");
                userReservations.forEach(u->u.dispalyReservation());
            }
      //  }
    }

    public void cancelReservation(Reservation reservation) {
        // First, check if this reservation actually belongs to this patient
        if (reservation.getPatientId() != this.id) {
            System.out.println("Error: You can only cancel your own reservations.");
            return;
        }

        // If it's theirs, call the existing cancel logic
        reservation.cancelReservation(); //
        System.out.println("Reservation " + reservation.getId() + " has been successfully cancelled by " + this.name);
    }
}