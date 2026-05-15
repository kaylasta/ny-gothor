/**
 * Entry point for Ny'Gothor.
 * Creates the shared UI and hands control to GameEngine.
 */
public class NyGothor {

    public static void main(String[] args) throws Exception {
        UI ui = new UI();
        GameEngine engine = new GameEngine(ui);
        engine.mainMenu();
    }
}
