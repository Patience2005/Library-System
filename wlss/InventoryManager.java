package wlss;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all WaterSportItem objects in memory.
 * Lecture 6: Using collections (ArrayList) and managing object relationships.
 */
public class InventoryManager {

    private List<WaterSportItem> items = new ArrayList<>();

    public void addItem(WaterSportItem item) {
        items.add(item);
    }

    public List<WaterSportItem> getAvailableItems() {
        List<WaterSportItem> available = new ArrayList<>();
        for (WaterSportItem item : items) {
            if (item.isAvailable()) {
                available.add(item);
            }
        }
        return available;
    }

    public WaterSportItem findItemById(String itemID) {
        for (WaterSportItem item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return item;
            }
        }
        return null;
    }

    public void markItemAsRented(String itemID) {
        WaterSportItem item = findItemById(itemID);
        if (item != null) {
            item.setAvailable(false);
        }
    }

    public void printAvailableItems() {
        System.out.println("=== Available Equipment ===");
        for (WaterSportItem item : items) {
            if (item.isAvailable()) {
                System.out.println(item);
            }
        }
    }
}
