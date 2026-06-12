package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.Routes.EndPoints;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteProducts extends BaseTest {
    @Test
    public void deleteProductSuccessTest() {
        LoginTests.successfulLogin();
        Response allProductsResponse = Methods.GetMethod(EndPoints.Products);
        String productId = allProductsResponse.path("data[0].id");

        String targetEndpoint = EndPoints.Products + "/" + productId;
        String token = properties.getProperty("token");

        Response response = Methods.DeleteMethod(targetEndpoint, token);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("message"), equalTo("Product deactivated"));    }
    @Test
    public void deleteProductWithNonExistentIdTest() {
        LoginTests.successfulLogin();

        String fakeId = "88888888-8888-8888-8888-888888888888";
        String targetEndpoint = EndPoints.Products + "/" + fakeId;
        String token = properties.getProperty("token");
        Response response = Methods.DeleteMethod(targetEndpoint, token);
        assertThat(response.statusCode(), equalTo(404));
    }
}
