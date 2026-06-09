package com.ThinkAndGetIt.TestCases.CartManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ThinkAndGetIt.ReusableMethods.TestData.token;
import static com.ThinkAndGetIt.Routes.EndPoints.Cart;
import static org.testng.Assert.assertEquals;

public class ClearCart extends BaseTest {
    @Test
    public void testClearCartSuccessfully(){
        LoginTests.successfulLogin();
        Response response = Methods.DeleteMethod(Cart, token);
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void testClearCartWithoutToken(){
        Response response = Methods.DeleteMethod(Cart);
        assertEquals(response.statusCode(), 401);
    }
}
