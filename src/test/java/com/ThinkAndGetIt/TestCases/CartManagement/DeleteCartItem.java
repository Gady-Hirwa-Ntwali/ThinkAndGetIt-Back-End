package com.ThinkAndGetIt.TestCases.CartManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import com.ThinkAndGetIt.TestCases.ProductManagement.CreateProduct;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ThinkAndGetIt.ReusableMethods.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteCartItem extends BaseTest {
    @Test
    public void testDeleteCartItemSuccessfully() {
        getFreshCartItemId();
        String deletePath = "/cart/items/" + activeItemId;
        Response response = Methods.DeleteMethod(deletePath, token);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("message"), equalTo("Item removed"));
    }

    @Test
    public void testDeleteNonExistentCartItem() {
        LoginTests.successfulLogin();
        String fakeItemId = "00000000-0000-0000-0000-000000000000";

        Response response = Methods.DeleteMethod("/cart/items/" + fakeItemId, token);
        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.path("success"), equalTo(false));
    }

    @Test
    public void testDeleteCartItemWithoutAuthentication() {
        Response response = Methods.DeleteMethod("/cart/items/some-random-id", "");

        assertThat(response.statusCode(), equalTo(404));
    }
}