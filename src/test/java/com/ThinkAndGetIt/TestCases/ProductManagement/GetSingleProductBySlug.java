package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetSingleProductBySlug extends BaseTest {
    @Test
    public void getSingleProductByValidSlugTest() {
        Response allProductsResponse = Methods.GetMethod(EndPoints.Products);
        String dynamicSlug = allProductsResponse.path("data[0].slug");
        String expectedId = allProductsResponse.path("data[0].id");

        Response response = Methods.GetMethod(EndPoints.Products + "/" + dynamicSlug);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));

        assertThat(response.path("data.slug"), equalTo(dynamicSlug));
        assertThat(response.path("data.id"), equalTo(expectedId));
        assertThat(response.path("data.name"), notNullValue());
    }

    @Test
    public void getProductWithNonExistentSlugTest() {
        String fakeSlug = "this-slug-does-not-exist-in-db-12345";

        Response response = Methods.GetMethod(EndPoints.Products + "/" + fakeSlug);

        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.path("success"), equalTo(false));
        assertThat(response.path("message"), containsString("not found"));
    }

    @Test
    public void getProductWithSpecialCharactersInSlugTest() {
        String invalidSlug = "sneakers@#$!%^&*()_+";
        Response response = Methods.GetMethod(EndPoints.Products + "/" + invalidSlug);

        assertThat(response.statusCode(), anyOf(equalTo(404), equalTo(400)));
        assertThat(response.path("success"), equalTo(false));
    }
}