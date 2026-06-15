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
        this.authToken = TestData.token;
    }

    @Test
    public void testChangePasswordSuccessfully() {
        HashMap<String, Object> payload = Payloads.changePasswordPayload("Password123!", "NewSecurePass123!");

        Response response = Methods.putMethod(CHANGE_PASSWORD_ENDPOINT, payload, authToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));

        if (response.path("message") != null) {
            assertThat(response.path("message"), anyOf(containsStringIgnoringCase("success"), containsStringIgnoringCase("changed")));
        }
    }

    @Test
    public void testChangePasswordWithoutAuthentication() {
        HashMap<String, Object> payload = Payloads.changePasswordPayload("OldPass123!", "NewPass123!");

        Response response = Methods.putMethod(CHANGE_PASSWORD_ENDPOINT, payload, "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testChangePasswordWithIncorrectCurrentPassword() {
        HashMap<String, Object> payload = Payloads.changePasswordPayload("WrongCurrentPassword!", "NewPass123!");

        Response response = Methods.putMethod(CHANGE_PASSWORD_ENDPOINT, payload, authToken);

        assertThat(response.statusCode(), anyOf(equalTo(400), equalTo(401)));
        assertThat(response.path("success"), equalTo(false));
    }
}