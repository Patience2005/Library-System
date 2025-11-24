package emss;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all EquipmentItem objects in memory.
 * Lecture 6: Using collections (ArrayList) and managing object relationships.
 */
public class InventoryManager {

    private List<EquipmentItem> items = new ArrayList<>();

    public void addItem(EquipmentItem item) {
        items.add(item);
    }

    public List<EquipmentItem> getAvailableItems() {
        List<EquipmentItem> available = new ArrayList<>();
        for (EquipmentItem item : items) {
            if (item.isAvailable()) {
                available.add(item);
            }
        }
        return available;
    }

    public EquipmentItem findItemById(String itemID) {
        for (EquipmentItem item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return item;
            }
        }
        return null;
    }

    public void markItemAsRented(String itemID) {
        EquipmentItem item = findItemById(itemID);
        if (item != null) {
            item.setAvailable(false);
        }
    }

    public void printAvailableItems() {
        System.out.println("=== Available Equipment ===");
        for (EquipmentItem item : items) {
            if (item.isAvailable()) {
                System.out.println(item);
            }
        }
    }
}
