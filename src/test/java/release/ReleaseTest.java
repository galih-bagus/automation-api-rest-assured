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
    private String createdProjectTitle;

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
        Response response = OtherUtils.responsePost("/crud/release", token, body, 201, "project-schema/create-project-schema.json", true);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }
}