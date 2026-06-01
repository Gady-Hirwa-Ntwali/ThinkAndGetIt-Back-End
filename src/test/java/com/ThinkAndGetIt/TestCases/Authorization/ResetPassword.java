package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import org.testng.annotations.Test;
import java.util.HashMap;
import static com.ThinkAndGetIt.Routes.EndPoints.PasswordReset;
import static io.restassured.RestAssured.given;

public class ResetPassword extends BaseTest {
    @Test
    public void resetPassword(){
        RegisterCustomer.successfulRegister();
        HashMap<String, String> body = new HashMap<>();
        body.put("password", "GoodGood");

        Methods.postMethod(PasswordReset + "/"+ properties.getProperty("token"), body);
    }
}
