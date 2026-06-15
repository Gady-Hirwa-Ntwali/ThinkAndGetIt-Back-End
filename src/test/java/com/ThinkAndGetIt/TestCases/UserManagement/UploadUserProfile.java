package com.ThinkAndGetIt.TestCases.UserManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Payloads.UpdateProfilePayload;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UploadUserProfile extends BaseTest {

    private String authToken;

    @BeforeClass
    public void setUpUserToken() {
        this.authToken = TestData.token;
    }

    @Test
    public void testUpdateUserProfileSuccessfully() {
        String updatedFirstName = "Automation_" + System.currentTimeMillis();
        String updatedLastName = "QA";
        String updatedPhone = "+1234567890";

        UpdateProfilePayload bodyPayload = new UpdateProfilePayload(updatedFirstName, updatedLastName, updatedPhone);

        Response response = Methods.putMethod("/users/profile", bodyPayload, authToken);

        assertThat(response.statusCode(), equalTo(200));

        if (response.path("data") != null) {
            assertThat(response.path("data.firstName"), equalTo(updatedFirstName));
            assertThat(response.path("data.lastName"), equalTo(updatedLastName));
            assertThat(response.path("data.phone"), equalTo(updatedPhone));
        }
    }

    @Test
    public void testUpdateProfileWithoutAuthentication() {
        UpdateProfilePayload bodyPayload = new UpdateProfilePayload("Jane", "Doe", "+1999999999");
        Response response = Methods.putMethod("/users/profile", bodyPayload, "");
        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }
}