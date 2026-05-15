import java.io.Serializable;
import java.util.Stack;

public class Player implements Serializable {

    // Constants
    public static final int MAX_SANITY = 100;
    public static final int STARTING_HEALTH = 100;
    public static final int STARTING_SANITY = 0;

    // Fields
    private int health;
    private int sanity;          // 0 = sane, 100 = fully insane
    private boolean isDead;
    private boolean incantationSpoken;
    private int currentRoomIndex;

    private Item[] inventory;
    private Item[] allItems;
    private Room[] roomList;
    private Monster[] monsterList;

    private final Stack<Integer> prevRooms = new Stack<>();

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    public Player() {
        this.health = STARTING_HEALTH;
        this.sanity = STARTING_SANITY;
        this.isDead = false;
        this.incantationSpoken = false;
        this.currentRoomIndex = 0;
    }

    // -------------------------------------------------------------------------
    // Health
    // -------------------------------------------------------------------------
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Reduces health by {@code damage}. Marks the player as dead if health
     * drops to zero or below.
     *
     * @return true if the player died from this damage
     */
    public boolean takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            isDead = true;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------
    // Sanity
    // -------------------------------------------------------------------------
    public int getSanity() {
        return sanity;
    }

    public void setSanity(int sanity) {
        this.sanity = sanity;
    }

    /**
     * Increases sanity by {@code amount}.
     */
    public void raiseSanity(int amount) {
        this.sanity += amount;
    }

    public boolean isFullyInsane() {
        return sanity >= MAX_SANITY;
    }

    // -------------------------------------------------------------------------
    // State flags
    // -------------------------------------------------------------------------
    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean hasSpokenIncantation() {
        return incantationSpoken;
    }

    public void setIncantationSpoken(boolean spoken) {
        incantationSpoken = spoken;
    }

    // -------------------------------------------------------------------------
    // Room navigation
    // -------------------------------------------------------------------------
    public int getCurrentRoomIndex() {
        return currentRoomIndex;
    }

    public void setCurrentRoomIndex(int index) {
        currentRoomIndex = index;
    }

    public Stack<Integer> getPrevRooms() {
        return prevRooms;
    }

    public Room getCurrentRoom() {
        return roomList[currentRoomIndex];
    }

    // -------------------------------------------------------------------------
    // Inventory
    // -------------------------------------------------------------------------
    public Item[] getInventory() {
        return inventory;
    }

    public void setInventory(Item[] inventory) {
        this.inventory = inventory;
    }

    /**
     * Appends {@code item} to the player's inventory array.
     */
    public void addItem(Item item) {
        Item[] enlarged = new Item[inventory.length + 1];
        System.arraycopy(inventory, 0, enlarged, 0, inventory.length);
        enlarged[enlarged.length - 1] = item;
        inventory = enlarged;
    }

    public Item[] getAllItems() {
        return allItems;
    }

    public void setAllItems(Item[] allItems) {
        this.allItems = allItems;
    }

    // -------------------------------------------------------------------------
    // Rooms & monsters
    // -------------------------------------------------------------------------
    public Room[] getRoomList() {
        return roomList;
    }

    public void setRoomList(Room[] roomList) {
        this.roomList = roomList;
    }

    public Monster[] getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(Monster[] monsterList) {
        this.monsterList = monsterList;
    }
}
