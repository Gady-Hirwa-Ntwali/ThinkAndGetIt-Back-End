package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class flashSales extends BaseTest {
    @Test
    public void getTrendingProductsSuccessTest() {
        Response response = Methods.GetMethod(EndPoints.Products + "/flash-sales");
        boolean success = response.path("success");
        String message = response.path("message");
        assertThat(response.statusCode(), equalTo(200));
        assertThat(success, equalTo(true));
        assertThat(message, equalTo("Success"));
    }
    @Test
    public void getTrendingProductsInvalidAPi() {
        Response response = Methods.GetMethod(EndPoints.Products + "/flashin");

        boolean success = response.path("success");
        String message = response.path("message");
        assertThat(response.statusCode(), equalTo(404));
        assertThat(success, equalTo(false));
        assertThat(message, equalTo("Product not found"));
    }
}
