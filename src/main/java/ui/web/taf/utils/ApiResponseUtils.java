package ui.web.taf.utils;

import io.restassured.response.Response;

public class ApiResponseUtils {

    /**
     * Validates the response status code.
     *
     * @param response       The Response object.
     * @param expectedStatus The expected HTTP status code.
     */
    public static void validateResponseStatus(Response response, int expectedStatus) {
        LoggingUtils.info("Validating response status code. Expected: " + expectedStatus + ", Actual: " + response.getStatusCode());
        if (response.getStatusCode() != expectedStatus) {
            throw new AssertionError("Expected status code " + expectedStatus + " but got " + response.getStatusCode());
        }
    }

    /**
     * Extracts a value from the JSON response using a JSON path.
     *
     * @param response The Response object.
     * @param jsonPath The JSON path to the desired value.
     * @return The extracted value as a String.
     */
    public static String extractJsonPath(Response response, String jsonPath) {
        LoggingUtils.info("Extracting JSON path: " + jsonPath);
        try {
            return response.jsonPath().getString(jsonPath);
        } catch (Exception e) {
            LoggingUtils.error("Failed to extract JSON path: " + jsonPath + ". Error: " + e.getMessage());
            throw e;
        }
    }
}
