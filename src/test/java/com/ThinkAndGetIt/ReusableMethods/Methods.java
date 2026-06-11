package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.Routes.EndPoints;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import com.ThinkAndGetIt.TestCases.CartManagement.AddToCart;
import com.ThinkAndGetIt.TestCases.CartManagement.GetCurrentCart;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.ThinkAndGetIt.ReusableMethods.TestData.token;
import static com.ThinkAndGetIt.ReusableMethods.TestData.updateCartItem;
import static io.restassured.RestAssured.given;

public class Methods extends BaseTest {

    public static Response GetMethod(String endpoint) {
        return GetMethod(endpoint, "");
    }

    public static Response GetMethod(String endpoint, String token) {
        if (token == null || token.isEmpty()) {
            return given()
                    .spec(requestSpec)
                    .when()
                    .get(endpoint)
                    .then()
                    .spec(responseSpec)
                    .log().all()
                    .extract().response();
        }

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    public static Response GetFilteredProduct(Map queryParams, String endPoint){
                Response response = given()
                .spec(requestSpec)
                .queryParams(queryParams)
                .when()
                .get(endPoint)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .log().all()
                .extract().response();
                return response;
    }

    public static Response postMethod(String endpoint, Object body) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer ")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }
    public static Response postMethod(String endpoint, Object body, String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer "+token)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }
    public static Response putMethod(String endpoint, Object body, String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer "+token)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    public static Response DeleteMethod(String targetEndpoint) {
        return DeleteMethod(targetEndpoint, "");
    }

    public static Response DeleteMethod(String targetEndpoint, String token){
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(targetEndpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    public static Response updateCartItemQuantity(int quantity){
        LoginTests.successfulLogin();
        AddToCart.addToCartSuccessfully();
        TestData.renewProductVariables();
        Response cartResponse = GetCurrentCart.testGetCartSuccessfullyAsLoggedInUser();
        String actualCartItemId = cartResponse.path("data.items.find { it.variantId == '" + TestData.variantId + "' }.id");

        return Methods.putMethod(EndPoints.AddToCart + "/" + actualCartItemId, updateCartItem(quantity), token);
    }

    public static Response patchMethod(String endpoint, String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .patch(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    public static Response patchMethod(String endpoint, Object body, String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }
}
