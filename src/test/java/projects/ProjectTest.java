package projects;

import Helper.ConfigTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AuthUtils;
import utils.OtherUtils;

import java.util.Properties;

public class ProjectTest {
    private static final Properties properties = new Properties();
    private String baseUri;

    @BeforeClass
    public void setup() {
        baseUri = ConfigTest.get("baseUrl");
        RestAssured.baseURI = baseUri;
    }

    @Test
    public void getListProject() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("project", token);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test
    public void searchProject() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("project?filter[title]=galih", token);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String title = response.jsonPath().getString("data.content[0].title");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(title, "galih", "Judul tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }
}
