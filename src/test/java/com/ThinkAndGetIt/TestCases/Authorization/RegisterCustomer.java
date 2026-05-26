package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.util.HashMap;

import static com.ThinkAndGetIt.Routes.EndPoints.Register;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class RegisterCustomer extends BaseTest {

    @Test
    public void registerCustomerTest(){
        String dynamicEmail = "gady_" + System.currentTimeMillis() + "@gmail.com";

        HashMap<String, Object> body = new HashMap<>();
        body.put("email", dynamicEmail);
        body.put("password",  "MyPass@123");
        body.put("firstName",     "darry");
        body.put("lastName",       "Doe");
        body.put("phone", "+250788123456");
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(Register)
        .then()
                .spec(responseSpec)
                .statusCode(201)
                .log().all()
                .body("data.user.firstName", equalTo("darry"));    }
}
