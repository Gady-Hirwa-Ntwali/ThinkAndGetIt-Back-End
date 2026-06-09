package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Methods extends BaseTest {

    public static Response GetMethod(String endpoint) {
        return GetMethod(endpoint, "");
    }

    public static Response GetMethod(String endpoint, String token) {
        if (token == null || token.isEmpty()) {
            return given()
                    .spec(requestSpec)
                    .when()
                    .get(endpoint)
                    .then()
                    .spec(responseSpec)
                    .log().all()
                    .extract().response();
        }

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    public static Response GetFilteredProduct(Map queryParams, String endPoint){
                Response response = given()
                .spec(requestSpec)
                .queryParams(queryParams)
                .when()
                .get(endPoint)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .log().all()
                .extract().response();
                return response;
    }

    public static Response postMethod(String endpoint, Object body) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer ")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }
    public static Response postMethod(String endpoint, Object body, String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer "+token)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }
    public static Response putMethod(String endpoint, Object body, String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer "+token)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    public static Response DeleteMethod(String targetEndpoint) {
        return DeleteMethod(targetEndpoint, "");
    }

    public static Response DeleteMethod(String targetEndpoint, String token){
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(targetEndpoint);
    }
}
