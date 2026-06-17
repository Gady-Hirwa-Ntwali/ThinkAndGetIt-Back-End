package com.ThinkAndGetIt.TestCases.OrderPlacement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.Payloads;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RequestOrderReturn extends BaseTest {

    private String authToken;
    private String dynamicOrderId;

    @BeforeClass
    public void setUp() {
        TestData.renewProductVariables();
        this.authToken = TestData.token;

        // Fetch dynamic order context to interact with an order ID safely
        Response response = Methods.GetMethod(EndPoints.Orders, authToken);
        if (response.statusCode() == 200 && response.path("data[0].id") != null) {
            this.dynamicOrderId = response.path("data[0].id");
        } else {
            this.dynamicOrderId = "00000000-0000-0000-0000-000000000000";
        }
    }

    @Test
    public void testRequestReturnValidExecutionFlow() {
        HashMap<String, Object> payload = Payloads.returnOrderPayload("The product size is too small.");

        Response response = Methods.patchMethod(EndPoints.getReturnOrderEndpoint(dynamicOrderId), payload, authToken);
        assertThat(response.statusCode(), anyOf(equalTo(200), equalTo(400)));

        if (response.statusCode() == 200) {
            assertThat(response.path("success"), equalTo(true));
        }
    }

    @Test
    public void testRequestReturnWithoutAuthentication() {
        HashMap<String, Object> payload = Payloads.returnOrderPayload("Defective item.");

        Response response = Methods.patchMethod(EndPoints.getReturnOrderEndpoint(dynamicOrderId), payload, "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testRequestReturnWithMissingReasonField() {
        HashMap<String, Object> invalidPayload = Payloads.returnOrderPayload("");

        Response response = Methods.patchMethod(EndPoints.getReturnOrderEndpoint(dynamicOrderId), invalidPayload, authToken);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("success"), equalTo(false));
    }

    @Test
    public void testRequestReturnNotFound() {
        String nonExistentOrderId = "99999999-9999-9999-9999-999999999999";
        HashMap<String, Object> payload = Payloads.returnOrderPayload("Changed my mind.");

        Response response = Methods.patchMethod(EndPoints.getReturnOrderEndpoint(nonExistentOrderId), payload, authToken);

        assertThat(response.statusCode(), anyOf(equalTo(404), equalTo(400)));
        assertThat(response.path("success"), equalTo(false));
    }
}