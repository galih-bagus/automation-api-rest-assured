package profile;

import Helper.ConfigTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AuthUtils;
import utils.OtherUtils;

import java.util.Properties;

public class ProfileTest {
    private static final Properties properties = new Properties();
    private String baseUri;

    @BeforeClass
    public void setup() {
        baseUri = ConfigTest.get("baseUrl");
        RestAssured.baseURI = baseUri;
    }

    @Test
    public void getMyProfile() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("user/my", token, 200);
        String email = response.jsonPath().getString("data.email");
        Assert.assertEquals(email, "galih.bagus@javan.co.id", "Email tidak sesuai!");
    }
}
