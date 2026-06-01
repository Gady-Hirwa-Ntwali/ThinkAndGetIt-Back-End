package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.ThinkAndGetIt.Routes.EndPoints.Forgot_Password;
import static com.ThinkAndGetIt.Routes.EndPoints.Register;

public class LoginAndRegisterMethods extends BaseTest {
    public static Response createAccountMethod (Object payload){
        return Methods.postMethod(Register, payload);
    }

    public static Response loginTest(String email, String password){
        HashMap<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return Methods.postMethod(EndPoints.Login, body);

    }

    public static Response forgotPassword(String email){
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        return Methods.postMethod(Forgot_Password, body);


    }

}
