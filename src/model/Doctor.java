package model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private String address;
    private String department;
    private double price;
    private String description;
    private Schedule schedule;

    //static HashMap<Integer, List<com.clinicSystem.model.model.Reservation>> docterReservations = new HashMap<>();
    private static List<Doctor> doctors=new ArrayList<>();

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

    public static List<Doctor> getAllDoctors(){return doctors;}
    public static void addDoctor(Doctor doctor){doctors.add(doctor);}
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    public Doctor(String name, String email, String phone, String password) {
        super(name, email, phone, password, "doctor");
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

    public static Doctor getDoctorById(int id){
        for(Doctor d:doctors){
            if(d.id==id)
                return d;
        }
        return null;
    }
}