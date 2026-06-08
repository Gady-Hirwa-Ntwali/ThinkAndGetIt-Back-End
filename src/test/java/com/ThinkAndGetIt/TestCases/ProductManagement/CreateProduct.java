package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import com.ThinkAndGetIt.TestCases.Authorization.RegisterCustomer;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static com.ThinkAndGetIt.ReusableMethods.Methods.postMethod;
import static org.hamcrest.Matchers.equalTo;

public class CreateProduct extends BaseTest {
    private String adminToken;
    private final String validCategoryId = "24517e2b-3a02-4bfa-aca1-6a9198dc8c70";

    @BeforeClass
    public void loginAsAdmin() {
        // Log in once before executing tests to keep things DRY and efficient
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", properties.getProperty("email"));
        credentials.put("password", properties.getProperty("password"));

        Response loginResponse = postMethod(EndPoints.Login, credentials);
        adminToken = loginResponse.path("data.token");
    }
    @Test
    public void testCreateNewProduct() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", properties.getProperty("email"));
        credentials.put("password", properties.getProperty("password"));

        Response loginResponse = postMethod(EndPoints.Login, credentials);
        String adminToken = loginResponse.path("data.token");

        Map<String, Object> productPayload = TestData.createProductPayload(
                "24517e2b-3a02-4bfa-aca1-6a9198dc8c70",
                "XL",
                "Matte Black",
                "SKU-BAG-XL"
        );

        Response response = postMethod(EndPoints.Products, productPayload, adminToken);

        response.then()
                .statusCode(201)
                .body("success", equalTo(true));
    }
    @Test
    public void testCreateProductWithInvalidCategoryId() {
        Map<String, Object> productPayload = TestData.createProductPayload(
                "",
                "XL",
                "Matte Black",
                "SKU-INVALID-CAT"
        );

        Response response = postMethod(EndPoints.Products, productPayload, adminToken);
        response.then()
                .statusCode(400)
                .body("success", equalTo(false));
    }

    @Test
    public void testCreateProductWithMissingRequiredFields() {
        Map<String, Object> productPayload = TestData.createProductPayload(
                validCategoryId, "L", "Red", "SKU-MISSING-NAME"
        );

        productPayload.remove("name");

        Response response = postMethod(EndPoints.Products, productPayload, adminToken);
        response.then()
                .statusCode(500)
                .body("success", equalTo(false));
    }

    @Test
    public void testCreateProductWithNegativePrice() {
        Map<String, Object> productPayload = TestData.createProductPayload(
                validCategoryId, "M", "Blue", "SKU-NEG-PRICE"
        );

        productPayload.put("price", -50);

        Response response = postMethod(EndPoints.Products, productPayload, adminToken);

        response.then()
                .statusCode(400)
                .body("success", equalTo(false));
    }
}