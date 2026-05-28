package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.ThinkAndGetIt.TestCases.Authorization.Login.updatePropertiesFile;
import static io.restassured.RestAssured.given;

public class LoginMethods extends BaseTest {
public static void loginTest(String email, String password){
    HashMap<String, Object> body = new HashMap<>();
    body.put("email", email);
    body.put("password", password);

    Response response = given()
            .spec(requestSpec)
            .body(body)
            .when()
            .post(EndPoints.Login)
            .then()
            .spec(responseSpec)
            .statusCode(200)
            .log().all()
            .extract().response();

    String token = response.path("data.token");
    String refreshToken = response.path("data.refreshToken");
    updatePropertiesFile(token, refreshToken);
}
}
