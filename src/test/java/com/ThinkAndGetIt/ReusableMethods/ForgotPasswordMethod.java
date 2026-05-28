package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.ThinkAndGetIt.Routes.EndPoints.Forgot_Password;
import static io.restassured.RestAssured.given;

public class ForgotPasswordMethod extends BaseTest {

    public static Response forgotPassword(String email){
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(Forgot_Password)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();

    }
}
