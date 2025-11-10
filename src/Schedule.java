
import java.time.LocalDateTime;
import java.util.*;

public class Schedule {

    protected  Map<LocalDateTime,Boolean> slots=new TreeMap<>();
    //  private List<LocalDateTime> slots = new ArrayList<>();

    public void addSlot(LocalDateTime slot) {
        if (!slots.containsKey(slot)) {
            slots.put(slot,false);
            System.out.println(slot + " added to Schedule");
        } else {
            System.out.println(slot + " already in Schedule");
        }
    }

    public void removeSlot(LocalDateTime slot) {
        if (slots.containsKey(slot)) {
            slots.remove(slot);
            System.out.println(slot + " removed from Schedule");
        }else {
            System.out.println(slot + " not in Schedule");
        }
    }

    public void cancelSlot(LocalDateTime slot) {
        if (slots.containsKey(slot)) {
            slots.put(slot,false ); // Set status to "true" (Booked)
            System.out.println("Slot " + slot + " has been cancelled.");
        }
    }

    public void bookSlot(LocalDateTime slot) {
        if (slots.containsKey(slot)) {
            slots.put(slot, true); // Set status to "true" (Booked)
            System.out.println("Slot " + slot + " has been booked.");
        }
    }
    public void displaySlots(){
        if (slots.isEmpty()){
            System.out.println("No slots in Schedule");
        }else{
            System.out.println("All slots:");
            for (Map.Entry<LocalDateTime, Boolean> slot : slots.entrySet()){
                System.out.print(" - "+ slot.getKey());
                if(slot.getValue()==false) System.out.println(" Available");
                else System.out.println(" Not Available");
            }
        }
    }
    public List<LocalDateTime> getAvailableSlots(){
        List<LocalDateTime> availableSlots=new ArrayList<>();
        for (Map.Entry<LocalDateTime, Boolean> slot: slots.entrySet()){
            if(slot.getValue()==false)
                availableSlots.add(slot.getKey());
        }

        return availableSlots;
    }
}