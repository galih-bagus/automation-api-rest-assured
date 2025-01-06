package Data;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class releaseData {
    private Map<String, Object> requestBody;

    public static JSONObject create(String projectId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        DateTimeFormatter dateNow = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateEnd = now.plusDays(7);
        DateTimeFormatter dateEndFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedNow = now.format(formatter);
        String formattedDateNow = now.format(dateNow);
        String formattedDateEnd = dateEnd.format(dateNow);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Automation API Rest Assured " + formattedNow);
        requestBody.put("start_date", formattedDateNow);
        requestBody.put("end_date", formattedDateEnd);
        requestBody.put("project_id", projectId);
        JSONObject jsonObject = new JSONObject(requestBody);

        return jsonObject; // Return the JSONObject
    }

    public Map<String, Object> build() {
        return this.requestBody;
    }
}
