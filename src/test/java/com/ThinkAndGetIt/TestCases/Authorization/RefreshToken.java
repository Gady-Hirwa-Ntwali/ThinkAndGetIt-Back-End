package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.ThinkAndGetIt.Routes.EndPoints.PasswordReset;
import static com.ThinkAndGetIt.Routes.EndPoints.TokenRefresh;
import static io.restassured.RestAssured.given;

public class RefreshToken extends BaseTest {

    @Test
    public void refreshToken(){
        RegisterCustomer.successfulRegister();
        HashMap<String, String> body = new HashMap<>();
        body.put("refreshToken", properties.getProperty("refreshToken"));

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(TokenRefresh )
                .then()
                .spec(responseSpec)
                .log().all()
                .statusCode(200);
    }
}
