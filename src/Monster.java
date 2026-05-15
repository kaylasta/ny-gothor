import java.io.Serializable;
import java.util.Random;

public class Monster implements Serializable {

    private int health;
    private String name;
    private int sanityImpact;  // How much sanity this monster drains on encounter
    private int damage;
    private int attackChance;  // % chance the monster lands its attack
    private int dodgeChance;   // % chance the monster dodges the player's attack
    private boolean isDead;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    /**
     * Creates a monster with fixed health, damage, and name. Sanity impact,
     * attack chance, and dodge chance are randomised for variety each run.
     */
    public Monster(String name, int health, int damage) {
        Random rnd = new Random();

        this.name = name;
        this.health = health;
        this.damage = damage;
        this.sanityImpact = rnd.nextInt(20);
        this.attackChance = rnd.nextInt(80);
        this.dodgeChance = rnd.nextInt(15);
        this.isDead = false;
    }

    // -------------------------------------------------------------------------
    // Health & combat
    // -------------------------------------------------------------------------
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Reduces the monster's health by {@code damage}. Marks it as dead if
     * health drops to zero or below.
     *
     * @return true if the monster died from this hit
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

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    // -------------------------------------------------------------------------
    // Stats
    // -------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSanityImpact() {
        return sanityImpact;
    }

    public void setSanityImpact(int sanityImpact) {
        this.sanityImpact = sanityImpact;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAttackChance() {
        return attackChance;
    }

    public void setAttackChance(int attackChance) {
        this.attackChance = attackChance;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(int dodgeChance) {
        this.dodgeChance = dodgeChance;
    }
}
