package Data;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class moduleData {

    private Map<String, Object> requestBody;

    public static JSONObject create(String projectId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("module_name", "Title Automation API Rest Assured " + formattedNow);
        requestBody.put("project_id", projectId);
        JSONObject jsonObject = new JSONObject(requestBody);

        return jsonObject; // Return the JSONObject
    }

    public static JSONObject update() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("module_name", "Title Automation API Rest Assured Update " + formattedNow);
        JSONObject jsonObject = new JSONObject(requestBody);

        return jsonObject; // Return the JSONObject
    }

    public Map<String, Object> build() {
        return this.requestBody;
    }
}
