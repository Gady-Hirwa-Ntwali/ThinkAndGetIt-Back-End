package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
public class TrendingProduct extends BaseTest {
    @Test
    public void getTrendingProductsSuccessTest() {
        Response response = Methods.GetMethod(EndPoints.Products + "/trending");
        boolean success = response.path("success");
        String message = response.path("message");
        assertThat(response.statusCode(), equalTo(200));
        assertThat(success, equalTo(true));
        assertThat(message, equalTo("Success"));
    }
    @Test
    public void getTrendingProductsInvalidAPi() {
        Response response = Methods.GetMethod(EndPoints.Products + "/trendin");

        boolean success = response.path("success");
        String message = response.path("message");
        assertThat(response.statusCode(), equalTo(404));
        assertThat(success, equalTo(false));
        assertThat(message, equalTo("Product not found"));
    }
}
