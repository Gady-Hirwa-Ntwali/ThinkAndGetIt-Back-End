package com.ThinkAndGetIt.TestCases.UserManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetUserAddressesTests extends BaseTest {

    private String authToken;
    private final String GET_ADDRESSES_ENDPOINT = "/users/addresses";

    @BeforeClass
    public void setUpUserToken() {
        this.authToken = TestData.token;
    }

    @Test
    public void testGetAddressesSuccessfully() {
        Response response = Methods.GetMethod(GET_ADDRESSES_ENDPOINT, authToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("data"), notNullValue());
    }

    @Test
    public void testGetAddressesWithoutAuthentication() {
        Response response = Methods.GetMethod(GET_ADDRESSES_ENDPOINT, "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }
}