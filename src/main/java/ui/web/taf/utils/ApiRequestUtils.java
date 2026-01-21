package ui.web.taf.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiRequestUtils {

    /**
     * Performs a GET request to the specified endpoint.
     *
     * @param endpoint The API endpoint URL.
     * @param headers  A map of headers to include in the request.
     * @return The Response object.
     */
    public static Response getRequest(String endpoint, Map<String, String> headers) {
        LoggingUtils.info("Sending GET request to: " + endpoint);
        RequestSpecification request = RestAssured.given();
        if (headers != null) {
            request.headers(headers);
        }
        Response response = request.get(endpoint);
        LoggingUtils.info("Response Status Code: " + response.getStatusCode());
        return response;
    }

    /**
     * Performs a POST request to the specified endpoint.
     *
     * @param endpoint The API endpoint URL.
     * @param payload  The request body payload.
     * @param headers  A map of headers to include in the request.
     * @return The Response object.
     */
    public static Response postRequest(String endpoint, Object payload, Map<String, String> headers) {
        LoggingUtils.info("Sending POST request to: " + endpoint);
        RequestSpecification request = RestAssured.given();
        if (headers != null) {
            request.headers(headers);
        }
        if (payload != null) {
            request.body(payload);
        }
        Response response = request.post(endpoint);
        LoggingUtils.info("Response Status Code: " + response.getStatusCode());
        return response;
    }
}
