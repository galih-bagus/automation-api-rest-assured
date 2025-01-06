package release;

import Data.releaseData;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthUtils;
import utils.OtherUtils;

public class ReleaseTest {
    private final String createdProjectId = "9dd32c57-a44e-4ccf-b55a-b727480aea72";
    private String releaseCreatedId;
    private String createdReleasedTitle;
    private String startDate;
    private String endDate;

    @Test(priority = 1)
    public void getListRelease() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("/crud/release?filter[project_id]=" + this.createdProjectId + "&filter[is_archive]=false", token, 200, "releaseSchema/list.json", true);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);

    }

    @Test(priority = 2)
    public void createRelease() {
        String token = AuthUtils.login();
        JSONObject requestBody = releaseData.create(this.createdProjectId);
        String body = requestBody.toString();
        Response response = OtherUtils.responsePost("/crud/release", token, body, 201, "releaseSchema/create.json", true);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        this.releaseCreatedId = response.jsonPath().getString("data.id");
        this.createdReleasedTitle = response.jsonPath().getString("data.name");
        this.startDate = response.jsonPath().getString("data.start_date");
        this.endDate = response.jsonPath().getString("data.end_date");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }


    @Test(priority = 3, dependsOnMethods = "createRelease")
    public void searchRelease() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("/crud/release?filter[name]=" + this.createdReleasedTitle + "&filter[start_date]=" + this.startDate + "&filter[end_date]=" + this.endDate + "&filter[project_id]=" + this.createdProjectId + "&filter[is_archive]=false", token, 200, "releaseSchema/list.json", true);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String title = response.jsonPath().getString("data.content[0].name");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(title, this.createdReleasedTitle, "Judul tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 4, dependsOnMethods = "searchRelease")
    public void getDetailRelease() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("/crud/release/" + this.releaseCreatedId, token, 200, "releaseSchema/detail.json", true);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String id = response.jsonPath().getString("data.id");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(id, this.releaseCreatedId, "Id tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 5, dependsOnMethods = "getDetailRelease")
    public void updateRelease() {
        String token = AuthUtils.login();
        JSONObject requestBody = releaseData.update(this.createdProjectId);
        String body = requestBody.toString();
        Response response = OtherUtils.responsePost("/crud/release/" + this.releaseCreatedId, token, body, 200, "releaseSchema/create.json", true);
        String id = response.jsonPath().getString("data.id");
        String message = response.jsonPath().getString("message");
        String createdReleaseTitleData = response.jsonPath().getString("data.title");
        String createdReleaseIdData = response.jsonPath().getString("data.id");
        this.createdReleasedTitle = createdReleaseTitleData;
        this.releaseCreatedId = createdReleaseIdData;
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(id, this.releaseCreatedId, "Id tidak sesuai!");
    }

    @Test(priority = 6, dependsOnMethods = "updateRelease")
    public void deleteRelease() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseDelete("/crud/release/" + this.releaseCreatedId, token, 200, "releaseSchema/delete.json");
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String dataMessage = response.jsonPath().getString("data");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertEquals(dataMessage, "Object was deleted", "Pesan data tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }
}