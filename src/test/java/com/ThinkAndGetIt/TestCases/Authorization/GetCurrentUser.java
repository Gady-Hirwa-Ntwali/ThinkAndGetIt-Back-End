package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import org.testng.annotations.Test;
import static com.ThinkAndGetIt.Routes.EndPoints.User;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetCurrentUser extends BaseTest {
    @Test
    public void getCurrentUserTest(){
        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + properties.getProperty("token"))
                .when()
                .get(User)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data.user.firstName", equalTo("darry"));

    }
}
