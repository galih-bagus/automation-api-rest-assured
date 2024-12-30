package Data;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class projectData {

    private Map<String, Object> requestBody;

    public static JSONObject create() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Automation API Rest Assured " + formattedNow);
        JSONObject jsonObject = new JSONObject(requestBody);

        return jsonObject; // Return the JSONObject
    }

    public static JSONObject update() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Automation API Rest Assured Update " + formattedNow);
        JSONObject jsonObject = new JSONObject(requestBody);

        return jsonObject; // Return the JSONObject
    }

    public static void main(String[] args) {
        JSONObject updateBody = update();
        System.out.println("Update Body: " + updateBody);
    }

    public Map<String, Object> build() {
        return this.requestBody;
    }
}
