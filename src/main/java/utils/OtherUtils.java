package utils;

import Helper.Config;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.io.File;


public class OtherUtils {
    public static Response responseGet(String baseUrl, String token, Integer statusCode, String jsonSchemaPath, Boolean isValidateJson) {
        File schemaFile = new File("src/test/resources/" + jsonSchemaPath);
        if (!schemaFile.exists()) {
            throw new IllegalArgumentException("JSON Schema tidak ditemukan di path: " + jsonSchemaPath);
        }
        ValidatableResponse validatableResponse = RestAssured.given()
                .baseUri(Config.get("baseUrl"))
                .header("Authorization", "Bearer " + token)
                .when()
                .get(baseUrl)
                .then();

        if (isValidateJson) {
            validatableResponse = validatableResponse.assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
        }

        Response response = validatableResponse.statusCode(statusCode)
                .extract()
                .response();

        return response;
    }

    public static Response responsePost(String baseUrl, String token, String requestBody, Integer statusCode, String jsonSchemaPath, Boolean isValidateJson) {
        File schemaFile = new File("src/test/resources/" + jsonSchemaPath);
        if (!schemaFile.exists()) {
            throw new IllegalArgumentException("JSON Schema tidak ditemukan di path: " + jsonSchemaPath);
        }
        ValidatableResponse validatableResponse = RestAssured.given()
                .contentType("application/json")
                .baseUri(Config.get("baseUrl"))
                .header("Authorization", "Bearer " + token)
                .when()
                .body(requestBody)
                .post(baseUrl)
                .then();

        if (isValidateJson) {
            validatableResponse = validatableResponse.assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
        }

        Response response = validatableResponse.statusCode(statusCode)
                .extract()
                .response();

        return response;
    }

    public static Response responseDelete(String baseUrl, String token, Integer statusCode, String jsonSchemaPath) {
        File schemaFile = new File("src/test/resources/" + jsonSchemaPath);
        if (!schemaFile.exists()) {
            throw new IllegalArgumentException("JSON Schema tidak ditemukan di path: " + jsonSchemaPath);
        }
        Response response = RestAssured.given()
                .contentType("application/json")
                .baseUri(Config.get("baseUrl"))
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(baseUrl)
                .then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile))
                .statusCode(statusCode)
                .extract()
                .response();

        return response;
    }
}
