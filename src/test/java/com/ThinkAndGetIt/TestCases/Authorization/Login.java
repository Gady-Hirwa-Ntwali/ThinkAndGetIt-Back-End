package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Login extends BaseTest {

    @Test
    public void loginWithEmailAndPassword(){
        HashMap<String, Object> body = new HashMap<>();
        body.put("email", "darry@example.com");
        body.put("password",  "MyPass@123");
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(EndPoints.Login)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .log().all()
                .body("data.user.firstName", equalTo("darry"));
    }
}
