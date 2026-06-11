package com.ThinkAndGetIt.TestCases.CartManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ThinkAndGetIt.ReusableMethods.TestData.token;
import static com.ThinkAndGetIt.Routes.EndPoints.Cart;
import static com.ThinkAndGetIt.TestCases.Authorization.LoginTests.successfulLogin;
import static org.testng.Assert.assertEquals;

public class GetCurrentCart extends BaseTest {
    @Test
    public static Response testGetCartSuccessfullyAsLoggedInUser() {
        successfulLogin();
        Response response = Methods.GetMethod(Cart, token);
        assertEquals(response.statusCode(), 200);
        return response;
    }

    @Test
    public void testGetCartWithoutLogin(){
        Response response = Methods.GetMethod(Cart, token);
        assertEquals(response.statusCode(), 200);
    }

}
