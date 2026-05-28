package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

//Browser opens visibly
//-Every action moves slowly
//-Element gets highlighted
//-Page scrolls to the element
//-Click happens after delay
//-Browser stays open after execution

import static com.ThinkAndGetIt.ReusableMethods.LoginMethods.loginTest;
import static io.restassured.RestAssured.given;

public class Login extends BaseTest {

    @Test
    public static void loginWithEmailAndPassword() {
        loginTest(properties.getProperty("email"), properties.getProperty("password"));
    }
}