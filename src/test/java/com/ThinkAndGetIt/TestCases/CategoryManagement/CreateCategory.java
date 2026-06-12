package com.ThinkAndGetIt.TestCases.CategoryManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class CreateCategory extends BaseTest {

    @Test
    public void testCreateCategoryAsAdminSuccessfully() {
        LoginTests.successfulLogin();
        TestData.renewProductVariables();
        String uniqueCategoryName = "Test Category " + System.currentTimeMillis();
        Map<String, Object> payload = TestData.createCategoryPayload(
                uniqueCategoryName,
                "Automated testing category description",
                null
        );

        Response response = Methods.postMethod("/categories", payload, TestData.token);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("message"), equalTo("Category created"));
        assertThat(response.path("data.id"), is(notNullValue()));
    }

    @Test
    public void testCreateCategoryWithoutRequiredFields() {
        LoginTests.successfulLogin();
        TestData.renewProductVariables();

        Map<String, Object> payload = TestData.createCategoryPayload("", "Missing name property", null);

        Response response = Methods.postMethod("/categories", payload, TestData.token);

        assertThat(response.statusCode(), equalTo(409));
        assertThat(response.path("success"), equalTo(false));
    }

    @Test
    public void testCreateCategoryWithoutAuthentication() {
        Map<String, Object> payload = TestData.createCategoryPayload("Guest Cat", "Should fail", null);

        Response response = Methods.postMethod("/categories", payload, "");

        assertThat(response.statusCode(), equalTo(401));
    }
}