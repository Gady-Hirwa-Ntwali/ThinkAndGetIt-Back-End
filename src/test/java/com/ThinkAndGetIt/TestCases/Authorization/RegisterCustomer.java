package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static com.ThinkAndGetIt.ReusableMethods.Payloads.payload;
import static com.ThinkAndGetIt.ReusableMethods.LoginAndRegisterMethods.createAccountMethod;
import static com.ThinkAndGetIt.ReusableMethods.TestData.*;
import static org.testng.Assert.*;

public class RegisterCustomer extends BaseTest {

    @Test
    public static void successfulRegister() {
        Response response = createAccountMethod(payload(DynamicEmail, Password, FName, LName, Phone));
        assertEquals(response.statusCode(), 201);
    }

    @Test
    public void invalidEmailRegister(){
        Response response = createAccountMethod(payload("@Tmbf@tghdsk", Password, FName, LName, Phone));
        assertEquals(response.statusCode(), 409);
        boolean success = response.path("success");
        String message = response.path("message");
        assertFalse(success);
        assertEquals(message, "Email already registered");
    }
}
