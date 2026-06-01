package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import org.testng.annotations.Test;

import java.util.HashMap;
import static com.ThinkAndGetIt.Routes.EndPoints.TokenRefresh;
import static io.restassured.RestAssured.given;

public class RefreshToken extends BaseTest {

    @Test
    public void refreshToken(){
        RegisterCustomer.successfulRegister();
        HashMap<String, String> body = new HashMap<>();
        body.put("refreshToken", properties.getProperty("refreshToken"));

        Methods.postMethod(TokenRefresh,body);
    }
}
