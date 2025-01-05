package modules;

import Data.moduleData;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthUtils;
import utils.OtherUtils;

import java.util.Properties;

public class ModuleTest {
    private static final Properties properties = new Properties();
    private final String createdProjectId = "9dd32c57-a44e-4ccf-b55a-b727480aea72";
    private String createdModuleId;

    @Test(priority = 1)
    public void getListModules() {
        String text = "Dengan ini cluster idnya : DSG0001057";

        // Gunakan split untuk memisahkan berdasarkan tanda ":"
        String[] parts = text.split(":");
        String clusterId = parts[1].trim();
        System.out.println("Cluster ID: sadds" + clusterId + "asd");
    }

    @Test(priority = 2)
    public void createModules() {
        JSONObject requestBody = moduleData.create(this.createdProjectId);
        String body = requestBody.toString();
        String token = AuthUtils.login();
        Response response = OtherUtils.responsePost("module", token, body, 201, "moduleSchema/create.json");
        String data = response.asString();
        this.createdModuleId = response.jsonPath().getString("data.id");
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 3)
    public void detailModules() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("module/" + this.createdModuleId, token, 200, "moduleSchema/detail.json");
        String data = response.asString();
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 4)
    public void updateModules() {
        JSONObject requestBody = moduleData.update();
        String body = requestBody.toString();
        String token = AuthUtils.login();
        Response response = OtherUtils.responsePost("module/" + this.createdModuleId, token, body, 200, "moduleSchema/create.json");
        String data = response.asString();
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 5)
    public void deleteModules() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseDelete("module/" + this.createdModuleId, token, 200, "moduleSchema/delete.json");
        String data = response.asString();
        Assert.assertNotNull("Data tidak ditemukan!", data);
    }

    @Test(priority = 6)
    public void detailModulesInvalid() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("module/" + this.createdModuleId, token, 404, "moduleSchema/invalidDetail.json");
        String errroMessage = response.jsonPath().getString("message");
        Assert.assertEquals("No query results for model [App\\Models\\Module] " + this.createdModuleId, errroMessage, "Data ditemukan!");
    }
}