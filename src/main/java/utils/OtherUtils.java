package utils;

import Helper.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class OtherUtils {
    public static Response responseGet(String baseUrl, String token, Integer statusCode) {
        Response response = RestAssured.given()
                .baseUri(Config.get("baseUrl"))
                .header("Authorization", "Bearer " + token)
                .when()
                .get(baseUrl)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();

        return response;
    }

    public static Response responsePost(String baseUrl, String token, String requestBody, Integer statusCode) {
        Response response = RestAssured.given()
                .contentType("application/json")
                .baseUri(Config.get("baseUrl"))
                .header("Authorization", "Bearer " + token)
                .when()
                .body(requestBody)
                .post(baseUrl)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();

        return response;
    }

    public static Response responseDelete(String baseUrl, String token, Integer statusCode) {
        Response response = RestAssured.given()
                .contentType("application/json")
                .baseUri(Config.get("baseUrl"))
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(baseUrl)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();

        return response;
    }

//    public static JSONObject getRandomProjectDetail(String baseUrl, String token) {
//        // Mengirimkan request GET ke endpoint API
//        Response response = RestAssured.given()
//                .baseUri(Config.get("baseUrl"))
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get(baseUrl) // Gantilah endpoint sesuai dengan API yang benar
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        // Mengambil data proyek dalam bentuk JSON
//        JSONObject jsonResponse = new JSONObject(response.asString());
//        JSONObject data = jsonResponse.getJSONObject("data");
//        JSONArray projects = data.getJSONArray("content");
//
//        // Mengambil proyek secara acak dari daftar proyek
//        Random rand = new Random();
//        int randomIndex = rand.nextInt(projects.length());
//        JSONObject randomProject = projects.getJSONObject(randomIndex);
//
//        // Mengembalikan detail proyek yang dipilih secara acak
//        return randomProject;
//    }
}
