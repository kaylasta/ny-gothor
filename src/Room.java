import java.io.Serializable;

public class Room implements Serializable {

    private int index;
    private int[] linkedIndices;   // Indices of rooms this room connects to

    private String pathDescription; // Shown when the room appears as a choice
    private String roomDescription; // Shown when the player enters the room

    private boolean itemTaken;
    private boolean hasMonster;

    private Item item;
    private Monster monster;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    public Room(int index) {
        this.index = index;
        this.itemTaken = false;
        this.hasMonster = false;
    }

    // -------------------------------------------------------------------------
    // Index / connections
    // -------------------------------------------------------------------------
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int[] getLinkedIndices() {
        return linkedIndices;
    }

    public void setLinkedIndices(int[] linkedIndices) {
        this.linkedIndices = linkedIndices;
    }

    // -------------------------------------------------------------------------
    // Descriptions
    // -------------------------------------------------------------------------
    public String getPathDescription() {
        return pathDescription;
    }

    public void setPathDescription(String desc) {
        this.pathDescription = desc;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String desc) {
        this.roomDescription = desc;
    }

    // -------------------------------------------------------------------------
    // Item
    // -------------------------------------------------------------------------
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isItemTaken() {
        return itemTaken;
    }

    public void setItemTaken(boolean taken) {
        this.itemTaken = taken;
    }

    // -------------------------------------------------------------------------
    // Monster
    // -------------------------------------------------------------------------
    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public boolean hasMonster() {
        return hasMonster;
    }

    public void setHasMonster(boolean hasMonster) {
        this.hasMonster = hasMonster;
    }
}
