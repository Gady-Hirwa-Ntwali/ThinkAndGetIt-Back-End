package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.GetUsers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static com.ThinkAndGetIt.Routes.EndPoints.User;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class GetCurrentUser extends BaseTest {
    @Test
    public void getCurrentSuccessful(){
        RegisterCustomer.successfulRegister();
        Response response = GetUsers.currentUser(properties.getProperty("token"));
        String firstName = response.path("data.user.firstName");
        String lastName = response.path("data.user.lastName");
        assertEquals(firstName, "Am not");
        assertEquals(lastName, "A Human");
    }
}
