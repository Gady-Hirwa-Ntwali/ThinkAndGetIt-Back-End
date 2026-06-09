package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrendingProduct extends BaseTest {
    @Test
    public void getTrendingProductsSuccessTest() {
        Response response = Methods.GetMethod(EndPoints.Products + "/trending");

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));

        List<Integer> allViewCounts = response.path("data.viewCount");

        assertThat("The trending data array should contain items",
                allViewCounts, is(not(empty())));

        int trendingThreshold = 100;

        assertThat("Every returned product must have a view count greater than or equal to " + trendingThreshold,
                allViewCounts, everyItem(greaterThanOrEqualTo(trendingThreshold)));
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
