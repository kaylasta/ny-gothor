import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Responsible for generating the dungeon: creating rooms, assigning connections
 * between them, and placing items and monsters.
 */
public class DungeonFactory {

    // Room count for a standard game
    public static final int ROOM_COUNT = 10;

    // Special room indices
    public static final int START_ROOM_INDEX = 0;
    public static final int ALTAR_ROOM_INDEX = 1;
    public static final int END_ROOM_INDEX = 2;

    // Spawn chances (percentage)
    private static final int MONSTER_SPAWN_CHANCE = 20;
    private static final int ITEM_SPAWN_CHANCE = 50;

    private final Random rnd = new Random();

    // -------------------------------------------------------------------------
    // Public entry point
    // -------------------------------------------------------------------------
    /**
     * Builds and returns a fully initialised room array for {@code player},
     * with connections, descriptions, items, and monsters assigned.
     */
    public Room[] buildDungeon(Player player) {
        Room[] rooms = new Room[ROOM_COUNT];

        for (int i = 0; i < ROOM_COUNT; i++) {
            rooms[i] = new Room(i);
        }

        assignConnections(rooms);

        for (int i = 0; i < ROOM_COUNT; i++) {
            configureRoom(rooms[i], player);
        }

        return rooms;
    }

    // -------------------------------------------------------------------------
    // Connection assignment
    // -------------------------------------------------------------------------

    /**
     * Builds a directed dungeon graph with three guarantees:
     *
     * Room indices 3-(ROOM_COUNT-1) are the regular pool, shuffled each run
     * so the layout varies while the structure remains sound.
     */
    private void assignConnections(Room[] rooms) {
 
        // Initialise every room with an empty link list.
        for (Room room : rooms) {
            room.setLinkedIndices(new int[0]);
        }
 
        // Build a shuffled pool of all regular room indices (3 ... ROOM_COUNT-1).
        List<Integer> pool = new ArrayList<>();
        for (int i = 3; i < ROOM_COUNT; i++) {
            pool.add(i);
        }
        java.util.Collections.shuffle(pool, rnd);
 
        // --- SPINE ---
        // Pick 2-4 rooms from the pool to sit between START and END.
        int spineLength = Math.min(2 + rnd.nextInt(3), pool.size());
 
        List<Integer> spine = new ArrayList<>();
        spine.add(START_ROOM_INDEX);
        for (int i = 0; i < spineLength; i++) {
            spine.add(pool.remove(0));
        }
        spine.add(END_ROOM_INDEX);
 
        // Wire the spine forward: each room links to the next.
        for (int i = 0; i < spine.size() - 1; i++) {
            addLink(rooms[spine.get(i)], spine.get(i + 1));
        }
 
        // --- ALTAR BRANCH ---
        // Attach the altar off a random mid-spine room (spine[1] to spine[n-2]).
        // Mid-spine is always at least 1 room since spineLength >= 2.
        int midCount  = spine.size() - 2;
        int altarHost = spine.get(1 + rnd.nextInt(midCount));
        addLink(rooms[altarHost], ALTAR_ROOM_INDEX);
        // Altar and end rooms are terminal — no outward links; player uses -1.
 
        // --- SIDE BRANCHES ---
        // Remaining pool rooms become dead-end branches off random spine rooms.
        // END is excluded as a host since it is terminal.
        List<Integer> hosts = new ArrayList<>(spine.subList(0, spine.size() - 1));
        for (int branchRoom : pool) {
            addLink(rooms[hosts.get(rnd.nextInt(hosts.size()))], branchRoom);
        }
    }
 
    /**
     * Appends {@code target} to {@code room}'s linked-indices array.
     */
    private void addLink(Room room, int target) {
        int[] existing = room.getLinkedIndices();
        int[] updated  = new int[existing.length + 1];
        System.arraycopy(existing, 0, updated, 0, existing.length);
        updated[existing.length] = target;
        room.setLinkedIndices(updated);
    }

    // -------------------------------------------------------------------------
    // Room configuration
    // -------------------------------------------------------------------------
    /**
     * Sets descriptions, items, and monsters for a single room.
     */
    private void configureRoom(Room room, Player player) {
        room.setItemTaken(false);
        room.setHasMonster(false);

        int idx = room.getIndex();

        // Items and monsters are only placed in non-start rooms
        if (idx != START_ROOM_INDEX) {
            if (rnd.nextInt(101) < ITEM_SPAWN_CHANCE) {
                room.setItem(pickItemForRoom(player));
            }
            if (rnd.nextInt(101) < MONSTER_SPAWN_CHANCE) {
                room.setMonster(pickMonsterForRoom(player));
                room.setHasMonster(true);
            }
        }

        assignDescriptions(room);
    }

    /**
     * Assigns path and room descriptions based on the room's special role.
     */
    private void assignDescriptions(Room room) {
        switch (room.getIndex()) {
            case START_ROOM_INDEX -> {
                room.setRoomDescription("The dying light from where you fell shines down.");
                room.setPathDescription("A gleaming light shines down.");
            }
            case ALTAR_ROOM_INDEX ->
                room.setPathDescription("Whispers echo. Whispers beckon.");
            // Room description is handled by the altar narrative in GameEngine
            case END_ROOM_INDEX ->
                room.setPathDescription("The path seems to trail into infinity with no return.");
            default -> {
                room.setRoomDescription(randomRoomDescription());
                room.setPathDescription(randomPathDescription());
            }
        }
    }

    // -------------------------------------------------------------------------
    // Item / monster selection
    // -------------------------------------------------------------------------
    /**
     * Picks a random item from the player's item pool that has not yet been
     * placed in a room. Returns null if no eligible item is found within the
     * attempt limit.
     */
    private Item pickItemForRoom(Player player) {
        Item[] allItems = player.getAllItems();
        int attempts = 0;

        while (attempts < 5) {
            Item candidate = allItems[rnd.nextInt(allItems.length)];
            if (!candidate.isExistsInRoom()) {
                candidate.setExistsInRoom(true);
                return candidate;
            }
            attempts++;
        }

        return null;
    }

    /**
     * Picks a random monster from the player's monster pool.
     */
    private Monster pickMonsterForRoom(Player player) {
        Monster[] monsters = player.getMonsterList();
        return monsters[rnd.nextInt(monsters.length)];
    }

    // -------------------------------------------------------------------------
    // Description helpers
    // -------------------------------------------------------------------------
    private String randomPathDescription() {
        return StoryText.PATH_DESCRIPTIONS[rnd.nextInt(StoryText.PATH_DESCRIPTIONS.length)];
    }

    private String randomRoomDescription() {
        return StoryText.ROOM_DESCRIPTIONS[rnd.nextInt(StoryText.ROOM_DESCRIPTIONS.length)];
    }
}
