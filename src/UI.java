import java.util.Scanner;

/**
 * Handles all console I/O: animated text output and user input. A single
 * Scanner is created once and reused throughout the session.
 */
public class UI {

    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    // -------------------------------------------------------------------------
    // Output
    // -------------------------------------------------------------------------
    /**
     * Prints {@code text} one character at a time with a {@code delayMs}
     * millisecond pause between characters, then adds a newline. Uses
     * Thread.sleep instead of a busy-wait spin-loop.
     */
    public void type(String text, int delayMs) {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            if (delayMs > 0) {
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println();
    }

    /**
     * Prints animated text at the default narrative speed (10 ms/char).
     */
    public void type(String text) {
        type(text, 10);
    }

    /**
     * Prints a plain separator line with no animation.
     */
    public void separator() {
        System.out.println("----------------------------------------------------------------------");
    }

    // -------------------------------------------------------------------------
    // Input
    // -------------------------------------------------------------------------
    /**
     * Optionally displays {@code prompt}, then reads and returns one line of
     * input. The prompt is skipped when it is null or blank.
     */
    public String readLine(String prompt) {
        if (prompt != null && !prompt.isBlank()) {
            type(prompt, 50);
        }
        return scanner.nextLine().trim();
    }

    /**
     * Reads a line and returns it parsed as an integer. Re-prompts until the
     * user provides a valid integer. Accepts negative integers (e.g. -1).
     */
    public int readInt(String prompt) {
        String input = readLine(prompt);
        while (!isValidInteger(input)) {
            type("Invalid input. Please enter a whole number.", 50);
            input = readLine("");
        }
        return Integer.parseInt(input);
    }

    /**
     * Returns true if {@code input} is a valid integer (including negative
     * values such as -1 used for "return to last room").
     */
    public boolean isValidInteger(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        int start = 0;
        if (input.charAt(0) == '-') {
            if (input.length() == 1) {
                return false; // bare "-" is not a number

            }
            start = 1;
        }

        for (int i = start; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
