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

public class UploadImageForProduct extends BaseTest {
    @Test
    public void uploadProductImagesSuccessTest() {
        // 1. Authenticate session context
        LoginTests.successfulLogin();

        // 2. Query target entity system to fetch an existing entity reference ID
        Response allProductsResponse = Methods.GetMethod(EndPoints.Products);
        String productId = allProductsResponse.path("data[0].id");

        // 3. Mount resource component configuration context (Targeting webp)
        java.io.File targetFile = new java.io.File("src/test/resources/test_image.webp");

        // Automatically generates a real, valid 1x1 transparent WebP file if missing
        if (!targetFile.exists()) {
            try {
                targetFile.getParentFile().mkdirs();
                byte[] validWebpBytes = new byte[] {
                        0x52, 0x49, 0x46, 0x46, 0x1A, 0x00, 0x00, 0x00, 0x57, 0x45, 0x42, 0x50, 0x56, 0x50, 0x38, 0x4C,
                        0x0D, 0x00, 0x00, 0x00, 0x2F, 0x00, 0x07, 0x00, 0x00, 0x07, (byte) 0xB5, (byte) 0x86, 0x73, 0x6D, 0x48, 0x00, 0x00
                };
                java.nio.file.Files.write(targetFile.toPath(), validWebpBytes);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate test WebP asset", e);
            }
        }

        String pathUrl = EndPoints.Products + "/" + productId + "/images";
        String authHeaderToken = properties.getProperty("token");

        // 4. Construct execution wrapper context with correct image/webp content type
        Response response = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + authHeaderToken)
                .contentType("multipart/form-data")
                .multiPart("images", targetFile, "image/webp") // Fixed to webp mapping
                .when()
                .post(pathUrl);

        // Debugging print: Tells you exactly what the server says if it rejects it
        response.prettyPrint();

        // Core Assertions matrix
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("success"), equalTo(true));
    }
    }
