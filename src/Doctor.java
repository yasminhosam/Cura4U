public class Doctor extends User {
    private String address;
    private String department;
    private double price;
    private String description;
    private Schedule schedule;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    public Doctor(String name, String email, int phone, String password, String role) {
        super(name, email, phone, password, role);
        this.schedule = new Schedule();
    }
    public void addClinicInfo(String address, double price, String department, String description){
        this.address = address;
        this.price = price;
        this.department = department;
        this.description = description;
    }
    public void updateSchedule(Schedule schedule){
        this.schedule = schedule;
    }

}
