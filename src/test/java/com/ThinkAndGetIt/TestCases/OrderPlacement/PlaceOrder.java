package com.ThinkAndGetIt.TestCases.OrderPlacement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.Payloads;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import com.ThinkAndGetIt.TestCases.CartManagement.AddToCart;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PlaceOrder extends BaseTest {

    private String authToken;
    private String validAddressId;

    @BeforeClass
    public void setUp() {
        TestData.renewProductVariables();
        this.authToken = TestData.token;
        AddToCart.addToCartSuccessfully();

        Response addressResponse = Methods.GetMethod(EndPoints.GET_ADDRESSES_ENDPOINT, authToken);
        if (addressResponse.statusCode() == 200 && addressResponse.path("data[0].id") != null) {
            this.validAddressId = addressResponse.path("data[0].id");
        } else {
            this.validAddressId = "00000000-0000-0000-0000-000000000000";
        }
    }

    @Test
    public void testPlaceOrderSuccessfully() {
        HashMap<String, Object> payload = Payloads.placeOrderPayload(
                validAddressId,
                "CASH_ON_DELIVERY",
                "Deliver after 5 PM please.",
                0
        );

        Response response = Methods.postMethod(EndPoints.Orders, payload, authToken);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("success"), equalTo(true));

        if (response.path("message") != null) {
            assertThat(response.path("message"), containsStringIgnoringCase("order"));
        }
    }

    @Test
    public void testPlaceOrderWithoutAuthentication() {
        HashMap<String, Object> payload = Payloads.placeOrderPayload(validAddressId, "CASH_ON_DELIVERY", "Notes", 0);

        Response response = Methods.postMethod(EndPoints.Orders, payload, "");
        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testPlaceOrderWithEmptyCartOrInvalidFields() {
        HashMap<String, Object> invalidPayload = Payloads.placeOrderPayload("", "CASH_ON_DELIVERY", "", 0);

        Response response = Methods.postMethod(EndPoints.Orders, invalidPayload, authToken);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("success"), equalTo(false));
    }
}