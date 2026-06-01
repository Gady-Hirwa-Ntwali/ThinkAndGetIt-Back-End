package com.ThinkAndGetIt.TestCases.Authorization;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.GetUsers;
import org.testng.annotations.Test;
import static com.ThinkAndGetIt.Routes.EndPoints.User;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetCurrentUser extends BaseTest {
    @Test
    public void getCurrentUserTest(){
        GetUsers.currentUser(properties.getProperty("token"));
    }
}
