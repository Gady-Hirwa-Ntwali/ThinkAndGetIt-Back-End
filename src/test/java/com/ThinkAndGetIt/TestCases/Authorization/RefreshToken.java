package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.ThinkAndGetIt.Routes.EndPoints.PasswordReset;
import static io.restassured.RestAssured.given;

public class RefreshToken extends BaseTest {

    @Test
    public void forgetPasswordTest(){
        HashMap<String, String> body = new HashMap<>();
        body.put("password", "GoodGood");

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(PasswordReset + properties.getProperty("token"))
                .then()
                .spec(responseSpec)
                .log().all()
                .statusCode(200);
    }
}
