package utils;

import Helper.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class AuthUtils {

    private static final String BASE_URL = Config.get("baseUrlKeycloak");

    public static String login() {
        String requestBody = "client_id=" + Config.get("clientId") +
                "&grant_type=" + Config.get("grantType") +
                "&client_secret=" + Config.get("clientSecret") +
                "&scope=" + Config.get("scope") +
                "&username=" + Config.get("usernameValid") +
                "&password=" + Config.get("passwordValid");
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded")
                .body(requestBody)
                .when()
                .post("/token");
        if (response.statusCode() != 200) {
            throw new RuntimeException("Login gagal: " + response.asString());
        }
        return response.jsonPath().getString("access_token");
    }
}

