package com.ThinkAndGetIt.TestCases.UserManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.Payloads;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ChangePasswordTests extends BaseTest {

    private String authToken;
    private final String CHANGE_PASSWORD_ENDPOINT = "/users/change-password";

    @BeforeClass
    public void setUpUserToken() {
        // Inherit the valid session token from your login sequence setup
        this.authToken = TestData.token;
    }

    @Test
    public void testChangePasswordSuccessfully() {
        // Build payload using your static HashMap utility method
        HashMap<String, Object> payload = Payloads.changePasswordPayload("Password123!", "NewSecurePass123!");

        // Execute via your existing framework utility method
        Response response = Methods.putMethod(CHANGE_PASSWORD_ENDPOINT, payload, authToken);

        // Verify status code success
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));

        // Optional verification block if your response passes back a custom feedback description string
        if (response.path("message") != null) {
            assertThat(response.path("message"), anyOf(containsStringIgnoringCase("success"), containsStringIgnoringCase("changed")));
        }
    }

    @Test
    public void testChangePasswordWithoutAuthentication() {
        // Build payload using your static HashMap utility method
        HashMap<String, Object> payload = Payloads.changePasswordPayload("OldPass123!", "NewPass123!");

        // Fire request using an empty token to verify security intercept
        Response response = Methods.putMethod(CHANGE_PASSWORD_ENDPOINT, payload, "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testChangePasswordWithIncorrectCurrentPassword() {
        // Build payload with an intentionally incorrect current password string
        HashMap<String, Object> payload = Payloads.changePasswordPayload("WrongCurrentPassword!", "NewPass123!");

        Response response = Methods.putMethod(CHANGE_PASSWORD_ENDPOINT, payload, authToken);

        // API should return Bad Request (400) or Unauthorized (401) for credential mismatches
        assertThat(response.statusCode(), anyOf(equalTo(400), equalTo(401)));
        assertThat(response.path("success"), equalTo(false));
    }
}