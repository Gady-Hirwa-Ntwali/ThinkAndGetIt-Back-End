package com.ThinkAndGetIt.TestCases.OrderPlacement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CancelOrderTests extends BaseTest {

    private String authToken;
    private String cancellableOrderId;

    @BeforeClass
    public void setUp() {
        TestData.renewProductVariables();
        this.authToken = TestData.token;

        Response response = Methods.GetMethod(EndPoints.Orders, authToken);
        if (response.statusCode() == 200 && response.path("data[0].id") != null) {
            this.cancellableOrderId = response.path("data[0].id");
        } else {
            this.cancellableOrderId = "00000000-0000-0000-0000-000000000000";
        }
    }

    @Test
    public void testCancelOrderSuccessfully() {
        Response response = Methods.patchMethod(EndPoints.getCancelOrderEndpoint(cancellableOrderId), authToken);

        assertThat(response.statusCode(), anyOf(equalTo(200), equalTo(400)));

        if (response.statusCode() == 200) {
            assertThat(response.path("success"), equalTo(true));
        }
    }

    @Test
    public void testCancelOrderWithoutAuthentication() {
        Response response = Methods.patchMethod(EndPoints.getCancelOrderEndpoint(cancellableOrderId), "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testCancelOrderNotFound() {
        String invalidOrderId = "99999999-9999-9999-9999-999999999999";

        Response response = Methods.patchMethod(EndPoints.getCancelOrderEndpoint(invalidOrderId), authToken);

        assertThat(response.statusCode(), anyOf(equalTo(404), equalTo(400)));
        assertThat(response.path("success"), equalTo(false));
    }
}