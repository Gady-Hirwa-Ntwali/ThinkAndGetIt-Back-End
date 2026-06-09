package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.ReusableMethods.UpdateProperties;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static com.ThinkAndGetIt.ReusableMethods.Methods.postMethod;
import static com.ThinkAndGetIt.ReusableMethods.TestData.ValidCategoryId;
import static com.ThinkAndGetIt.ReusableMethods.TestData.token;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateProduct extends BaseTest {


    @Test
    public static void createProductSuccessfully() {
        Map<String, Object> productPayload = TestData.createProductPayload(
                ValidCategoryId,
                "XL",
                "Matte Black",
                "SKU-BAG-XL"
        );

        Response response = postMethod(EndPoints.Products, productPayload, token);
        String message = response.path("message");
        String productId = response.path("data.id");
        String variantId = response.path("data.variants[0].id");
        UpdateProperties.updatevariantId(variantId, productId);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(message, equalTo("Product created"));
    }
    @Test
    public void testCreateProductWithInvalidCategoryId() {
        Map<String, Object> productPayload = TestData.createProductPayload(
                "",
                "XL",
                "Matte Black",
                "SKU-INVALID-CAT"
        );

        Response response = postMethod(EndPoints.Products, productPayload, token);
        response.then()
                .statusCode(400)
                .body("success", equalTo(false));
    }

    @Test
    public void testCreateProductWithMissingRequiredFields() {
        Map<String, Object> productPayload = TestData.createProductPayload(
                ValidCategoryId, "L", "Red", "SKU-MISSING-NAME"
        );

        productPayload.remove("name");

        Response response = postMethod(EndPoints.Products, productPayload, token);
        response.then()
                .statusCode(500)
                .body("success", equalTo(false));
    }

    @Test
    public void testCreateProductWithNegativePrice() {
        Map<String, Object> productPayload = TestData.createProductPayload(
                ValidCategoryId, "M", "Blue", "SKU-NEG-PRICE"
        );

        productPayload.put("price", -50);

        Response response = postMethod(EndPoints.Products, productPayload, token);

        response.then()
                .statusCode(400)
                .body("success", equalTo(false));
    }
}