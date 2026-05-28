package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.util.HashMap;

import static com.ThinkAndGetIt.ReusableMethods.CreateAccountPayload.payload;
import static com.ThinkAndGetIt.ReusableMethods.UpdateProperties.updatePropertiesFile;
import static com.ThinkAndGetIt.Routes.EndPoints.Register;
import static io.restassured.RestAssured.given;

public class RegisterCustomer extends BaseTest {

    @Test
    public static void registerCustomerTest() {
        payload();
    }
}
