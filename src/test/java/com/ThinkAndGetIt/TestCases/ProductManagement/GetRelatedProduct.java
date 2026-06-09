package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetRelatedProduct extends BaseTest {
    @Test
    public void getRelatedProductsSuccessTest() {
        Response allProductsResponse = Methods.GetMethod(EndPoints.Products);
        String productId = allProductsResponse.path("data[0].id");

        Response response = Methods.GetMethod(EndPoints.Products + "/" + productId + "/related");

        boolean success = response.path("success");
        String message = response.path("message");

        assertThat(response.statusCode(), equalTo(200));
        assertThat(success, equalTo(true));
        assertThat(message, equalTo("Success"));

        List<String> allProducts = response.jsonPath().getList("data");
        String ExpectedCategoryId = response.jsonPath().getString("data[0].categoryId");
        for(int i = 0; i<allProducts.size(); i++){
            Assert.assertEquals(response.jsonPath().getString("data["+ i +"].categoryId"), ExpectedCategoryId);
        }

    }
}
