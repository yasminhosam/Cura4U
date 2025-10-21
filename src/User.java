import java.util.ArrayList;
import java.util.Objects;

public class User {
    protected String name, email, password, role;
    protected int id;
    protected int phone;
    private static int cntUsers = 0;

    public User(String name, String email, int phone, String password, String role) {
        cntUsers++;
        this.id = cntUsers;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    private static ArrayList<User> users = new ArrayList<>();

    public void signUp(String name, String email, String password, int phone, String role) {
        boolean exists = users.stream()
                .anyMatch(u -> u.email.equals(email) || u.name.equals(name));
        if(exists){
            System.out.println("User with this name or email already exists!");
            return;
        }

        User user = new User(name, email, phone, password, role);
        users.add(user);
        System.out.println("Signed up successfully: "+name);
    }

    public void login(String name, String password) {
        User user = users.stream()
                .filter(u ->u.name.equals(name) && u.password.equals(password))
                .findFirst()
                .orElse(null);

        if(user !=null)
            System.out.println("Login successful welcome, "+user.name);
        else
            System.out.println("Invalid username or password");
    }
}
