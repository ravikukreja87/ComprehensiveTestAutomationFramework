package ui.web.taf.utils.system;

import ui.web.taf.utils.core.LoggingUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    /**
     * Checks if a file exists at the given path.
     *
     * @param filePath The path to the file.
     * @return True if the file exists, false otherwise.
     */
    public static boolean checkFileExists(String filePath) {
        File file = new File(filePath);
        boolean exists = file.exists() && !file.isDirectory();
        LoggingUtils.info("Checking file existence for: " + filePath + ". Exists: " + exists);
        return exists;
    }

    /**
     * Reads the content of a file into a string.
     *
     * @param filePath The path to the file.
     * @return The content of the file.
     */
    public static String readFileContent(String filePath) {
        try {
            LoggingUtils.info("Reading content from file: " + filePath);
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            LoggingUtils.error("Failed to read file content: " + e.getMessage());
            throw new RuntimeException("Failed to read file", e);
        }
    }

    /**
     * Deletes a file at the given path.
     *
     * @param filePath The path to the file.
     * @return True if the file was deleted successfully, false otherwise.
     */
    public static boolean deleteFile(String filePath) {
        try {
            LoggingUtils.info("Deleting file: " + filePath);
            return Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            LoggingUtils.error("Failed to delete file: " + e.getMessage());
            throw new RuntimeException("Failed to delete file", e);
        }
    }
}
