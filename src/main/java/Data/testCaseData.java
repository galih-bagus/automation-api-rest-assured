package Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class testCaseData {

    private Map<String, Object> requestBody;

    public static JSONObject create(String projectId, String moduleId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        Random random = new Random();
        int randomInt = random.nextInt(10);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Title Automation API Rest Assured " + formattedNow);
        requestBody.put("description", "Description Automation API Rest Assured " + formattedNow);
        requestBody.put("precondition", "ACCEPTANCE");
        requestBody.put("priority", "LOW");
        requestBody.put("tag", "POSITIVE");
        requestBody.put("project_id", projectId);
        requestBody.put("module_id", moduleId);

        JSONArray steps = new JSONArray();

//        Add step with random step
        for (int i = 0; i <= randomInt; i++) {
            JSONObject step = new JSONObject();
            step.put("title", "Step " + (i + 1) + " Automation API Rest Assured " + formattedNow);
            step.put("expected_result", "Expected Result " + (i + 1) + " Automation API Rest Assured " + formattedNow);
            step.put("position_step", i);
            steps.put(step);
        }
        requestBody.put("steps", steps);


        JSONObject jsonObject = new JSONObject(requestBody);

        return jsonObject; // Return the JSONObject
    }

    public Map<String, Object> build() {
        return this.requestBody;
    }
}
