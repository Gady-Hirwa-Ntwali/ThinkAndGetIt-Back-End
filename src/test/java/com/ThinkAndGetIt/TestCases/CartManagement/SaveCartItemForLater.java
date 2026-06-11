package com.ThinkAndGetIt.TestCases.CartManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.ThinkAndGetIt.ReusableMethods.TestData.token;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SaveCartItemForLater extends BaseTest {
    @Test
    public void testSaveCartItemForLaterSuccessfully() {
        TestData testData = new TestData();
        String patchPath = "/cart/items/" + testData.activeItemId + "/save-for-later";

        Response response = Methods.patchMethod(patchPath, token);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("message"), equalTo("Saved for later"));
    }

    @Test
    public void testSaveForLaterNonExistentItem() {
        LoginTests.successfulLogin();
        String fakeItemId = "00000000-0000-0000-0000-000000000000";

        String patchPath = "/cart/items/" + fakeItemId + "/save-for-later";
        Response response = Methods.patchMethod(patchPath, token);

        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.path("success"), equalTo(false));
    }

    @Test
    public void testSaveForLaterWithoutAuthentication() {
        String patchPath = "/cart/items/any-random-id-string/save-for-later";
        Response response = Methods.patchMethod(patchPath, "");
        assertThat(response.statusCode(), equalTo(404));
    }
}