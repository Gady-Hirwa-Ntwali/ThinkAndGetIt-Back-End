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

public class AdminUpdateOrderStatus extends BaseTest {

    private String adminToken;
    private String dynamicOrderId;

    @BeforeClass
    public void setUp() {
        TestData.renewProductVariables();
        this.adminToken = TestData.token;

        Response response = Methods.GetMethod(EndPoints.Orders, adminToken);
        if (response.statusCode() == 200 && response.path("data[0].id") != null) {
            this.dynamicOrderId = response.path("data[0].id");
        } else {
            this.dynamicOrderId = "00000000-0000-0000-0000-000000000000";
        }
    }

    @Test
    public void testAdminUpdateStatusSuccessfully() {
        HashMap<String, Object> payload = Payloads.updateOrderStatusPayload("CONFIRMED", "Order verified.", "TRACK12345");

        Response response = Methods.patchMethod(EndPoints.getAdminUpdateStatusEndpoint(dynamicOrderId), payload, adminToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
    }

    @Test
    public void testAdminUpdateStatusWithoutAuthentication() {
        HashMap<String, Object> payload = Payloads.updateOrderStatusPayload("SHIPPED", "On the way.", "TRACK12345");

        Response response = Methods.patchMethod(EndPoints.getAdminUpdateStatusEndpoint(dynamicOrderId), payload, "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testAdminUpdateStatusWithInvalidStatus() {
        HashMap<String, Object> invalidPayload = Payloads.updateOrderStatusPayload("COMPLETELY_INVALID_STATUS", "Oops", "0000");

        Response response = Methods.patchMethod(EndPoints.getAdminUpdateStatusEndpoint(dynamicOrderId), invalidPayload, adminToken);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("success"), equalTo(false));
    }

    @Test
    public void testAdminUpdateStatusNotFound() {
        String nonExistentOrderId = "99999999-9999-9999-9999-999999999999";
        HashMap<String, Object> payload = Payloads.updateOrderStatusPayload("DELIVERED", "Handed over.", "TRACK12345");

        Response response = Methods.patchMethod(EndPoints.getAdminUpdateStatusEndpoint(nonExistentOrderId), payload, adminToken);

        assertThat(response.statusCode(), anyOf(equalTo(404), equalTo(400)));
        assertThat(response.path("success"), equalTo(false));
    }
}
