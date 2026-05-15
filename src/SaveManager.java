import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles all save/load operations. Save files are written to the user's
 * Documents folder under a dedicated subdirectory.
 */
public class SaveManager {

    private static final String SAVE_FOLDER = "Ny-Gothor Saves";

    // -------------------------------------------------------------------------
    // Save
    // -------------------------------------------------------------------------
    /**
     * Serialises {@code player} to a file named {@code saveName} inside the
     * save directory, creating the directory if it does not exist.
     *
     * @throws IOException if the file cannot be written
     */
    public void save(Player player, String saveName) throws IOException {
        Path saveDir = getSaveDirectory();
        Files.createDirectories(saveDir);

        Path filePath = saveDir.resolve(saveName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            out.writeObject(player);
        }
    }

    // -------------------------------------------------------------------------
    // Load
    // -------------------------------------------------------------------------
    /**
     * Deserialises and returns a {@link Player} from {@code filePath}.
     *
     * @throws IOException if the file cannot be read
     * @throws ClassNotFoundException if the serialised class is not found
     */
    public Player load(Path filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (Player) in.readObject();
        }
    }

    // -------------------------------------------------------------------------
    // List saves
    // -------------------------------------------------------------------------
    /**
     * Returns all regular files found in the save directory, or an empty array
     * if the directory does not exist or contains no saves.
     *
     * @throws IOException if the directory cannot be read
     */
    public Path[] listSaves() throws IOException {
        Path saveDir = getSaveDirectory();

        if (!Files.exists(saveDir) || !Files.isDirectory(saveDir)) {
            return new Path[0];
        }

        return Files.list(saveDir)
                .filter(Files::isRegularFile)
                .toArray(Path[]::new);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------
    private Path getSaveDirectory() {
        return Paths.get(System.getProperty("user.home"), "Documents", SAVE_FOLDER);
    }
}
