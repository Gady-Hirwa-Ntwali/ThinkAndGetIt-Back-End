package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import org.testng.annotations.Test;
import java.util.HashMap;

import static com.ThinkAndGetIt.Routes.EndPoints.Forgot_Password;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ForgotPassword extends BaseTest {
    @Test
    public void forgetPasswordTest(){
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(Forgot_Password)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("message", equalTo("If an account with that email exists, a reset link has been sent."));
    }
}
