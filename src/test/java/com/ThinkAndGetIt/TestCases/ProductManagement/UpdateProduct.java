package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UpdateProduct extends BaseTest {

    @Test
    public void updateProductSuccessTest() {

        Map<String, Object> updatedBody = TestData.updateProduct("new sneakers", 12345);

        LoginTests.successfulLogin();
        Response allProductsResponse = Methods.GetMethod(EndPoints.Products);
        String productId = allProductsResponse.path("data[0].id");
        Response response = Methods.putMethod(EndPoints.Products + "/" + productId, updatedBody, properties.getProperty("token"));
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));

    }

    @Test
    public void updateProductWithNonExistentIdTest() {
        String fakeId = "99999999-9999-9999-9999-999999999999";
        Map<String, Object> updatedBody = TestData.updateProduct("new shorts", 12);

        Response response = Methods.putMethod(EndPoints.Products + "/" + fakeId, updatedBody, properties.getProperty("token"));
        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.path("success"), equalTo(false));
    }
}