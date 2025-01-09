package testCases;

import Data.testCaseData;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthUtils;
import utils.OtherUtils;

public class TestCaseTest {
    private final String createdProjectId = "9dd32c57-a44e-4ccf-b55a-b727480aea72";
    private final String createdModuleId = "9dd8ee32-0a1d-4b45-a45e-1b38d959b10e";
    private String createdTestCaseTitle;
    private String createdTestCaseCode;

    @Test(priority = 1)
    public void getListTestCaseByModule() {
        String token = AuthUtils.login();
        Response response = OtherUtils.responseGet("/crud/test-case?filter[module_id]=" + this.createdModuleId + "&filter[project_id]=" + this.createdProjectId + "&filter[is_archive]=false", token, 200, "testCaseSchema/list.json", true);
        String content = response.jsonPath().getString("data.content");
        System.out.println(content);
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull(content, "Data tidak ditemukan!");
    }

    @Test(priority = 2)
    public void createTestCase() {
        JSONObject requestBody = testCaseData.create(createdProjectId, createdModuleId);
        String token = AuthUtils.login();
        String body = requestBody.toString();
        Response response = OtherUtils.responsePost("/crud/test-case", token, body, 201, "testCaseSchema/create.json", false);
        String data = response.asString();
        String message = response.jsonPath().getString("message");
        String createdTestCaseTitleData = response.jsonPath().getString("data.title");
        String createdTestCaseCodeData = response.jsonPath().getString("data.case_code");
        this.createdTestCaseTitle = createdTestCaseTitleData;
        this.createdTestCaseCode = createdTestCaseCodeData;
        Assert.assertEquals(message, "success", "Pesan tidak sesuai!");
        Assert.assertNotNull(data, "Data tidak ditemukan!");
    }
}
