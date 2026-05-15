import java.io.Serializable;

public class Item implements Serializable {

    private int damage;
    private String name;

    /**
     * Tracks whether this item instance has already been placed in a room,
     * ensuring only one copy of each item exists in the dungeon at a time.
     */
    private boolean existsInRoom;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    public Item(String name, int damage) {
        this.name = name;
        this.damage = damage;
        this.existsInRoom = false;
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExistsInRoom() {
        return existsInRoom;
    }

    public void setExistsInRoom(boolean existsInRoom) {
        this.existsInRoom = existsInRoom;
    }

    @Override
    public String toString() {
        return name + " (damage: " + damage + ")";
    }
}
