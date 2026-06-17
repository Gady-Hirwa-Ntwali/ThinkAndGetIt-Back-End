package com.ThinkAndGetIt.TestCases.OrderPlacement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AdminGetAllOrders extends BaseTest {

    private String adminToken;

    @BeforeClass
    public void setUp() {
        TestData.renewProductVariables();
        // Assuming your active system token points to an administrator or you load an admin override property
        this.adminToken = TestData.token;
    }

    @Test
    public void testAdminGetOrdersSuccessfullyWithoutFilters() {
        Map<String, Object> queryParams = TestData.getAdminOrdersQueryParams(1, "");

        Response response = Methods.GetFilteredProduct(queryParams, EndPoints.AdminAllOrders, adminToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("data"), notNullValue());
    }

    @Test
    public void testAdminGetOrdersWithValidStatusFilter() {
        Map<String, Object> queryParams = TestData.getAdminOrdersQueryParams(1, "PENDING");

        Response response = Methods.GetFilteredProduct(queryParams, EndPoints.AdminAllOrders, adminToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
    }

    @Test
    public void testAdminGetOrdersWithoutAuthentication() {
        Map<String, Object> queryParams = TestData.getAdminOrdersQueryParams(1, "");

        Response response = Methods.GetFilteredProduct(queryParams, EndPoints.AdminAllOrders, "");
        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testAdminGetOrdersWithInvalidStatusFilter() {
        Map<String, Object> queryParams = TestData.getAdminOrdersQueryParams(1, "NOT_A_VALID_ORDER_STATUS");

        Response response = Methods.GetFilteredProduct(queryParams, EndPoints.AdminAllOrders, adminToken);

        assertThat(response.statusCode(), equalTo(400));
    }
}