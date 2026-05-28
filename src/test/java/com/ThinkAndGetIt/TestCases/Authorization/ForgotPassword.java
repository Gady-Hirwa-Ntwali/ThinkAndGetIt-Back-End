package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.HashMap;

import static com.ThinkAndGetIt.ReusableMethods.ForgotPasswordMethod.forgotPassword;
import static com.ThinkAndGetIt.Routes.EndPoints.Forgot_Password;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ForgotPassword extends BaseTest {
    @Test
    public void SuccessFulTest(){
        Response response = forgotPassword(properties.getProperty("email"));
        String message = response.path("message");
        boolean success = response.path("success");
             assertEquals(response.statusCode(), 200);
             assertEquals(message, "If an account with that email exists, a reset link has been sent.");
             assertTrue(success);
    }

    @Test
    public void invalidEmail(){
        Response response = forgotPassword("dslkfj1234-");
        String message = response.path("message");
        boolean success = response.path("success");
        assertEquals(response.statusCode(), 200);
        assertEquals(message, "If an account with that email exists, a reset link has been sent.");
        assertTrue(success);
    }
}
//even the invalid tests here success