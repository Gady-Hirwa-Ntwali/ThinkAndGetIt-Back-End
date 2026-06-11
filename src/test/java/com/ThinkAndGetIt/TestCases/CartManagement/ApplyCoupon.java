package com.ThinkAndGetIt.TestCases.CartManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ApplyCoupon extends BaseTest {

    @Test
    public void testApplyCouponSuccessfully() {
        TestData testData = new TestData();
        testData.getFreshCartItemId();

        Map<String, Object> payload = TestData.applyCouponPayload("SAVE10");

        Response response = Methods.postMethod("/cart/coupon", payload, TestData.token);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("message"), equalTo("Coupon applied"));
    }

    @Test
    public void testApplyInvalidCoupon() {
        LoginTests.successfulLogin();
        TestData.renewProductVariables();

        Map<String, Object> payload = TestData.applyCouponPayload("FAKE_COUPON_CODE_999");

        Response response = Methods.postMethod("/cart/coupon", payload, TestData.token);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("success"), equalTo(false));
        assertThat(response.path("message"), equalTo("Invalid coupon"));
    }

    @Test
    public void testApplyCouponWithoutAuthentication() {
        Map<String, Object> payload = TestData.applyCouponPayload("SAVE10");

        Response response = Methods.postMethod("/cart/coupon", payload, "");

        assertThat(response.statusCode(), equalTo(401));
    }
}