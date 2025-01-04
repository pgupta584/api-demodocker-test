package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class APITest {

    @Test
    public void testGetRequest() {
        // Define base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send GET request and capture response
        Response response = given()
                .when()
                .get("/posts/1")
                .then()
                .extract()
                .response();

        // Assert response status code is 200
        Assert.assertEquals(response.getStatusCode(), 200);

        // Assert response body contains expected data
        Assert.assertTrue(response.getBody().asString().contains("userId"));
    }

    @Test
    public void testPostRequest() {
        // Define base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Create a new post as JSON
        String requestBody = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }";

        // Send POST request
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .extract()
                .response();

        // Assert response status code is 201 (Created)
        Assert.assertEquals(response.getStatusCode(), 201);

        // Assert that the response body contains the title
        Assert.assertTrue(response.getBody().asString().contains("foo"));
    }

    @Test
    public void testPutRequest() {
        // Define base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Create updated post as JSON
        String updatedRequestBody = "{ \"id\": 1, \"title\": \"Updated Title\", \"body\": \"Updated Body\", \"userId\": 1 }";

        // Send PUT request
        Response response = given()
                .contentType("application/json")
                .body(updatedRequestBody)
                .when()
                .put("/posts/1")
                .then()
                .extract()
                .response();

        // Assert response status code is 200 (OK)
        Assert.assertEquals(response.getStatusCode(), 200);

        // Assert that the response body contains the updated title
        Assert.assertTrue(response.getBody().asString().contains("Updated Title"));
    }

    @Test
    public void testDeleteRequest() {
        // Define base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send DELETE request
        Response response = given()
                .when()
                .delete("/posts/1")
                .then()
                .extract()
                .response();

        // Assert response status code is 200 (OK)
        Assert.assertEquals(response.getStatusCode(), 200);

        // Optional: Check that the resource has been deleted (e.g., checking a GET request for the deleted post)
        Response getResponse = given()
                .when()
                .get("/posts/1")
                .then()
                .extract()
                .response();
        Assert.assertEquals(getResponse.getStatusCode(), 404);  // 404 Not Found
    }
}
