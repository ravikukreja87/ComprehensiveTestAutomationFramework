package ui.web.taf.utils.api;

import io.restassured.response.Response;
import java.util.Map;

/**
 * Facade class for API interactions, delegating to specific utility classes.
 */
public class ApiUtils {

    public static Response getRequest(String endpoint, Map<String, String> headers) {
        return ApiRequestUtils.getRequest(endpoint, headers);
    }

    public static Response postRequest(String endpoint, Object payload, Map<String, String> headers) {
        return ApiRequestUtils.postRequest(endpoint, payload, headers);
    }

    public static void validateResponseStatus(Response response, int expectedStatus) {
        ApiResponseUtils.validateResponseStatus(response, expectedStatus);
    }

    public static String extractJsonPath(Response response, String jsonPath) {
        return ApiResponseUtils.extractJsonPath(response, jsonPath);
    }
}
