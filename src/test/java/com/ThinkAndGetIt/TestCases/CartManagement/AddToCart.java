package com.ThinkAndGetIt.TestCases.CartManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import com.ThinkAndGetIt.TestCases.ProductManagement.CreateProduct;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static com.ThinkAndGetIt.ReusableMethods.TestData.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class AddToCart extends BaseTest {
    @Test
    public static void addToCartSuccessfully() {
        CreateProduct.createProductSuccessfully();
        Response response = Methods.postMethod(EndPoints.AddToCart, TestData.addToCartPayload(productId, variantId, 1), token);
        String message = response.path("message");
        assertEquals(message, "Item added to cart");
    }


    @Test
    public void addToCartWithInvalidProductId() {
        CreateProduct.createProductSuccessfully();
        String fakeProductId = "00000000-0000-0000-0000-000000000000";

        Map<String, Object> badPayload = TestData.addToCartPayload(fakeProductId, variantId, 1);
        Response response = Methods.postMethod(EndPoints.AddToCart, badPayload, token);
        String message = response.path("message");
        assertEquals(message, "Product not found");
    }

    @Test
    public void addToCartWithInvalidVariantId() {
        CreateProduct.createProductSuccessfully();
        String fakeVariantId = "00000000-0000-0000-0000-000000000000";

        Map<String, Object> badPayload = TestData.addToCartPayload(productId, fakeVariantId, 1);
        Response response = Methods.postMethod(EndPoints.AddToCart, badPayload, token);

        assertEquals(response.statusCode(), 400);
        String message = response.path("message");
        assertEquals(message, "Only 0 item(s) in stock");
    }

    @Test
    public void addToCartWithZeroOrNegativeQuantity() {
        CreateProduct.createProductSuccessfully();

        Map<String, Object> badPayload = TestData.addToCartPayload(productId, variantId, 0);
        Response response = Methods.postMethod(EndPoints.AddToCart, badPayload, token);

        assertEquals(response.statusCode(), 400);
        assertFalse(response.path("success"));
    }

    @Test
    public static void addToCartWithoutToken() {
        CreateProduct.createProductSuccessfully();
        Map<String, Object> validPayload = TestData.addToCartPayload(productId, variantId, 1);

        Response response = Methods.postMethod(EndPoints.AddToCart, validPayload, "");

        assertEquals(response.statusCode(), 400);
        assertEquals(response.path("message"), "No user or session ID");
        assertFalse(response.path("success"));
    }

}