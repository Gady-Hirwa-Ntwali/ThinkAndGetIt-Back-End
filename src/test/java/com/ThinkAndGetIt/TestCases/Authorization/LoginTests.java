package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.ReusableMethods.UpdateProperties;
import io.restassured.response.Response;
import org.testng.annotations.Test;

//Browser opens visibly
//-Every action moves slowly
//-Element gets highlighted
//-Page scrolls to the element
//-Click happens after delay
//-Browser stays open after execution

import static com.ThinkAndGetIt.ReusableMethods.LoginAndRegisterMethods.loginTest;
import static org.testng.Assert.*;

public class LoginTests extends BaseTest {

    @Test
    public static void successfulLogin() {
        Response response = loginTest(properties.getProperty("email"), properties.getProperty("password"));
        assertEquals(response.statusCode(), 200);
        String token = response.path("data.token");
        String refreshToken = response.path("data.refreshToken");
        UpdateProperties.updatePropertiesFile(token, refreshToken);
        BaseTest.reloadProperties();
        TestData.renewProductVariables();
        String actualFirstName = response.path("data.user.firstName");
        assertEquals(actualFirstName, "Xmhnpur");
    }

    @Test
    public void emptyEmailAndPassword(){
        Response response = loginTest(properties.getProperty(""), properties.getProperty(""));
        String email = response.path("email");
        String password = response.path("password");
        assertEquals(response.statusCode(), 500);
        assertEquals(email, null);
        assertEquals(password, null);
    }
    @Test
    public void invalidEmail(){
        Response response = loginTest("@jldskjf@T", properties.getProperty("password"));
        boolean success = response.path("success");
        String message = response.path("message");
        assertEquals(response.statusCode(), 401);
        assertFalse(success);
        assertEquals(message, "Invalid email or password");
    }

    @Test
    public void invalidPassword(){
        Response response = loginTest("email", "dsfkjoekd");
        boolean success = response.path("success");
        String message = response.path("message");
        assertEquals(response.statusCode(), 401);
        assertFalse(success);
        assertEquals(message, "Invalid email or password");
    }



}