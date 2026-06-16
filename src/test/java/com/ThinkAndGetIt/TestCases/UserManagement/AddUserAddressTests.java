package com.ThinkAndGetIt.TestCases.UserManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.Payloads;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

import static com.ThinkAndGetIt.Routes.EndPoints.GET_ADDRESSES_ENDPOINT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AddUserAddressTests extends BaseTest {

    private String authToken;

    @BeforeClass
    public void setUpUserToken() {
        this.authToken = TestData.token;
    }

    @Test
    public void testAddAddressSuccessfully() {
        HashMap<String, Object> payload = Payloads.addAddressPayload(
                "Home",
                "Automation",
                "QA",
                "+1234567890",
                "123 Automation St",
                "Tech City",
                "Silicon State",
                "US",
                "12345",
                true
        );

        Response response = Methods.postMethod(GET_ADDRESSES_ENDPOINT, payload, authToken);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("success"), equalTo(true));

        if (response.path("data") != null) {
            assertThat(response.path("data.id"), notNullValue());
            assertThat(response.path("data.label"), equalTo("Home"));
            assertThat(response.path("data.isDefault"), equalTo(true));
        }
    }

    @Test
    public void testAddAddressWithoutAuthentication() {
        HashMap<String, Object> payload = Payloads.addAddressPayload(
                "Work", "TestName", "TestLastName", "+123", "Street", "City", "State", "US", "123", false
        );

        Response response = Methods.postMethod(GET_ADDRESSES_ENDPOINT, payload, "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }
}