package projects;

import Data.projectData;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthUtils;
import utils.OtherUtils;

public class ProjectTest {
    private String createdProjectTitle;
    private String createdProjectId;

    @Test(priority = 1)
    public void getListProject() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("/crud/project", token, 200, "project-schema/project-schema.json");
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);

    }

    @Test(priority = 2)
    public void createProject() {
        String token = AuthUtils.login();
        JSONObject requestBody = projectData.create();
        String body = requestBody.toString();
        Response response = OtherUtils.responsePost("/crud/project", token, body, 201, "project-schema/create-project-schema.json");
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
        Response response = OtherUtils.responseGet("/crud/project?filter[title]=" + this.createdProjectTitle, token, 200, "project-schema/project-schema.json");
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String title = response.jsonPath().getString("data.content[0].title");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(title, this.createdProjectTitle, "Judul tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 4, dependsOnMethods = "searchProject")
    public void getDetailProject() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("/crud/project/" + this.createdProjectId, token, 200, "project-schema/detail-project-schema.json");
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String id = response.jsonPath().getString("data.id");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(id, this.createdProjectId, "Id tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 5, dependsOnMethods = "getDetailProject")
    public void updateProject() {
        String token = AuthUtils.login();
        JSONObject requestBody = projectData.update();
        String body = requestBody.toString();
        Response response = OtherUtils.responsePost("/crud/project/" + this.createdProjectId, token, body, 200, "project-schema/create-project-schema.json");
        String id = response.jsonPath().getString("data.id");
        String message = response.jsonPath().getString("message");
        String createdProjectTitleData = response.jsonPath().getString("data.title");
        String createdProjectIdData = response.jsonPath().getString("data.id");
        this.createdProjectTitle = createdProjectTitleData;
        this.createdProjectId = createdProjectIdData;
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(id, this.createdProjectId, "Id tidak sesuai!");
    }

    @Test(priority = 6, dependsOnMethods = "updateProject")
    public void deleteProject() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseDelete("/crud/project/" + this.createdProjectId, token, 200, "project-schema/delete-project-schema.json");
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String dataMessage = response.jsonPath().getString("data");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(dataMessage, "Object was deleted", "Pesan data tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }
}