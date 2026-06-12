package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import static com.ThinkAndGetIt.Routes.EndPoints.TokenRefresh;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RefreshToken extends BaseTest {

    @Test
    public void refreshToken(){
        RegisterCustomer.successfulRegister();
        HashMap<String, String> body = new HashMap<>();
        body.put("refreshToken", properties.getProperty("refreshToken"));

        Response response = Methods.postMethod(TokenRefresh,body);
        boolean success = response.path("success");
        String message = response.path("message");
        assertTrue(success);
        assertEquals(message, "Success");
    }
}
