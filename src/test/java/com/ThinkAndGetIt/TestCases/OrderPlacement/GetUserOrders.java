package com.ThinkAndGetIt.TestCases.OrderPlacement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;

import static com.ThinkAndGetIt.Routes.EndPoints.Orders;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetUserOrders extends BaseTest {

    private String authToken;

    @BeforeClass
    public void setUp() {
        TestData.renewProductVariables();
        this.authToken = TestData.token;
    }

    @Test
    public void testGetOrdersSuccessfullyWithoutFilters() {
        Response response = Methods.GetMethod(Orders, authToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("data"), notNullValue());
    }

    @Test
    public void testGetOrdersWithValidFilters() {
        Map<String, Object> queryParams = TestData.getOrdersQueryParams(1, "PENDING");

        Response response = Methods.GetFilteredProduct(queryParams, Orders, authToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
    }

    @Test
    public void testGetOrdersWithoutAuthentication() {
        Response response = Methods.GetMethod(Orders, "");

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testGetOrdersWithInvalidStatusFilter() {
        Map<String, Object> queryParams = TestData.getOrdersQueryParams(1, "INVALID_STATUS_STRING");

        Response response = Methods.GetFilteredProduct(queryParams, Orders, authToken);
        assertThat(response.statusCode(), equalTo(500));
    }
}