import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<LocalDateTime> slots = new ArrayList<>();

    public void addSlot(LocalDateTime slot) {
        if (!slots.contains(slot)) {
            slots.add(slot);
            System.out.println(slot + " added to Schedule");
        } else {
            System.out.println(slot + " already in Schedule");
        }
    }

    public void removeSlot(LocalDateTime slot) {
        if (slots.contains(slot)) {
            slots.remove(slot);
            System.out.println(slot + " removed from Schedule");
        }else {
            System.out.println(slot + " not in Schedule");
        }
    }

    public void displaySlots(){
        if (slots.isEmpty()){
            System.out.println("No slots in Schedule");
        }else{
            System.out.println("Available slots:");
            for (LocalDateTime slot : slots){
                System.out.println(" - "+ slot);
            }
        }
    }
    public List<LocalDateTime> getAvailableSlots(){
        return slots;
    }
}
