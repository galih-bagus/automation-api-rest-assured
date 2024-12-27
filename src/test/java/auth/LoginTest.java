package auth;

import Helper.ConfigTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Properties;

public class LoginTest {
    private static final Properties properties = new Properties();
    private String baseUri;

    @BeforeClass
    public void setup() {
        baseUri = ConfigTest.get("baseUrlKeycloak");
        RestAssured.baseURI = baseUri;
    }

    @Test
    public void LoginTestWithInvalidUserName() {
        String requestBody = "client_id=" + ConfigTest.get("clientId") +
                "&grant_type=" + ConfigTest.get("grantType") +
                "&client_secret=" + ConfigTest.get("clientSecret") +
                "&scope=" + ConfigTest.get("scope") +
                "&username=galih.bagusa" +
                "&password=password";
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .body(requestBody)
                .when()
                .post("/token")
                .then()
                .statusCode(401)
                .extract()
                .response();

        String error = response.jsonPath().getString("error");
        Assert.assertEquals(error, "invalid_grant", "Error message tidak sesuai!");
    }

    @Test
    public void LoginTestWithInvalidPassword() {
        String requestBody = "client_id=" + ConfigTest.get("clientId") +
                "&grant_type=" + ConfigTest.get("grantType") +
                "&client_secret=" + ConfigTest.get("clientSecret") +
                "&scope=" + ConfigTest.get("scope") +
                "&username=galih.bagus" +
                "&password=password";
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .body(requestBody)
                .when()
                .post("/token")
                .then()
                .statusCode(401)
                .extract()
                .response();

        String error = response.jsonPath().getString("error");
        Assert.assertEquals(error, "invalid_grant", "Error message tidak sesuai!");
    }

    @Test
    public void LoginTestWithValidUser() {
        String requestBody = "client_id=" + ConfigTest.get("clientId") +
                "&grant_type=" + ConfigTest.get("grantType") +
                "&client_secret=" + ConfigTest.get("clientSecret") +
                "&scope=" + ConfigTest.get("scope") +
                "&username=" + ConfigTest.get("usernameValid") +
                "&password=" + ConfigTest.get("passwordValid");
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .body(requestBody)
                .when()
                .post("/token")
                .then()
                .statusCode(200)
                .extract()
                .response();
        String accessToken = response.jsonPath().getString("access_token");
        Assert.assertNotNull("Access token tidak ditemukan!", accessToken);
    }
}
