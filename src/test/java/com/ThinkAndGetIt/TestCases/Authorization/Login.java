package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

//Browser opens visibly
//-Every action moves slowly
//-Element gets highlighted
//-Page scrolls to the element
//-Click happens after delay
//-Browser stays open after execution

import static io.restassured.RestAssured.given;

public class Login extends BaseTest {

    @Test
    public static void loginWithEmailAndPassword() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("email", "darry@example.com");
        body.put("password", "MyPass@123");

        Response response = given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(EndPoints.Login)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .log().all()
                .extract().response();

        String token = response.path("data.token");
        String refreshToken = response.path("data.refreshToken");
        updatePropertiesFile(token, refreshToken);
    }

    static void updatePropertiesFile(String token, String refreshToken) {
        String filePath = "src/test/resources/Config.Properties";
        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream(filePath)) {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Could not load properties file. Creating a new one.");
        }

        props.setProperty("token", token);
        props.setProperty("refreshToken", refreshToken);

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            props.store(out, "Updated via Automation Login Test Execution");
            System.out.println("Tokens successfully updated in Config.Properties!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write tokens back to properties file.");
        }
    }
}