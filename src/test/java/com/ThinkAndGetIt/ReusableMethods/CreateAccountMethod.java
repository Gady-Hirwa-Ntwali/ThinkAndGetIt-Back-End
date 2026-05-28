package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import io.restassured.response.Response;
import static com.ThinkAndGetIt.Routes.EndPoints.Register;
import static io.restassured.RestAssured.given;

public class CreateAccountMethod extends BaseTest {

    public static Response createAccountMethod (Object payload){
        return  given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post(Register)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }
}
