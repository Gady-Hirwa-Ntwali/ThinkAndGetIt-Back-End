package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetProducts extends BaseTest {

    @Test
    public void getAllProductsTest() {
        Response response = Methods.GetMethod(EndPoints.Products);

        assertThat(response.statusCode(), equalTo(200));
        int productCount = response.path("data.size()");
        assertThat(productCount, greaterThan(1));
    }

    @Test
    public void getProductsWithFiltersAndPaginationTest() {
        int expectedPage = 1;
        int expectedLimit = 5;
        String expectedCategorySlug = "electronics";

        Map<String, Object> queryParams = TestData.getFilteredProduct(expectedPage, expectedLimit, expectedCategorySlug, "price-asc", false);
        Response response = Methods.GetFilteredProduct(queryParams, EndPoints.Products);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("pagination.page"), equalTo(expectedPage));
        assertThat(response.path("pagination.limit"), equalTo(expectedLimit));

        int actualProductCount = response.path("data.size()");
        assertThat(actualProductCount, equalTo(expectedLimit));

        List<String> categories = response.path("data.category.slug");
        assertThat(categories, everyItem(equalTo(expectedCategorySlug)));

        List<Float> prices = response.path("data.price");
        for (int i = 0; i < prices.size() - 1; i++) {
            assertThat("Product list is not sorted in ascending price order!",
                    prices.get(i), lessThanOrEqualTo(prices.get(i + 1)));
        }

    }
    @Test
    public void getProductsWithNegativePaginationTest() {
        // Scenario: Passing completely invalid/negative page and limit values
        Map<String, Object> invalidParams = Map.of(
                "page", -1,
                "limit", -5,
                "category", "electronics"
        );

        Response response = Methods.GetFilteredProduct(invalidParams, EndPoints.Products);

        // Depending on your backend design, this should either fail (400)
        // OR fallback gracefully to default pagination (e.g., page 1, limit 10/20)
        if (response.statusCode() == 400) {
            assertThat(response.path("success"), equalTo(false));
            assertThat(response.path("message"), containsString("invalid"));
        } else {
            // Fallback assertion check
            assertThat(response.statusCode(), equalTo(200));
            assertThat(response.path("pagination.page"), lessThan(1));
            assertThat(response.path("pagination.limit"), greaterThan(0));
        }
    }

    @Test
    public void getProductsWithNonExistentCategoryTest() {
        // Scenario: Passing a category slug that does not exist in the database
        Map<String, Object> queryParams = Map.of(
                "page", 1,
                "limit", 5,
                "category", "non_existent_category_xyz"
        );

        Response response = Methods.GetFilteredProduct(queryParams, EndPoints.Products);
        assertThat(response.statusCode(), equalTo(200));

        // The API shouldn't crash; it should return a successful wrapper but an empty data array
        assertThat(response.path("success"), equalTo(false));
        List<Object> dataList = response.path("data");
        assertThat(dataList, hasSize(0));
    }

    @Test
    public void getProductsWithInvalidSortOptionTest() {
        // Scenario: Passing a sorting rule that the system doesn't support
        Map<String, Object> queryParams = Map.of(
                "page", 1,
                "limit", 5,
                "sortBy", "random-unsupported-string"
        );

        Response response = Methods.GetFilteredProduct(queryParams, EndPoints.Products);

            assertThat(response.path("success"), equalTo(false));

    }
}