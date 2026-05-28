package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.util.HashMap;

import static com.ThinkAndGetIt.Routes.EndPoints.Register;
import static com.ThinkAndGetIt.TestCases.Authorization.Login.updatePropertiesFile;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class RegisterCustomer extends BaseTest {

    @Test
    public static void registerCustomerTest() {
        String dynamicEmail = "gady_" + System.currentTimeMillis() + "@gmail.com";

        HashMap<String, Object> body = new HashMap<>();
        body.put("email", dynamicEmail);
        body.put("password", "MyPass@123");
        body.put("firstName", "darry");
        body.put("lastName", "Doe");
        body.put("phone", "+250788123456");
        Response response = given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(Register)
                .then()
                .spec(responseSpec)
                .log().all()
                .statusCode(201)
                .extract().response();
        String token = response.path("data.token");
        String refreshToken = response.path("data.refreshToken");
        updatePropertiesFile(token, refreshToken);

    }
}
