package ui.web.taf.utils.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class AssertionUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Asserts that two JSON strings are semantically equal, ignoring formatting and key order.
     *
     * @param expectedJson The expected JSON string.
     * @param actualJson   The actual JSON string.
     */
    public static void assertJsonEquals(String expectedJson, String actualJson) {
        LoggingUtils.info("Asserting JSON equality.");
        try {
            JsonNode expectedNode = objectMapper.readTree(expectedJson);
            JsonNode actualNode = objectMapper.readTree(actualJson);
            Assert.assertEquals(actualNode, expectedNode, "The JSON objects are not equal.");
        } catch (IOException e) {
            LoggingUtils.error("Failed to parse JSON for comparison: " + e.getMessage());
            throw new RuntimeException("Invalid JSON format provided for comparison.", e);
        }
    }

    /**
     * Asserts that a list of strings contains a specific value.
     *
     * @param list  The list of strings to check.
     * @param value The value to search for in the list.
     */
    public static void assertListContains(List<String> list, String value) {
        LoggingUtils.info("Asserting that list contains value: " + value);
        Assert.assertTrue(list.contains(value), "The list does not contain the expected value: " + value);
    }
}
