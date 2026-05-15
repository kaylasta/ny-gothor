import java.io.IOException;
import java.nio.file.Path;

/**
 * Drives the main game loop. Owns navigation, room entry logic, the altar
 * sequence, and the final Ny'Gothor encounter. Delegates combat to
 * {@link CombatSystem} and all I/O to {@link UI}.
 */
public class GameEngine {

    private final UI ui;
    private final CombatSystem combat;
    private final SaveManager saves;

    public GameEngine(UI ui) {
        this.ui = ui;
        this.combat = new CombatSystem(ui);
        this.saves = new SaveManager();
    }

    // -------------------------------------------------------------------------
    // Main menu
    // -------------------------------------------------------------------------
    public void mainMenu() throws IOException, ClassNotFoundException {
        ui.type("Welcome to Ny'Gothor", 50);

        while (true) {
            ui.type("1. New game", 50);
            ui.type("2. Load game", 50);
            ui.type("3. Help", 50);
            ui.type("4. Quit", 50);

            int choice = ui.readInt("");

            switch (choice) {
                case 1 ->
                    startNewGame();
                case 2 ->
                    loadAndRun();
                case 3 ->
                    printHelp();
                case 4 -> {
                    return;
                }
                default ->
                    ui.type("Please enter 1, 2, 3, or 4.", 50);
            }
        }
    }

    // -------------------------------------------------------------------------
    // New / load
    // -------------------------------------------------------------------------
    private void startNewGame() throws IOException {
        ui.separator();
        printStorySequence(StoryText.INTRODUCTION);
        gameLoop(initialisePlayer());
    }

    private void loadAndRun() throws IOException, ClassNotFoundException {
        Path[] saveFiles = saves.listSaves();

        if (saveFiles.length == 0) {
            ui.type("No save files found.", 50);
            return;
        }

        ui.type("Available saves:", 50);
        for (int i = 0; i < saveFiles.length; i++) {
            ui.type((i + 1) + ": " + saveFiles[i].getFileName(), 50);
        }

        int choice = ui.readInt("");
        if (choice < 1 || choice > saveFiles.length) {
            ui.type("Invalid choice.", 50);
            return;
        }

        gameLoop(saves.load(saveFiles[choice - 1]));
    }

    // -------------------------------------------------------------------------
    // Player initialisation
    // -------------------------------------------------------------------------
    /**
     * Creates a fresh {@link Player}, populates items, monsters, and the
     * dungeon, then returns it ready for the game loop.
     */
    private Player initialisePlayer() {
        Player player = new Player();

        // Starter weapon
        player.setInventory(new Item[]{new Item("Hatchet", 30)});

        // Full item pool (placed into rooms by DungeonFactory)
        player.setAllItems(new Item[]{
            new Item("Knife", 22),
            new Item("Club", 50),
            new Item("Spear", 36),
            new Item("Sword", 60),
            new Item("Dynamite", 1000)
        });

        // Monster pool (picked from randomly when populating rooms)
        player.setMonsterList(new Monster[]{
            new Monster("Ky-Tagar", 100, 20),
            new Monster("Azakoth", 200, 10),
            new Monster("Agaroth", 20, 40)
        });

        // Build the dungeon (requires items + monsters to already be set)
        DungeonFactory factory = new DungeonFactory();
        player.setRoomList(factory.buildDungeon(player));

        return player;
    }

    // -------------------------------------------------------------------------
    // Game loop
    // -------------------------------------------------------------------------
    private void gameLoop(Player player) throws IOException {

        while (!player.isDead()) {
            Room current = player.getCurrentRoom();

            // Special handling for the two terminal rooms
            if (current.getIndex() == DungeonFactory.ALTAR_ROOM_INDEX) {
                runAltarSequence(player);
                returnToLastRoom(player);
                continue;
            }

            if (current.getIndex() == DungeonFactory.END_ROOM_INDEX) {
                runFinalSequence(player);
                return;
            }

            // Describe the room
            if (current.getRoomDescription() != null) {
                ui.type(current.getRoomDescription());
            }

            // Monster encounter
            if (current.hasMonster()) {
                CombatSystem.CombatResult result = combat.runEncounter(player, current.getMonster());

                if (result == CombatSystem.CombatResult.PLAYER_DEAD) {
                    printDeath();
                    return;
                }
                if (result == CombatSystem.CombatResult.MONSTER_DEAD) {
                    current.setHasMonster(false);
                }
                if (result == CombatSystem.CombatResult.FLED) {
                    // returnToLastRoom already performed inside the loop via navigation
                    // Re-check death in case fleeing somehow left player at 0
                    if (player.isDead()) {
                        printDeath();
                        return;
                    }
                    continue;
                }
            }

            // Insanity check (after encounter, sanity may have tipped over)
            if (player.isFullyInsane()) {
                ui.type("Your weak mind cannot comprehend these creatures as you fall into insanity.", 50);
                printDeath();
                return;
            }

            // Room item
            if (current.getItem() != null && !current.isItemTaken()) {
                offerRoomItem(player, current);
            }

            // Navigation
            String input = promptNavigation(player, current);
            handleNavigationInput(player, current, input);
        }

        if (player.isDead()) {
            printDeath();
        }
    }

    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------
    /**
     * Displays available paths and returns whatever the player typed. The raw
     * string is returned so the caller can detect SAVE, Items, a room number,
     * or -1 without re-reading.
     */
    private String promptNavigation(Player player, Room current) {
        ui.type("Which path do you take?", 50);

        for (int linkedIndex : current.getLinkedIndices()) {
            Room next = player.getRoomList()[linkedIndex];
            ui.type(linkedIndex + ": " + next.getPathDescription(), 10);
        }

        ui.type("-1: Return to last room", 10);
        return ui.readLine("");
    }

    private void handleNavigationInput(Player player, Room current, String input) throws IOException {
        switch (input.toUpperCase()) {
            case "SAVE" -> {
                String name = ui.readLine("Enter save name:");
                try {
                    saves.save(player, name);
                    ui.type("Game saved.", 50);
                } catch (IOException e) {
                    ui.type("Could not save: " + e.getMessage(), 50);
                }
                return;
            }
            case "ITEMS" -> {
                combat.outputInventory(player);
                return;
            }
        }

        if (!ui.isValidInteger(input)) {
            ui.type("Input does not match available choices.", 50);
            ui.separator();
            return;
        }

        int choice = Integer.parseInt(input);

        if (choice == -1) {
            returnToLastRoom(player);
            return;
        }

        // Validate the chosen index is actually linked from this room
        for (int linkedIndex : current.getLinkedIndices()) {
            if (choice == linkedIndex) {
                player.getPrevRooms().push(current.getIndex());
                player.setCurrentRoomIndex(choice);
                return;
            }
        }

        ui.type("Input does not match available choices.", 50);
        ui.separator();
    }

    /**
     * Steps back to the most recent room in the history stack. Leaves the stack
     * entry in place when only one room exists so the player can keep pressing
     * -1 and always return to start rather than getting stuck.
     */
    private void returnToLastRoom(Player player) {
        if (player.getPrevRooms().isEmpty()) {
            return;
        }

        player.setCurrentRoomIndex(player.getPrevRooms().peek());

        if (player.getPrevRooms().size() > 1) {
            player.getPrevRooms().pop();
        }
    }

    // -------------------------------------------------------------------------
    // Room item
    // -------------------------------------------------------------------------
    private void offerRoomItem(Player player, Room room) {
        Item item = room.getItem();
        ui.type("Within this room you notice a " + item.getName() + ".", 50);

        String choice = ui.readLine("Do you pick up the " + item.getName() + "? (y/n)");
        while (!choice.equals("y") && !choice.equals("n")) {
            ui.type("Please enter y or n.", 50);
            choice = ui.readLine("");
        }

        if (choice.equals("y")) {
            player.addItem(item);
            room.setItem(null);
            room.setItemTaken(true);
            ui.type(item.getName() + " picked up.", 10);
        }
    }

    // -------------------------------------------------------------------------
    // Altar sequence (room index 1)
    // -------------------------------------------------------------------------
    private void runAltarSequence(Player player) {
        for (String line : StoryText.ALTAR_INTRO) {
            ui.type(line);
        }

        if (player.hasSpokenIncantation()) {
            ui.type(StoryText.ALTAR_ALREADY_SPOKEN);
            return;
        }

        String choice = ui.readLine("Do you speak the text? (y/n)");
        while (!choice.equals("y") && !choice.equals("n")) {
            ui.type("Please enter y or n.", 50);
            choice = ui.readLine("");
        }

        if (choice.equals("y")) {
            player.setIncantationSpoken(true);
            ui.type(StoryText.ALTAR_SPEAK);
        } else {
            ui.type(StoryText.ALTAR_REFUSE);
        }
    }

    // -------------------------------------------------------------------------
    // Final sequence (room index 2)
    // -------------------------------------------------------------------------
    private void runFinalSequence(Player player) {
        // Shared encounter description
        for (String line : StoryText.NY_GOTHOR_SHARED) {
            ui.type(line);
        }

        if (!player.hasSpokenIncantation()) {
            // No incantation — player is compelled to their death
            for (String line : StoryText.NY_GOTHOR_WITHOUT_INCANTATION) {
                ui.type(line);
            }
            printDeath();
            return;
        }

        // Incantation spoken — player fights Ny'Gothor
        for (String line : StoryText.NY_GOTHOR_WITH_INCANTATION) {
            ui.type(line);
        }

        Monster nyGothor = new Monster("Ny-Gothor", 1000, 70);
        CombatSystem.CombatResult result = combat.runEncounter(player, nyGothor);

        if (result == CombatSystem.CombatResult.PLAYER_DEAD
                || result == CombatSystem.CombatResult.FLED) {
            printDeath();
            return;
        }

        // Ny'Gothor defeated — play ending based on remaining sanity
        printEnding(player);
    }

    // -------------------------------------------------------------------------
    // Endings
    // -------------------------------------------------------------------------
    private void printEnding(Player player) {
        for (String line : StoryText.ENDING_SHARED) {
            ui.type(line);
        }

        // Bad ending: sanity > 90; good ending otherwise
        String[] lines = (player.getSanity() > 90)
                ? StoryText.ENDING_BAD
                : StoryText.ENDING_GOOD;

        for (String line : lines) {
            ui.type(line);
        }
    }

    private void printDeath() {
        ui.separator();
        ui.type("You are dead.", 50);
    }

    // -------------------------------------------------------------------------
    // Help
    // -------------------------------------------------------------------------
    private void printHelp() {
        ui.separator();
        ui.type("Help:", 50);
        ui.type("Enter a room number to move to that room.", 50);
        ui.type("Enter -1 to return to the previous room.", 50);
        ui.type("Enter an item name during combat to use it.", 50);
        ui.type("Type SAVE while in a room to save the game.", 50);
        ui.type("Type Items to show your inventory.", 50);
        ui.separator();
    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------
    private void printStorySequence(String[] lines) {
        for (String line : lines) {
            ui.type(line, 50);
        }
    }
}
