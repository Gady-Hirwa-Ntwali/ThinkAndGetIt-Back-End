package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.Routes.EndPoints;
import org.testng.annotations.Test;

import java.util.HashMap;
import static com.ThinkAndGetIt.TestCases.Authorization.RegisterCustomer.successfulRegister;


public class VerifyEmail extends BaseTest {
    @Test
    public void emailVerificationTest(){
        HashMap<String, String> body = new HashMap<>();
        body.put("email", properties.getProperty("email"));

        successfulRegister();
        Methods.postMethod(EndPoints.VerifyEmail + "/"+ properties.getProperty("token"), body);

    }
}
