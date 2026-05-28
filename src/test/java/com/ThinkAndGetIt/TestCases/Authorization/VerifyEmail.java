package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.ThinkAndGetIt.TestCases.Authorization.RegisterCustomer.successfulRegister;
import static io.restassured.RestAssured.given;

public class VerifyEmail extends BaseTest {
    @Test
    public void emailVerificationTest(){
        HashMap<String, String> body = new HashMap<>();
        body.put("email", properties.getProperty("email"));

        successfulRegister();

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(EndPoints.VerifyEmail + "/"+ properties.getProperty("token"))
                .then()
                .spec(responseSpec)
                .log().all()
                .statusCode(200);
    }
}
