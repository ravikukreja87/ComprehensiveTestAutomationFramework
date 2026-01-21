package ui.web.taf.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Serializes a Java object to a JSON string.
     *
     * @param object The object to serialize.
     * @return The JSON string representation of the object.
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            LoggingUtils.error("Failed to serialize object to JSON: " + e.getMessage());
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    /**
     * Deserializes a JSON string to a Java object.
     *
     * @param json  The JSON string.
     * @param clazz The class of the object to deserialize into.
     * @param <T>   The type of the object.
     * @return The deserialized object.
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            LoggingUtils.error("Failed to deserialize JSON to object: " + e.getMessage());
            throw new RuntimeException("Failed to deserialize JSON to object", e);
        }
    }

    /**
     * Reads a JSON file and deserializes it into a Java object.
     *
     * @param filePath The path to the JSON file.
     * @param clazz    The class of the object to deserialize into.
     * @param <T>      The type of the object.
     * @return The deserialized object.
     */
    public static <T> T readJsonFile(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            LoggingUtils.error("Failed to read JSON file: " + filePath + ". Error: " + e.getMessage());
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
}
