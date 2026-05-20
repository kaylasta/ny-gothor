import java.util.Random;

/**
 * Handles all combat logic: monster encounters, player and monster attacks, and
 * the item-selection prompt during a fight.
 */
public class CombatSystem {

    public enum CombatResult {
        PLAYER_DEAD,
        MONSTER_DEAD,
        FLED
    }

    private final UI ui;
    private final Random rnd = new Random();

    public CombatSystem(UI ui) {
        this.ui = ui;
    }

    // -------------------------------------------------------------------------
    // Encounter entry point
    // -------------------------------------------------------------------------
    /**
     * Runs a full monster encounter. The player may fight or flee each round.
     * If the player flees, they are sent back to their previous room. If the
     * monster is killed, the room's monster flag is cleared.
     */
    public CombatResult runEncounter(Player player, Monster monster) {
        Room currentRoom = player.getCurrentRoom();

        // Announce the encounter and apply sanity drain
        if (monster.getName().equals("Ny-Gothor")) {
            ui.type("At the sight of " + monster.getName() + " you feel your mind falter.");
        } else {
            ui.type("You notice something shifting within the room.");
            ui.type("At the sight of " + monster.getName() + " you feel your mind falter.");
        }
        ui.type("Sanity decreased by " + monster.getSanityImpact() + ".");
        player.raiseSanity(monster.getSanityImpact());

        boolean fled = false;

        while (!monster.isDead() && !player.isDead() && !fled) {
            ui.type(monster.getName() + " has " + monster.getHealth() + " health remaining.");
            ui.type("You have " + player.getHealth() + " health remaining. You are " + player.getSanity() + "% insane.");

            int choice = promptCombatChoice();
            if (choice == 1) {
                resolveAttackRound(player, monster);
            } else {
                fled = true;
            }
        }

        if (fled) {
            ui.type("You flee.");
            returnToLastRoom(player);
            return CombatResult.FLED;
        }

        if (monster.isDead()) {
            ui.type("The beast falls.");
            currentRoom.setHasMonster(false);
            return CombatResult.MONSTER_DEAD;
        }

        return CombatResult.PLAYER_DEAD;
    }

    // -------------------------------------------------------------------------
    // Attack resolution
    // -------------------------------------------------------------------------
    /**
     * Resolves one full round: player attacks, then - if the monster survives -
     * the monster attacks.
     */
    private void resolveAttackRound(Player player, Monster monster) {
        // Player's turn
        outputInventory(player);
        Item weapon = promptItemChoice(player);

        // Monster may dodge the player's attack
        if (rnd.nextInt(101) >= monster.getDodgeChance()) {
            monster.takeDamage(weapon.getDamage());
            ui.type("You strike " + monster.getName() + " for " + weapon.getDamage() + " damage.");
        } else {
            ui.type("The attack missed.");
        }

        if (monster.isDead()) {
            return;
        }

        // Monster's turn
        ui.type("The monster attacks.");
        if (rnd.nextInt(101) < monster.getAttackChance()) {
            player.takeDamage(monster.getDamage());
            ui.type("You take " + monster.getDamage() + " damage.");
        } else {
            ui.type("The monster's attack missed.");
        }

        ui.separator();
    }

    // -------------------------------------------------------------------------
    // Prompts
    // -------------------------------------------------------------------------
    /**
     * Prompts and validates the fight/run choice. Returns 1 (fight) or 2 (run).
     */
    private int promptCombatChoice() {
        while (true) {
            ui.type("Fight: 1", 50);
            ui.type("Run:   2", 50);
            int choice = ui.readInt("");
            if (choice == 1 || choice == 2) {
                return choice;
            }
            ui.type("Invalid input - enter 1 to fight or 2 to run.", 50);
        }
    }

    /**
     * Prompts the player to name an item from their inventory. Re-prompts until
     * a valid item name is entered.
     */
    private Item promptItemChoice(Player player) {
        while (true) {
            String input = ui.readLine("What will you use?");
            for (Item item : player.getInventory()) {
                if (item.getName().equalsIgnoreCase(input)) {
                    return item;
                }
            }
            ui.type("Invalid input - enter the exact item name.", 50);
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------
    /**
     * Prints the player's current inventory.
     */
    public void outputInventory(Player player) {
        ui.type("You currently have:");
        for (Item item : player.getInventory()) {
            ui.type(" - " + item.toString(), 50);
        }
        ui.separator();
    }

    /**
     * Moves the player back to their previous room (used when fleeing).
     */
    private void returnToLastRoom(Player player) {
        if (player.getPrevRooms().isEmpty()) {
            return;
        }

        player.setCurrentRoomIndex(
                player.getPrevRooms().get(player.getPrevRooms().size() - 1)
        );

        if (player.getPrevRooms().size() > 1) {
            player.getPrevRooms().pop();
        }
    }
}
