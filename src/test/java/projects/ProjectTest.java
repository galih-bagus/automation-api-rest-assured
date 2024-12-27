package projects;

import Helper.ConfigTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AuthUtils;
import utils.OtherUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ProjectTest {
    private static final Properties properties = new Properties();
    private String baseUri;
    private String createdProjectTitle;
    private String createdProjectId;

    @BeforeClass
    public void setup() {
        baseUri = ConfigTest.get("baseUrl");
        RestAssured.baseURI = baseUri;
    }

    @Test(priority = 1)
    public void getListProject() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("project", token, 200);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 2)
    public void createProject() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        String token = AuthUtils.login();
        String body = "{\n" +
                "  \"title\": \"Automation API Rest Assured " + formattedNow + "\"\n" +
                "}";
        Response response = OtherUtils.responsePost("project", token, body, 201);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String createdProjectTitleData = response.jsonPath().getString("data.title");
        String createdProjectIdData = response.jsonPath().getString("data.id");
        this.createdProjectTitle = createdProjectTitleData;
        this.createdProjectId = createdProjectIdData;
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 3, dependsOnMethods = "createProject")
    public void searchProject() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("project?filter[title]=" + this.createdProjectTitle, token, 200);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String title = response.jsonPath().getString("data.content[0].title");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(title, this.createdProjectTitle, "Judul tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 4, dependsOnMethods = "createProject")
    public void getDetailProject() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("project/" + this.createdProjectId, token, 200);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String id = response.jsonPath().getString("data.id");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(id, this.createdProjectId, "Id tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 5, dependsOnMethods = "createProject")
    public void updateProject() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        String token = AuthUtils.login();
        String body = "{\n" +
                "  \"title\": \"Automation API Rest Assured Update " + formattedNow + "\"\n" +
                "}";
        Response response = OtherUtils.responsePost("project/" + this.createdProjectId, token, body, 200);
        String id = response.jsonPath().getString("data.id");
        String message = response.jsonPath().getString("message");
        String createdProjectTitleData = response.jsonPath().getString("data.title");
        String createdProjectIdData = response.jsonPath().getString("data.id");
        this.createdProjectTitle = createdProjectTitleData;
        this.createdProjectId = createdProjectIdData;
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(id, this.createdProjectId, "Id tidak sesuai!");
    }

    @Test(priority = 6, dependsOnMethods = "createProject")
    public void deleteProject() {
        System.out.println("Delete Project ID: " + this.createdProjectId);
        System.out.println("Delete Project Title: " + this.createdProjectTitle);
        String token = AuthUtils.login();
        Response response = OtherUtils.responseDelete("project/" + this.createdProjectId, token, 200);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String dataMessage = response.jsonPath().getString("data");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(dataMessage, "Object was deleted", "Pesan data tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 7, dependsOnMethods = "deleteProject")
    public void getDetailProjectInvalidId() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("project/" + this.createdProjectId, token, 404);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String id = response.jsonPath().getString("data.id");
        Assert.assertEquals(message, "The route api/crud/project/null could not be found.", "Pesan tidak sesuai!");
        Assert.assertEquals(id, this.createdProjectId, "Id tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }
}