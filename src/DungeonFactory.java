import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    // Max attempts before falling back when searching for unique indices
    private static final int MAX_INDEX_ATTEMPTS = 10;

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
     * Assigns each room a random set of outgoing connections. Uses a global
     * used-index set so that no two rooms share the same destination, which
     * prevents unreachable duplicates. Falls back to the start room (index 0)
     * if a unique index cannot be found within the attempt limit.
     */
    private void assignConnections(Room[] rooms) {
        Set<Integer> globalUsed = new HashSet<>();

        for (Room room : rooms) {
            int idx = room.getIndex();

            // Special-case connection counts
            int connections;
            connections = switch (idx) {
                case START_ROOM_INDEX ->
                    2;
                case ALTAR_ROOM_INDEX, END_ROOM_INDEX ->
                    0;
                default ->
                    rnd.nextInt(3) + 1;
            }; // Terminal rooms — no outward paths

            int[] linked = new int[connections];
            Set<Integer> localUsed = new HashSet<>();

            for (int x = 0; x < connections; x++) {
                boolean found = false;
                int attempts = 0;

                while (!found && attempts < MAX_INDEX_ATTEMPTS) {
                    int candidate = rnd.nextInt(rooms.length);

                    if (candidate != idx
                            && !localUsed.contains(candidate)
                            && !globalUsed.contains(candidate)) {
                        linked[x] = candidate;
                        localUsed.add(candidate);
                        globalUsed.add(candidate);
                        found = true;
                    } else {
                        attempts++;
                    }
                }

                if (!found) {
                    // Fall back: loop back to start so the room is never a dead end
                    linked[x] = START_ROOM_INDEX;
                }
            }

            room.setLinkedIndices(linked);
        }
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
