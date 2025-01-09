package testCases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthUtils;
import utils.OtherUtils;

public class TestCaseTest {
    private final String createdProjectId = "9dd32c57-a44e-4ccf-b55a-b727480aea72";
    private final String createdModuleId = "9dd8ec8b-37a1-4615-9946-acea669b502c";

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
}
