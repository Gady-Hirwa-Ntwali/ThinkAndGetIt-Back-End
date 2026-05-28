package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.ThinkAndGetIt.ReusableMethods.UpdateProperties.updatePropertiesFile;
import static io.restassured.RestAssured.given;

public class LoginMethods extends BaseTest {
public static Response loginTest(String email, String password){
    HashMap<String, Object> body = new HashMap<>();
    body.put("email", email);
    body.put("password", password);

    return given()
            .spec(requestSpec)
            .body(body)
            .when()
            .post(EndPoints.Login)
            .then()
            .spec(responseSpec)
            .log().all()
            .extract().response();
}
}
