package com.ThinkAndGetIt.TestCases.OrderPlacement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.ThinkAndGetIt.Routes.EndPoints.Orders;
import static com.ThinkAndGetIt.Routes.EndPoints.SingleOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetSingleOrder extends BaseTest {

    private String authToken;
    public static String dynamicOrderId;

    @BeforeClass
    public void setUp() {
        TestData.renewProductVariables();
        this.authToken = TestData.token;

        Response response = Methods.GetMethod(Orders, authToken);

        if (response.statusCode() == 200 && response.path("data[0].id") != null) {
            dynamicOrderId = response.path("data[0].id");
        } else {
            dynamicOrderId = "00000000-0000-0000-0000-000000000000";
        }
    }

    @Test
    public void testGetSingleOrderSuccessfully() {
        Response response = Methods.GetMethod(SingleOrder, authToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("data"), notNullValue());

        if (!dynamicOrderId.equals("00000000-0000-0000-0000-000000000000")) {
            assertThat(response.path("data.id"), equalTo(dynamicOrderId));
        }
    }

    @Test
    public void testGetSingleOrderWithoutAuthentication() {
        String targetEndpoint = Orders + "/" + dynamicOrderId;

        Response response = Methods.GetMethod(targetEndpoint, "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testGetSingleOrderNotFound() {
        String nonExistentOrderId = "99999999-9999-9999-9999-999999999999";
        String targetEndpoint = Orders + "/" + nonExistentOrderId;
        Response response = Methods.GetMethod(targetEndpoint, authToken);

        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.path("success"), equalTo(false));
    }
}