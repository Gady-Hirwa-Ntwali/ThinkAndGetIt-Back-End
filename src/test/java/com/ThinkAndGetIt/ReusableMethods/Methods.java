package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Methods extends BaseTest {

    public static Response GetMethod(String endpoint, String token){
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

    public static Response postMethod(String endpoint, Object body){
           return given()
                    .spec(requestSpec)
                    .body(body)
                    .when()
                    .post(endpoint)
                    .then()
                    .spec(responseSpec)
                    .log().all()
                    .extract().response();

    }
}
